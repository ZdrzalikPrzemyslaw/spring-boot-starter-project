#! /bin/bash

docker compose -f dockercompose.yml down --volumes  --remove-orphans  

docker compose -f dockercompose.yml build --no-cache

mvn clean resources:resources liquibase:update clean -P dev_local -f ../pom.xml

docker compose -f dockercompose.yml up