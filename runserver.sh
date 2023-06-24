#!/bin/bash

echo "running server ..."

if docker images | awk '{print $1}' | grep -q "^baloot-frontend$"; then
    echo "frontend image already exists, skipping build."
  else
    echo "frontend image does not exist, building."
    docker build -t baloot-frontend ./front-end/myapp
fi

if docker images | awk '{print $1}' | grep -q "^baloot-backend$"; then
    echo "backend image already exists, skipping build."
  else
    echo "backend image does not exist, building."
    docker build -t baloot-frontend ./back-end
fi

if docker images | awk '{print $1}' | grep -q "^baloot-db$"; then
    echo "DB image already exists, skipping build."
  else
    echo "DB image does not exist, building."
    docker build -t baloot-frontend ./backend/src/main/java/Repository
fi

docker container stop $(docker container ls -aq)

echo "frontend connected to port 3000..."
docker run -p 3000:80 baloot-frontend &

echo "DB connected to port 3306"
docker run -p 3306:3306 baloot-db &

echo "backend connected to port 8080"
docker run -p 8080:8080 baloot-backend &


