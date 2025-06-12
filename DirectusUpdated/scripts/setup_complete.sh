#!/bin/bash

# Script completo para configurar todo el proyecto USOLO
# Ubicación: DirectusUpdated/setup_complete.sh

echo "🚀 USOLO - Configuración Completa del Proyecto"
echo "=============================================="

# Verificar si Docker está corriendo
if ! docker info > /dev/null 2>&1; then
    echo "❌ Docker no está corriendo. Por favor inicia Docker primero."
    exit 1
fi

# 1. Levantar los contenedores
echo "📦 Levantando contenedores Docker..."
docker-compose up -d

# Esperar a que la base de datos esté lista
echo "⏳ Esperando a que la base de datos esté lista..."
until docker exec directusupdated-db-1 pg_isready -U directus -d directus > /dev/null 2>&1; do
    echo "   Esperando conexión a la base de datos..."
    sleep 2
done
echo "✅ Base de datos lista"

# Esperar a que Directus esté listo
echo "⏳ Esperando a que Directus esté listo..."
sleep 10

# 2. Ejecutar scripts de base de datos
echo "📋 Configurando base de datos..."
cd scripts

# Ejecutar schema
if [ -f "schema.sql" ]; then
    echo "   Ejecutando schema.sql..."
    docker cp schema.sql directusupdated-db-1:/tmp/
    docker exec -i directusupdated-db-1 psql -U directus -d directus -f /tmp/schema.sql -w
    echo "   ✅ Schema aplicado"
else
    echo "   ❌ schema.sql no encontrado"
    exit 1
fi

# Ejecutar data
if [ -f "data.sql" ]; then
    echo "   Ejecutando data.sql..."
    docker cp data.sql directusupdated-db-1:/tmp/
    docker exec -i directusupdated-db-1 psql -U directus -d directus -f /tmp/data.sql -w
    echo "   ✅ Datos insertados"
else
    echo "   ❌ data.sql no encontrado"
    exit 1
fi

# Ejecutar relaciones de Directus
if [ -f "directus_relations.sql" ]; then
    echo "   Ejecutando directus_relations.sql..."
    docker cp directus_relations.sql directusupdated-db-1:/tmp/
    docker exec -i directusupdated-db-1 psql -U directus -d directus -f /tmp/directus_relations.sql -w
    echo "   ✅ Relaciones de Directus configuradas"
else
    echo "   ⚠️  directus_relations.sql no encontrado, saltando..."
fi

cd ..

# 3. Reiniciar Directus para aplicar cambios
echo "🔄 Reiniciando Directus para aplicar cambios..."
docker-compose restart directus

# Esperar a que Directus reinicie
echo "⏳ Esperando a que Directus reinicie..."
sleep 15

# 4. Verificar que todo esté funcionando
echo "🔍 Verificando configuración..."

# Verificar conexión a Directus
if curl -s http://localhost:8055/server/ping > /dev/null; then
    echo "✅ Directus está funcionando"
else
    echo "❌ Directus no responde"
fi

# Verificar relaciones en la base de datos
RELATIONS_COUNT=$(docker exec -i directusupdated-db-1 psql -U directus -d directus -t -c "SELECT COUNT(*) FROM directus_relations WHERE collection = 'reservation';" | tr -d ' ')

if [ "$RELATIONS_COUNT" -gt 0 ]; then
    echo "✅ Relaciones de Directus configuradas ($RELATIONS_COUNT encontradas)"
else
    echo "⚠️  No se encontraron relaciones de Directus"
fi

echo ""
echo "🎉 ¡Configuración completada!"
echo "================================"
echo "🌐 Directus Admin: http://localhost:8055"
echo "📧 Email: svalenciagarcia707@gmail.com"
echo "🔑 Password: admin"
echo ""
echo "📱 Ahora puedes ejecutar la app Android en Android Studio"
echo ""
echo "🔧 Comandos útiles:"
echo "   Ver logs: docker-compose logs -f"
echo "   Parar todo: docker-compose down"
echo "   Reiniciar: docker-compose restart"