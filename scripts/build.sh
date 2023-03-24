#!/bin/sh

docker buildx create --name xbuilder
docker buildx use xbuilder
docker buildx build --push --tag ${REPO_URI}/${APP_NAME}:latest \
--platform=linux/amd64 .
docker buildx build --push --tag ${REPO_URI}/${APP_NAME}:${APP_VERSION} \
--platform=linux/amd64 .

