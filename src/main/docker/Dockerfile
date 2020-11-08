FROM centos

RUN yum --upgrade
FROM openjdk:8
FROM mysql
FROM mysql

CMD ["/usr/bin/java", "-version"]

COPY StackImplService.war $CATALINA_HOME/webapps/StackImplService.war

EXPOSE 8080

CMD ["catalina.sh", "run"]
