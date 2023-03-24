#!/bin/sh

aws ecr get-login-password --region ap-southeast-2 | docker login --username AWS --password-stdin ${REPO_URI}