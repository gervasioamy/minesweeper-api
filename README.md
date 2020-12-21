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

## API Documentation
You can find API endpoints documented in `{host}:{port}/api-docs.html`, where `host` and `port` values are related to where this app is running, in the case of runing it locally, try `http://localhost:8080/api-docs.html` 