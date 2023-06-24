# FROM maven:3.6.3 AS maven-build
#
# RUN ls
#
# LABEL authors="aryan"
#
# RUN mkdir backend-baloot
#
# WORKDIR /backend-baloot
#
# COPY ./pom.xml .
# COPY ./src .
#
# RUN mvn package
#
# FROM openjdk:17-jdk-alpine
#
# COPY --from=maven-build /build/target/*.war /backend-baloot/app.war

FROM openjdk:20

COPY  target/*.war baloot-back.war

ENTRYPOINT ["java", "-jar", "baloot-back.war"]

EXPOSE 8080 8080

EXPOSE 3306