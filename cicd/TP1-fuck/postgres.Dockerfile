FROM postgres:17.8-alpine3.23

ENV POSTGRES_DB=postgres
ENV POSTGRES_USER=postgres
ENV POSTGRES_PASSWORD=admin

COPY dados/data.sql /docker-entrypoint-initdb.d/

VOLUME /var/lib/postgresql/data
