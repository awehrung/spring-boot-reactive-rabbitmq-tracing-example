#!/bin/bash

aws configure set aws_access_key_id foo
aws configure set aws_secret_access_key bar
echo "[default]" >~/.aws/config
echo "region = eu-central-1" >>~/.aws/config
echo "output = json" >>~/.aws/config

if awslocal dynamodb describe-table --table-name="my-db"; then
    echo "DynamoDB table already exists"
else
    awslocal dynamodb create-table \
        --table-name="my-db" \
        --attribute-definitions="AttributeName=id,AttributeType=S" \
        --key-schema="AttributeName=id,KeyType=HASH" \
        --provisioned-throughput="ReadCapacityUnits=5,WriteCapacityUnits=5" \
        --region eu-central-1
    echo "DynamoDB table created"
fi
