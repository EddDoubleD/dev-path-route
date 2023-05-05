# by default, the image for arm64 is pulled if the gitlab runners work on arch x86_64 must be explicitly specified
# FROM bellsoft/liberica-openjdk-debian:17.0.6
FROM bellsoft/liberica-openjdk-debian:17.0.6-x86_64
LABEL maintainer="sulimov.dmitriy@otr.ru"
WORKDIR /app
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
