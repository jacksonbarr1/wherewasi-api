# Where Was I API

## Overview

This repo contains source code for the Where Was I API, a Spring Boot REST API that provides services for users to track their
TV show watching history, manage watchlists, and discover new shows. The API integrates with the TMDB API to fetch live
episode metadata and images as needed to support the frontend application. The primary supporting technologies are MongoDB for
long term storage of user and show data, and Redis for caching frequently accessed data.

## Run Instructions

### Development

This environment is intended for local development and testing. It uses Spring Boot's Docker Compose integration to
spin up containers for the application's dependencies (MongoDB, Redis) on start.

- Ensure Docker Desktop is installed and running
- Ensure you have JDK 21 installed (check w/ `java -version`)
- Supply environment variables & secrets by providing your IDE a .env file or by mounting within the terminal via
  `export <ENV_VAR>=<VALUE>`
    - Required secrets
        - `JWT_SECRET`: JWT secret for signing authentication tokens
        - `TMDB_API_KEY`: API key for interfacing with TMDB API for fetching TV data
- Run the application through an IDE run configuration (remember to provide your .env file) or through the terminal with
  `./mvnw spring-boot:run` OR use the included `run-dev.sh` bash script to start the application using environment variables from a `.env` file.

## Testing

The test environment (`test` Spring profile) does not require injection of any environment variables or secrets.

### Unit Tests

- `./mvnw clean test`

### Integration Tests

Testcontainers library provides docker containers for relevant dependencies at runtime.

- Ensure Docker Desktop is installed and running
- `./mvnw clean verify`