#!/usr/bin/env bash
mvn clean resources:resources liquibase:update -P dev
