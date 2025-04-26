FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY target/socks-inventory.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]