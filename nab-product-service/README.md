# nab_product
This Spring Boot microservice provides operations for Product processes

## Development Setup
1. Install Java 8
2. Install Git
3. Install gradle

# Build/Run
run command "gradle bootRun"

## Docker file Setup (in-progress)
1. run command "gradle build assemble" to generate jar file
2. run command "docker build . -t [your docker hub]/nab-product-service"
2. run command "docker run -p 8081:8081 [your docker hub]/nab-product-service"
