FROM openjdk:16.0.2
LABEL maintainer="demo-fisclouds-api"
ADD target/demo-product-service-0.0.1-SNAPSHOT product-svc.jar
ENTRYPOINT ["java","-jar", "product-svc.jar"]