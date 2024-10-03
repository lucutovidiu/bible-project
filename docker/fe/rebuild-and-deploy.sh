#!/bin/bash

print_colored() {
  echo -e "\x1b[30;44m $1 \x1b[m"
}

print_colored "Removing current bible-project-fe container"
docker-compose down
print_colored "Re-Building new bible-project-fe container"
docker compose build
print_colored "Running new bible-project-fe container"
docker compose up -d