#!/bin/bash

export PGUSER=postgres
psql <<- SHELL
  CREATE USER docker;
  CREATE DATABASE "Adventureworks";
  GRANT ALL PRIVILEGES ON DATABASE "Adventureworks" TO docker;
SHELL
#cd /data
psql -d Adventureworks < /docker-entrypoint-initdb.d/data/install.sql

# this should have had a database by itself, but let's be lazy for now
psql -d Adventureworks < /docker-entrypoint-initdb.d/data/test-tables.sql
psql -d Adventureworks < /docker-entrypoint-initdb.d/data/issue148.sql
