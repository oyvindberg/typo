ARG PG_MAJOR
FROM postgis/postgis:$PG_MAJOR-3.4

RUN apt-get update \
    && apt-get install postgresql-$PG_MAJOR-pgvector \
    && rm -rf /var/lib/apt/lists/*

