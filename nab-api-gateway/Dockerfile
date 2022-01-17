#Start with a base image containing Java runtime
FROM openjdk:8 as build

#Information around who maintains the image
MAINTAINER com.nab

# Add the application's jar to the container
COPY build/libs/nab-api-gateway-0.0.1-SNAPSHOT.jar nab-api-gateway.jar

#execute the application
ENTRYPOINT ["java","-jar","/nab-api-gateway.jar"]
