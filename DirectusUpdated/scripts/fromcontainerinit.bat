@echo off
REM Nombre del contenedor
set CONTAINER_NAME=directusupdated-db-1

REM Ruta al archivo SQL
set SQL_FILE=schema.sql

REM Nombre de la base de datos y usuario
set DB_NAME=directus
set DB_USER=directus

REM Copiar el archivo SQL al contenedor
docker cp "%SQL_FILE%" "%CONTAINER_NAME%:/tmp/data.sql"

REM Ejecutar el SQL dentro del contenedor
docker exec -i %CONTAINER_NAME% psql -U %DB_USER% -d %DB_NAME% -f /tmp/data.sql
