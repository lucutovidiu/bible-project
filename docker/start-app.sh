#!/bin/bash

print_colored() {
  echo -e "\x1b[30;44m $1 \x1b[m"
}

print_colored "Stopping BE app"
cd ./be
docker compose up -d

cd ..

print_colored "Stopping FE app"
cd ./fe
docker compose up -d

cd ..

print_colored "Stopping nginx app"
cd ./nginx
docker compose up -d

cd ..

print_colored "Stopping pg-admin app"
cd ./db
docker compose start bible-project-pgadmin

cd ..

sleep 3
print_colored "Running containers"
docker ps