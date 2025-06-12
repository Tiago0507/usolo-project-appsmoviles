@echo off
REM Script actualizado para Windows - ejecutar desde host con reinicio automático
REM Ubicación: DirectusUpdated/scripts/fromhostinit.bat

REM Nombre del contenedor
set CONTAINER_NAME=directusupdated-db-1

REM Nombre de la base de datos y usuario
set DB_NAME=directus
set DB_USER=directus

echo 🚀 Iniciando configuración de base de datos desde host...

REM 1. Ejecutar schema.sql
echo 📋 Ejecutando schema.sql...
set SQL_FILE=schema.sql
if exist "%SQL_FILE%" (
    docker cp "%SQL_FILE%" "%CONTAINER_NAME%:/tmp/schema.sql"
    docker exec -i %CONTAINER_NAME% psql -U %DB_USER% -d %DB_NAME% -f /tmp/schema.sql -w
    
    if %ERRORLEVEL% EQU 0 (
        echo ✅ Schema ejecutado correctamente
    ) else (
        echo ❌ Error ejecutando schema
        pause
        exit /b 1
    )
) else (
    echo ❌ Archivo schema.sql no encontrado
    pause
    exit /b 1
)

REM 2. Ejecutar data.sql
echo 📊 Ejecutando data.sql...
set SQL_FILE=data.sql
if exist "%SQL_FILE%" (
    docker cp "%SQL_FILE%" "%CONTAINER_NAME%:/tmp/data.sql"
    docker exec -i %CONTAINER_NAME% psql -U %DB_USER% -d %DB_NAME% -f /tmp/data.sql -w
    
    if %ERRORLEVEL% EQU 0 (
        echo ✅ Datos insertados correctamente
    ) else (
        echo ❌ Error insertando datos
        pause
        exit /b 1
    )
) else (
    echo ❌ Archivo data.sql no encontrado
    pause
    exit /b 1
)

REM 3. Ejecutar directus_relations.sql
echo 🔗 Ejecutando directus_relations.sql...
set SQL_FILE=directus_relations.sql
if exist "%SQL_FILE%" (
    docker cp "%SQL_FILE%" "%CONTAINER_NAME%:/tmp/directus_relations.sql"
    docker exec -i %CONTAINER_NAME% psql -U %DB_USER% -d %DB_NAME% -f /tmp/directus_relations.sql -w
    
    if %ERRORLEVEL% EQU 0 (
        echo ✅ Relaciones de Directus configuradas correctamente
    ) else (
        echo ❌ Error configurando relaciones de Directus
        pause
        exit /b 1
    )
) else (
    echo ⚠️  Archivo directus_relations.sql no encontrado, saltando configuración de relaciones...
    echo 💡 Crea el archivo directus_relations.sql para configurar las relaciones automáticamente
)

REM 4. Reiniciar Directus automáticamente
echo 🔄 Reiniciando Directus para aplicar cambios...
cd ..
docker-compose restart directus

if %ERRORLEVEL% EQU 0 (
    echo ✅ Directus reiniciado correctamente
) else (
    echo ❌ Error reiniciando Directus
)

echo.
echo 🎉 ¡Configuración completada exitosamente!
echo 🌐 Accede a Directus en: http://localhost:8055
echo 📧 Email: svalenciagarcia707@gmail.com
echo 🔑 Password: admin
pause