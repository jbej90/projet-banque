package com.excilys.projet.banque.dao.utils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.commons.lang.StringUtils;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.stream.StreamingDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.dataset.xml.XmlDataSet;
import org.dbunit.dataset.xml.XmlProducer;
import org.dbunit.operation.DatabaseOperation;
import org.springframework.core.Constants;
import org.xml.sax.InputSource;

/**
 * Indicates that a test class or a test method has to load and purge the database wih a DBUnit dataset
 * 
 * @author Stephane LANDELLE
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface DataSet {

	/**
	 * @author Alain MAHIER
	 */
	enum DBOps {
		/** Nothing, @see {@link DatabaseOperation#NONE} */
		NONE,

		/** @see {@link DatabaseOperation#UPDATE} */
		UPDATE,

		/** @see {@link DatabaseOperation#INSERT} */
		INSERT,

		/** @see {@link DatabaseOperation#REFRESH} */
		REFRESH,

		/** @see {@link DatabaseOperation#DELETE} */
		DELETE,

		/** @see {@link DatabaseOperation#DELETE_ALL} */
		DELETE_ALL,

		/** @see {@link DatabaseOperation#TRUNCATE_TABLE} */
		TRUNCATE_TABLE,

		/** @see {@link DatabaseOperation#CLEAN_INSERT} */
		CLEAN_INSERT;

		/** Internal representation of the {@link DatabaseOperation} constants */
		private final transient Constants	operations	= new Constants(DatabaseOperation.class);

		/**
		 * Convert this enum into {@link DatabaseOperation operation database}.
		 * 
		 * @return The DatabaseOperation for this enum
		 */
		public DatabaseOperation asDatabaseOperation() {
			return DatabaseOperation.class.cast(operations.asObject(name()));
		}
	}

	/**
	 * Dataset file format
	 * 
	 * @author Alain Mahier
	 */
	enum Format {
		/**
		 * flat format
		 * 
		 * @see {@link FlatXmlDataSet}.
		 */
		FLAT {

			/**
			 * {@inheritDoc}
			 */
			public IDataSet fromInputStream(final InputStream in) throws DataSetException, IOException {
				return new FlatXmlDataSetBuilder().build(in);
			}
		},
		/**
		 * flat format
		 * 
		 * @see {@link XmlDataSet}.
		 */
		XML {

			/**
			 * {@inheritDoc}
			 */
			public IDataSet fromInputStream(final InputStream in) throws DataSetException, IOException {
				return new XmlDataSet(in);
			}
		},
		/**
		 * flat format
		 * 
		 * @see {@link StreamingXmlDataSet}.
		 */
		STREAMING {

			/**
			 * {@inheritDoc}
			 */
			public IDataSet fromInputStream(final InputStream in) throws DataSetException, IOException {
				return new StreamingDataSet(new XmlProducer(new InputSource(in)));
			}
		};

		/**
		 * Returns a {@link IDataSet dataset} with the format for this enum
		 * 
		 * @param the
		 *            data file {@link InputStream}
		 * @return a {@link IDataSet dataset}
		 * @throws DataSetException
		 *             DBUnit failure
		 * @throws IOException
		 *             I/O failure
		 */
		public abstract IDataSet fromInputStream(final InputStream in) throws DataSetException, IOException;
	}

	/**
	 * Dataset file location
	 * 
	 * @return
	 */
	String value() default StringUtils.EMPTY;

	/**
	 * DBUnit operation on setup (default : {@link DBOps#CLEAN_INSERT})
	 */
	DBOps setUpOperation() default DBOps.CLEAN_INSERT;

	/**
	 * DBUnit operation on teardown (default : {@link DBOps#CLEAN_INSERT})
	 */
	DBOps tearDownOperation() default DBOps.NONE;

	/**
	 * {@link IDataSet} file format (default : {@link Format#FLAT})
	 */
	Format format() default Format.FLAT;
}
