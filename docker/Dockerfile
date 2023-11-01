FROM postgis/postgis:14-3.3

RUN apt-get update \
    && apt-get install postgresql-$PG_MAJOR-pgvector \
    && rm -rf /var/lib/apt/lists/*

