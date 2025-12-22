# Systeme_Gestion_Colis_Transporteur
Système de Gestion de Colis et Transporteurs
Une application Spring Boot pour la gestion de colis avec authentification JWT et rôles ADMIN/TRANSPORTEUR.

# 📋 Description

Ce projet implémente un système complet de gestion de colis pour une entreprise de logistique, permettant :

La gestion de colis aux caractéristiques variables selon leur type (STANDARD, FRAGILE, FRIGO)
Une authentification sécurisée stateless avec JWT
Des niveaux d'accès différenciés (TRANSPORTEUR/ADMIN)
Une architecture moderne avec Spring Boot, MongoDB et Docker

# 🏗️ Architecture Technique

Technologies Utilisées

Backend: Spring Boot 3.2.0

Base de données: MongoDB (NoSQL)

Sécurité: Spring Security avec JWT

Tests: JUnit 5 + Mockito

Documentation: Swagger/OpenAPI 3

Conteneurisation: Docker + Docker Compose

CI/CD: GitHub Actions

# Architecture en Couches
├── Controller (REST API)
├── Service (Logique métier)
├── Repository (Accès données)
├── DTO (Data Transfer Objects)
├── Mapper (Conversion entités/DTOs)
├── Exception (Gestion des erreurs)
└── Model (Entités MongoDB)
🚀 Démarrage Rapide

# Prérequis
Java 17+
Maven 3.8+
Docker & Docker Compose
Installation
Cloner le projet
git clone <repository-url>
cd gestion-colis

Démarrer avec Docker Compose

docker-compose up -d

Accéder aux services

API: http://localhost:8082
Swagger UI: http://localhost:8082/swagger-ui.html
N8n: http://localhost:5678


# Admin par Défaut
Rôle	, Login	 ,Mot de passe
Admin	,admin	 ,admin123



📚 Documentation API
Authentification

# Se connecter
POST /api/auth/login
Content-Type: application/json

{
"login": "admin",
"password": "admin123"
}
# Endpoints Principaux
*Admin

GET /api/admin/colis - Lister tous les colis
POST /api/admin/colis - Créer un colis
PUT /api/admin/colis/{id} - Modifier un colis
DELETE /api/admin/colis/{id} - Supprimer un colis
POST /api/admin/colis/{id}/assign - Assigner un colis
GET /api/admin/transporteurs - Lister les transporteurs

*Transporteur

GET /api/transporteur/colis - Lister mes colis
PUT /api/transporteur/colis/{id}/status - Mettre à jour statut

# 📊 Modèles de Données

* Types de Colis

STANDARD: type, poids, adresse_destination, statut
FRAGILE: + instructions_manutention
FRIGO: + temperature_min, temperature_max

* Statuts
Colis: EN_ATTENTE, EN_TRANSIT, LIVRE, ANNULE
Transporteur: DISPONIBLE, EN_LIVRAISON


# Diagramm de class

<img width="320" height="345" alt="diag class brief security" src="https://github.com/user-attachments/assets/547d9753-d7f9-41ce-840c-c6b87e3b9083" />


# 🧪 Tests

Exécuter les tests unitaires

mvn test

🐳 Docker

Build l'image
docker build -t gestion-colis .
Lancer les services
docker-compose up -d

Arrêter les services

docker-compose down
🔄 CI/CD
Le pipeline GitHub Actions inclut :

Tests: Exécution des tests unitaires
Build: Compilation et packaging
Sécurité: Scan de vulnérabilités avec Trivy
Déploiement: Push Docker Hub et déploiement

📈 Performance

Index MongoDB
users.login: Unique
users.role: Performance
colis.transporteurId: Requêtes transporteur
colis.type/statut: Filtrage
Optimisations
Pagination sur toutes les listes
Cache des réponses fréquentes
Connection pooling MongoDB

🛡️ Sécurité

JWT: Tokens signés avec HMAC-SHA512
Rôles: Séparation ADMIN/TRANSPORTEUR
CORS: Configuration restrictive
Validation: Bean Validation sur tous les DTOs
Password Encoding: BCrypt
