#! /bin/bash

mvn clean -f ../pom.xml

docker compose -f dockercompose.yml down --volumes  --remove-orphans

docker compose -f dockercompose.yml build --no-cache

mvn clean compile liquibase:update -P dev_local -f ../pom.xml

mvn clean -f ../pom.xml

docker compose -f dockercompose.yml up -d