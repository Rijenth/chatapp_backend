# ChatApp Backend

Ce projet est un backend d'application de chat en temps réel développé avec **Spring Boot**. Il fournit des API REST pour la gestion des utilisateurs, des messages, des canaux de discussion, ainsi qu'un support **WebSocket** pour la communication en temps réel.

---

## 🔧 Technologies utilisées

- Java 21
- Spring Boot
- Spring Security (authentification JWT)
- WebSocket (communication temps réel)
- Maven
- MySQL
- Docker & Docker Compose

---

## 🚀 Démarrage rapide

### Prérequis

- [Java 21+](https://adoptium.net/)
- [Docker](https://www.docker.com/)
- [Maven](https://maven.apache.org/)
- [Git](https://git-scm.com/)

### Lancement avec Docker (recommandé)

```bash
docker-compose up --build
```

Cela lance :
- le backend Spring Boot (`http://localhost:8000`)
- la base de données MySQL
- le visualisateur de base de donnée PHPMYADMIN

---

## 📦 Structure du projet

```
src/
 └── main/
     └── java/
         └── com.discord.api
             ├── api.config         # Configuration WebSocket & sécurité
             ├── api.controllers    # Contrôleurs REST
             ├── api.models         # Entités & DTO
             ├── api.repositories   # Accès aux données (JPA)
             └── api.services       # Logique métier
```

---

## 🔒 Authentification

L’authentification est basée sur **JWT**. Les endpoints protégés nécessitent un token JWT passé via l'en-tête `Authorization: Bearer <token>`.

Exemple d'endpoints d’auth :
- `POST /auth/register`
- `POST /auth/login`

---

## 📡 WebSocket

L'application expose un endpoint WebSocket pour recevoir/envoyer des messages en temps réel :
```
ws://localhost:8000/ws
```

Messages structurés selon un format JSON

---

## 📄 Commandes utiles

```
docker exec -i mysql mysql -uroot -proot discord-db < database/data.sql
```

Permet de remplir la base de donnée avec des données factices
---
