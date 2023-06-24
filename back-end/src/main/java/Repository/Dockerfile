# Use the official MySQL image as a base
FROM mysql:latest

# Set the root password
ENV MYSQL_ROOT_PASSWORD=root

# Create a new database
ENV MYSQL_DATABASE = BalootDB

# Copy the SQL script to create the database
COPY CreateTable.sql /docker-entrypoint-initdb.d/

# Expose the MySQL port
EXPOSE 3306