FROM maven:3.8.6-amazoncorretto-17 as maven
LABEL COMPANY="Lodz University of Technology"
LABEL MAINTAINER="224406@edu.p.lodz.pl, 224441@edu.p.lodz.pl, 224466@edu.p.lodz.pl"
LABEL APPLICATION="Courses App"

WORKDIR /usr/src/app
COPY . /usr/src/app
RUN mvn -Dmaven.test.skip=true clean package -P dev_docker

FROM tomcat:10.0.27-jdk17-corretto-al2
ARG TOMCAT_FILE_PATH=/docker/tomcat

#Data & Config - Persistent Mount Point
ENV APP_DATA_FOLDER=/var/lib/SampleApp
ENV SAMPLE_APP_CONFIG=${APP_DATA_FOLDER}/config/

ENV CATALINA_OPTS="-Xms1024m -Xmx4096m -XX:MetaspaceSize=512m -XX:MaxMetaspaceSize=512m -Xss512k"

#Move over the War file from previous build step
WORKDIR /usr/local/tomcat/webapps/
COPY --from=maven /usr/src/app/target/courses_app.war /usr/local/tomcat/webapps/api.war

RUN cp -R /usr/local/tomcat/webapps.dist/manager /usr/local/tomcat/webapps/manager
RUN cp -R /usr/local/tomcat/webapps.dist/docs /usr/local/tomcat/webapps/docs

COPY ${TOMCAT_FILE_PATH}/* ${CATALINA_HOME}/conf/

RUN cp ${CATALINA_HOME}/conf/context.xml /usr/local/tomcat/webapps/manager/META-INF/context.xml

WORKDIR $APP_DATA_FOLDER

EXPOSE 8080
ENTRYPOINT ["catalina.sh", "run"]