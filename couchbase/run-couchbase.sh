#!/usr/bin/env bash

docker build -t eyerdurmaz/couchbase .
docker rm todo-couchbase
docker run -d --name todo-couchbase -p 8091-8093:8091-8093 -p 11210:11210 eyerdurmaz/couchbase