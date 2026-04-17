FROM maven:3.9.11-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml ./
COPY src ./src

RUN mvn -q -DskipTests package

FROM tomcat:9.0-jdk17-temurin

RUN rm -rf /usr/local/tomcat/webapps/*

COPY --from=build /app/target/webvul-shop.war /usr/local/tomcat/webapps/ROOT.war

ENV DB_HOST=mysql
ENV DB_PORT=3306
ENV DB_NAME=webvul_shop
ENV DB_USER=webvul
ENV DB_PASSWORD=webvul_pass

EXPOSE 8080

CMD ["catalina.sh", "run"]

