# Where Was I API

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
  `./mvnw spring-boot:run`

## Testing

The test environment (`test` Spring profile) does not require injection of any environment variables or secrets.

### Unit Tests

- `/mvnw clean test`

### Integration Tests

Testcontainers library provides docker containers for relevant dependencies at runtime.

- Ensure Docker Desktop is installed and running
- `/mvnw clean verify`