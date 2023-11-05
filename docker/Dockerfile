FROM postgis/postgis:16-3.4

RUN apt-get update \
    && apt-get install postgresql-$PG_MAJOR-pgvector \
    && rm -rf /var/lib/apt/lists/*

