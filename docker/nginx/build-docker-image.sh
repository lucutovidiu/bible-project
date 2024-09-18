#!/bin/bash

print_colored() {
  echo -e "\x1b[30;44m $1 \x1b[m"
}

print_colored "Removing current nginx-service container"
docker-compose down
print_colored "Building new nginx-service container"
docker compose build --no-cache
print_colored "Running new nginx-service container"
docker compose up -d