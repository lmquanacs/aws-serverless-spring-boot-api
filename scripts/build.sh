#!/bin/sh

docker buildx use default
docker buildx build --push \
--tag ${REPO_URI}/${APP_NAME}:latest \
--tag ${REPO_URI}/${APP_NAME}:${APP_VERSION} \
--platform=linux/arm64 .

docker buildx imagetools inspect ${REPO_URI}/${APP_NAME}:${APP_VERSION}
