#!/bin/bash

# Configuration de la base de données
DB_HOST="localhost"
DB_PORT="3306"
DB_USER="root"
DB_PASSWORD="root"
DB_NAME="discord-db"

# Chemin vers le fichier SQL
SQL_FILE="./database/data.sql"

# Vérifier que le fichier existe
if [ ! -f "$SQL_FILE" ]; then
  echo "❌ Fichier $SQL_FILE introuvable"
  exit 1
fi

# Exécution du script SQL
echo "📥 Seeding de la base de données '$DB_NAME' à l'aide de $SQL_FILE ..."
mysql -h "$DB_HOST" -P "$DB_PORT" -u "$DB_USER" -p"$DB_PASSWORD" "$DB_NAME" < "$SQL_FILE"

# Résultat
if [ $? -eq 0 ]; then
  echo "✅ Seeding terminé avec succès !"
else
  echo "❌ Échec lors du seeding."
  exit 1
fi