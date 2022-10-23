#! /bin/bash

docker compose -f dockercompose.yml down --volumes  --remove-orphans  

docker compose -f dockercompose.yml build --no-cache

docker compose -f dockercompose.yml up