#!/bin/bash

# Nombre del contenedor
CONTAINER_NAME="directusupdated-db-1"

# Ruta al archivo SQL
SQL_FILE="./data.sql"

# Nombre de la base de datos y usuario
DB_NAME="directus"
DB_USER="directus"

# Copiar el archivo SQL al contenedor
docker cp "$SQL_FILE" "$CONTAINER_NAME":/tmp/data.sql

# Ejecutar el SQL dentro del contenedor
docker exec -i "$CONTAINER_NAME" psql -U "$DB_USER" -d "$DB_NAME" -f /tmp/data.sql -w
