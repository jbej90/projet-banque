package com.excilys.projet.banque.dao.utils;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.ext.hsqldb.HsqldbDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;
import org.springframework.util.ClassUtils;

/**
 * Spring test framework TestExecutionListener that looks for the DataSet annotation and if found, attempts to load a data set (test fixture) before the test is run. Expects an
 * existing {@link DataSource} in the $ {@link TestContext}
 */
public class DataSetTestExecutionListener extends AbstractTestExecutionListener {

	/**
	 * A configuration cache used between setup and teardown
	 */
	private final Map<Method, DataSetConfiguration>	configurationCache	= Collections.synchronizedMap(new IdentityHashMap<Method, DataSetConfiguration>());

	/**
	 * Logger
	 */
	private static final Logger						LOGGER				= LoggerFactory.getLogger(DataSetTestExecutionListener.class);

	/**
	 * {@inheritDoc}
	 */
	public void beforeTestMethod(TestContext testContext) throws Exception {

		DataSetConfiguration configuration = getConfiguration(testContext);

		if (configuration != null) {
			executeOperation(configuration.getSetUpOperation(), configuration.getDataSet(), testContext);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void afterTestMethod(TestContext testContext) throws Exception {

		DataSetConfiguration configuration = getConfiguration(testContext);

		if (configuration != null) {
			executeOperation(configuration.getTearDownOperation(), configuration.getDataSet(), testContext);
		}
	}

	/**
	 * Execute a DBUbit operation
	 * 
	 * @param operation
	 *            the operation to be executed
	 * @param dataSet
	 *            the dataSet
	 * @param testContext
	 *            the context
	 * @throws Exception
	 *             failure
	 */
	private void executeOperation(DatabaseOperation operation, IDataSet dataSet, TestContext testContext) throws Exception {

		DataSource dataSource = lookUpDataSource(testContext);
		Connection connection = DataSourceUtils.getConnection(dataSource);
		DatabaseConnection databaseConnection = new DatabaseConnection(connection);
		databaseConnection.getConfig().setProperty(DatabaseConfig.PROPERTY_ESCAPE_PATTERN, "\"?\"");
		databaseConnection.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new HsqldbDataTypeFactory());

		try {
			operation.execute(databaseConnection, dataSet);
		}
		finally {
			if (!DataSourceUtils.isConnectionTransactional(connection, dataSource)) {
				// if the connection is transactional, closing it. Otherwise,
				// expects that the framework will do it
				DataSourceUtils.releaseConnection(connection, dataSource);
			}
		}
	}

	/**
	 * Get the configuration. <br />
	 * By convention:
	 * <ul>
	 * <li>if location is Empty, expect a file named after the test Class, in the same package, with .xml extention.</li>
	 * <li>otherwise if location does not contain ":" or "/", expect a file named after the location, in the same directory as the test Class.</li>
	 * <li>otherwise, use location as {@link Resource} location</li>
	 * </ul>
	 * 
	 * @param testContext
	 *            the context
	 * @return the configuration
	 * @throws IOException
	 *             I/O failureDataSet
	 * @throws DatabaseUnitException
	 *             DBUnit failure
	 */
	private DataSetConfiguration getConfiguration(TestContext testContext) throws IOException, DatabaseUnitException {

		DataSetConfiguration configuration = configurationCache.get(testContext.getTestMethod());

		if (configuration == null) {
			// no configuration in cache --> instancing it
			configuration = new DataSetConfiguration();

			DataSet annotation = DataSet.class.cast(findAnnotation(testContext.getTestMethod(), DataSet.class));
			if (annotation != null) {

				configuration.setSetUpOperation(annotation.setUpOperation().asDatabaseOperation());

				configuration.setTearDownOperation(annotation.tearDownOperation().asDatabaseOperation());

				// dataSet
				String location = annotation.value();

				if (location != null) {
					StringBuilder builder = new StringBuilder();

					if (StringUtils.EMPTY.equals(location)) {
						builder.append("classpath:/");
						builder.append(ClassUtils.getQualifiedName(testContext.getTestClass()).replace('.', '/'));
						builder.append(".xml");
						location = builder.toString();

					}
					else if (!location.contains(":") && !location.contains("/")) {
						builder.append("classpath:/");
						builder.append(ClassUtils.getPackageName(testContext.getTestClass()).replace('.', '/'));
						builder.append('/');
						builder.append(location);
						location = builder.toString();
					}
				}

				LOGGER.debug("Laoding DataSet file '{}'", location);
				Resource dataSetFile = new DefaultResourceLoader().getResource(location);
				configuration.setDataSet(annotation.format().fromInputStream(dataSetFile.getInputStream()));

				configurationCache.put(testContext.getTestMethod(), configuration);
			}
		}

		return configuration;
	}

	/**
	 * recupere une unique DataSource dans le contexte
	 * 
	 * @param testContext
	 *            le contexte de test
	 * @return la datasource
	 */
	private DataSource lookUpDataSource(TestContext testContext) {
		String[] dsNames = testContext.getApplicationContext().getBeanNamesForType(DataSource.class);
		if (dsNames.length != 1) {
			throw new IllegalStateException("Impossible de determiner une DataSource");
		}
		return DataSource.class.cast(testContext.getApplicationContext().getBean(dsNames[0]));
	}

	/**
	 * Cherche l'annotation au niveau de la methode, puis au niveau de la classe
	 * 
	 * @param method
	 *            la methode
	 * @param annotationType
	 *            le type d'annotation
	 * @return l'annotation
	 */
	private Annotation findAnnotation(Method method, Class<? extends Annotation> annotationType) {

		Annotation annotation = AnnotationUtils.findAnnotation(method, annotationType);
		return annotation == null ? AnnotationUtils.findAnnotation(method.getDeclaringClass(), annotationType) : annotation;
	}

	/**
	 * la configuration une fois processee
	 * 
	 * @author Stephane LANDELLE
	 */
	private class DataSetConfiguration {

		/**
		 * Le dataset
		 */
		private IDataSet			dataSet;

		/**
		 * L'operation a executer au setUp
		 */
		private DatabaseOperation	setUpOperation;

		/**
		 * L'operation a executer au tearDown
		 */
		private DatabaseOperation	tearDownOperation;

		public IDataSet getDataSet() {
			return dataSet;
		}

		public void setDataSet(IDataSet dataSet) {
			this.dataSet = dataSet;
		}

		public DatabaseOperation getSetUpOperation() {
			return setUpOperation;
		}

		public void setSetUpOperation(DatabaseOperation setUpOperation) {
			this.setUpOperation = setUpOperation;
		}

		public DatabaseOperation getTearDownOperation() {
			return tearDownOperation;
		}

		public void setTearDownOperation(DatabaseOperation tearDownOperation) {
			this.tearDownOperation = tearDownOperation;
		}
	}
}
