FROM openjdk:21-ea-9-jdk-slim
LABEL maintainer="demo-fisclouds-api"
ADD target/demo-product-service-0.0.1-SNAPSHOT product-svc.jar
ENTRYPOINT ["java","-jar", "product-svc.jar"]