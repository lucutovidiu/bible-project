#!/bin/bash

print_colored() {
  echo -e "\x1b[30;44m $1 \x1b[m"
}

print_colored "Removing current bible-project-be container"
docker-compose down
print_colored "Re-Building new bible-project-be container"
docker compose build
print_colored "Running new bible-project-be container"
docker compose up -d