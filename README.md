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
- `MONGODB_URI`: the MongoDB connection string. It should be formatted as: `mongodb://[username:password@]host1[:port1][,...hostN[:portN]][/[defaultauthdb][?options]]`. For more details go to [mongo reference](https://docs.mongodb.com/manual/reference/connection-string/)

(see ./docker-compose.yml as an example of using that en var)

## API Documentation
You can find API endpoints documented in `{host}:{port}/api-docs.html`, where `host` and `port` values are related to where this app is running, in the case of runing it locally, try `http://localhost:8080/api-docs.html` 

Or you can also find it at https://gamy-minesweeper-api.herokuapp.com/api-docs.html


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
 
 
## Public deploy
The application is deployed on [Heroku](www.heroku.com) and the MongoDB is deployed in [mongo cloud](https://cloud.mongodb.com/)

The API is accessible at https://gamy-minesweeper-api.herokuapp.com/ for example:
```
curl --request POST 'https://gamy-minesweeper-api.herokuapp.com/api/games/' \
--header 'Content-Type: application/json' \
--data-raw '{
    "cols": 6,
    "rows": 6,
    "mines": 8,
    "player": "JohnDoe"
}'
```

```
curl --request POST 'https://gamy-minesweeper-api.herokuapp.com/api/games/396800ef-be2a-4704-b428-113b61e06dc5/discover/' \
--header 'Content-Type: application/json' \
--data-raw '{
    "row": 3,
    "col": 3
}'
```

Or else, you can play with swagger docs and execute the endpoints from there at 
https://gamy-minesweeper-api.herokuapp.com/api-docs.html