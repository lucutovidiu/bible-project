#!/bin/bash

print_colored() {
  echo -e "\x1b[30;44m $1 \x1b[m"
}

print_colored "Stopping BE app"
cd ./be
docker compose down

cd ..

print_colored "Stopping FE app"
cd ./fe
docker compose down

cd ..

print_colored "Stopping nginx app"
cd ./nginx
docker compose down

cd ..

print_colored "Stopping pg-admin app"
cd ./db
docker compose stop bible-project-pgadmin

cd ..

print_colored "Running containers"
docker ps