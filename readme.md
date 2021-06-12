# Music Finder
## how to run the application
First you need to have maven and docker installed on the machine you want to run it on.

### build application
To (re)build the application (jar), execute the following:
Then you can execute the following:
```shell script
mvn package
```

### build docker image
To build a docker image that can run the application, execute the following:
```shell script
docker-compose build
```

### run docker image
To run the docker image, execute the following:
```shell script
docker-compose run
```
This will start both a mongo database that stores all data and the music finder application.
The application has a swagger ui which can be used to call the API:
http://localhost:8080/swagger-ui.html

## security
The API is secured with basic authentication. 
For now there is only one hard-coded user with username "username" and password "password" (can be edited in application.properties file).

## choices
The following choices were made when creating this application:
- Spring Boot
- Mongo DB: good scalability and performance and no relations needed
- Swagger: great tooling for generating API documentation
- Lombok: easy getters/setters generation
- Docker: containerize the application together with the database for easy setup
- Spring security: secure the API with basic authentication (fastest option)
- Spring validation: validate incoming models on requests
- seperation of controller layer and service layer so there are no strong dependencies between frontend and database

## next steps
- add integrations tests and unit tests for the services
- add more documentation to Swagger UI
- add more validation on models
- add error message to 400 responses to make more clear what went wrong
- add authentication to mongo db
- move values from application.properties to docker-compose.yml
- use docker volume to store mongo data files outside the container