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

