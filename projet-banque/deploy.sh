rm -rf /opt/apache-tomcat-7.0.8/webapp/projet-banque-web.war
rm -rf /opt/apache-tomcat-7.0.8/webapp/projet-banque-web
mvn clean

mvn package -Dmaven.test.skip=true

cp target/projet-banque-web.war /opt/apache-tomcat-7.0.8/webapps
