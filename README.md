# Minesweeper-api
https://github.com/deviget/minesweeper-API

## Getting Started
Run tests
```
./mvnw clean test
```

Build, tests, package & build docker image
```
./mvnw clean package
```
Note that [fabric8](https://dmp.fabric8.io/) plugin is used to build docker image during `package` phase

## Run it
The easy way to run it is using docker compose.
_Please go to [Docker docs](https://www.docker.com/get-started) if needed to install docker_
```
docker-compose up
```

### Config options
The following environment variables can be set to change some parameters:
- `MONGODB_HOST`: the host where MongoDB is running
- `MONGODB_PORT`: the port where MongoDB is running
- `MONGODB_USER`: the username to connect to MongoDB 

(see ./docker-compose.yml as an example of using those en vars)

## API Documentation
You can find API endpoints documented in `{host}:{port}/api-docs.html`, where `host` and `port` values are related to where this app is running, in the case of runing it locally, try `http://localhost:8080/api-docs.html` 

## Implementation details
Stack used:
- Java 11
- Spring Boot
- Spring Data 
- MongoDB
- Docker
- Swagger

The application basically have different layers:
- **Controller**: where all the REST endpoints are defined. It's based in Spring MVC
- **Service**: middle tier to let the controllers interact with the model and the repository
- **Repository**: the repository abstraction which implementation is for MongoDB. It's based on Spring Data
- **Model**: the domain. All the business rules and game logic is there.

The error handling is implemented by throwing `RuntimeExeptions` handled by `MainExceptionHandler` which is a 
 `@ControllerAdvice` that catched all uncaught exceptions before building the HTTP response. That way it's possible to
 centralize where the edge cases are handled taking advantage of SpringMVC features for converting exceptions into 
 HTTP Responses.