#!/usr/bin/env bash
mvn clean resources:resources liquibase:update -P dev_local -f ../pom.xml
