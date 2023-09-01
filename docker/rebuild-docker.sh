#! /bin/bash

docker compose -f dockercompose.yml down --volumes  --remove-orphans  

docker compose -f dockercompose.yml build --no-cache

mvn clean resources:testResources liquibase:update  -P dev_local -f ../pom.xml

mvn clean

docker compose -f dockercompose.yml up -d