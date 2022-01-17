#Start with a base image containing Java runtime
FROM openjdk:8 as build

#Information around who maintains the image
MAINTAINER com.nab

# Add the application's jar to the container
COPY build/libs/nab-eureka-0.0.1-SNAPSHOT.jar nab-eureka.jar

#execute the application
ENTRYPOINT ["java","-jar","/nab-eureka.jar"]
