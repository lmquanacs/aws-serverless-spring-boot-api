#!/bin/sh

awslocal dynamodb create-table \
    --table-name order \
    --key-schema \
        AttributeName=order_uid,KeyType=HASH \
        AttributeName=created_date,KeyType=RANGE \
    --attribute-definitions \
        AttributeName=order_uid,AttributeType=S \
        AttributeName=created_date,AttributeType=S \
    --billing-mode PAY_PER_REQUEST || true