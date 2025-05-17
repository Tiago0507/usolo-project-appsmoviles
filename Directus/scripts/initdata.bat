@echo off

SET CONTAINER_NAME=directus-db-1
SET SQL_FILE=.\data.sql
SET DB_NAME=directus
SET DB_USER=directus

docker cp "%SQL_FILE%" "%CONTAINER_NAME%:/tmp/data.sql"
docker exec -i %CONTAINER_NAME% psql -U %DB_USER% -d %DB_NAME% -f /tmp/data.sql -w

pause