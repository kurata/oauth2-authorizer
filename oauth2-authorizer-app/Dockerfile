FROM java:8
MAINTAINER Kurata <fernando.kurata@gmail.com>

VOLUME /tmp
EXPOSE 8080

ARG JAR_FILE

ENV env local
ENV db_host localhost
ENV db_port 3306
ENV db_schema oauth2authorizer
ENV db_username oauth2authorizer
ENV db_password oauth2authorizer

COPY ./application-custom.yaml /
RUN sed -e "s/\${db_host}/$db_host/" -e "s/\${db_port}/$db_port/" -e "s/\${db_schema}/$db_schema/" -e "s/\${db_username}/$db_username/" -e "s/\${db_password}/$db_password/" application-custom.yaml

ADD ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","app.jar","--spring.profiles.active=${env}"]