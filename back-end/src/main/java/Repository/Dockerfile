FROM mysql:latest

ENV MYSQL_HOST=localhost
ENV MYSQL_DATABASE = BalootDB
ENV MYSQL_ROOT_PASSWORD=root


COPY my.conf /etc/mysql/my.cnf

COPY CreateTable.sql /docker-entrypoint-initdb.d/

EXPOSE 3306