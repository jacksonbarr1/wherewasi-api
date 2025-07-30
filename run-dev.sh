#!/bin/bash

if [ ! -f .env ] then
    echo "Error: .env file not found."
    exit 1
fi

echo "Loading environment variables from .env."

set -a
source .env
set +a

echo "Environment variables loaded."

echo "Starting Spring Boot application."

./mvnw spring-boot:run