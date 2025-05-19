# Application de Gestion de Crédits Bancaires - Backend

## 1. Introduction
Ce projet est le backend d'une application de gestion de crédits bancaires développée avec Spring Boot. L'application permet de gérer différents types de crédits (Personnel, Immobilier et Professionnel) et leurs remboursements associés.

## 2. Analyse des Besoins

### 2.1 Règles Métier
- Un client peut souscrire à plusieurs crédits
- Trois types de crédits distincts : Personnel, Immobilier et Professionnel
- Chaque crédit peut avoir plusieurs remboursements
- Gestion des statuts des crédits (En cours, Accepté, Rejeté)
- Suivi des remboursements (Mensualités et Remboursements anticipés)

### 2.2 Modèle de Données
Le système est structuré autour de quatre entités principales :
1. **Client** : Représente les informations des clients
2. **Credit** : Classe abstraite représentant les crédits
3. **Remboursement** : Gère les remboursements des crédits
4. **Types de Crédits** : Héritent de la classe Credit (Personnel, Immobilier, Professionnel)

## 3. Architecture Technique

### 3.1 Stack Technologique
- Framework : Spring Boot
- Base de données : MySQL
- ORM : Hibernate
- API : REST

### 3.2 Structure du Projet et Diagramme
```
backend/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── bank/
│   │   │           ├── entites/       # Entités JPA
│   │   │           ├── repository/    # Interfaces d'accès aux données
│   │   │           ├── service/       # Logique métier
│   │   │           ├── controller/    # Points d'entrée API REST
│   │   │           ├── mappers/       # Pour les transformation entre JPA et DTO
│   │   │           └── dto/           # Entités DTO 
│   │   └── resources/
│   │       └── application.properties
│   └── test/
└── pom.xml
```


## 4. Modèle de Données Détaillé

### 4.1 Entité Client
- **Attributs** :
  - id (Long) : Identifiant unique
  - nom (String) : Nom du client
  - email (String) : Adresse email
- **Relations** :
  - One-to-Many avec Credit

### 4.2 Entité Credit (Abstraite)
- **Attributs** :
  - id (Long) : Identifiant unique
  - dateDemande (LocalDate) : Date de demande
  - statut (Enum) : En cours, Accepté, Rejeté
  - dateAcceptation (LocalDate) : Date d'acceptation
  - montant (BigDecimal) : Montant du crédit
  - dureeRemboursement (Integer) : Durée en mois
  - tauxInteret (BigDecimal) : Taux d'intérêt
- **Relations** :
  - Many-to-One avec Client
  - One-to-Many avec Remboursement


### 4.3 Types de Crédits

#### CreditPersonnel
- Hérite de Credit
- Attribut supplémentaire : motif (String)

#### CreditImmobilier
- Hérite de Credit
- Attribut supplémentaire : typeBien (Enum : Appartement, Maison, Local Commercial)

#### CreditProfessionnel
- Hérite de Credit
- Attributs supplémentaires :
  - motif (String)
  - raisonSociale (String)

### 4.4 Entité Remboursement
- **Attributs** :
  - id (Long) : Identifiant unique
  - date (LocalDate) : Date du remboursement
  - montant (BigDecimal) : Montant remboursé
  - type (Enum) : Mensualité ou Remboursement anticipé
- **Relations** :
  - Many-to-One avec Credit

## 5. Stratégie de Persistance

### 5.1 Mapping Objet-Relationnel
- Utilisation de JPA/Hibernate
- Stratégie d'héritage : SINGLE_TABLE pour les types de crédits
- Colonne discriminante : type_credit

### 5.2 Configuration de la Base de Données
- SGBD : MySQL
- Mode de génération du schéma : update
- Configuration des connexions via application.properties

## 6. Couche d'Accès aux Données

### 6.1 Repositories
- ClientRepository : Gestion des clients
- CreditRepository : Gestion des crédits
- RemboursementRepository : Gestion des remboursements

### 6.2 Méthodes Principales
- Recherche par identifiant
- Recherche des crédits par client
- Recherche des remboursements par crédit

## 7. Couche Service

### 7.1 Services Principaux
- CreditService : Gestion des opérations sur les crédits
- ClientService : Gestion des opérations sur les clients
- RemboursementService : Gestion des remboursements

### 7.2 Fonctionnalités Implémentées
- Création de crédits
- Validation des données
- Calcul des remboursements
- Gestion des statuts

## 8. API REST

### 8.1 Points d'Entrée
- /clients : Gestion des clients
- /credits : Gestion des crédits
- /remboursements : Gestion des remboursements

### 8.2 Opérations Disponibles
- GET : Récupération des données
- POST : Création de nouvelles entités
- PUT : Mise à jour des entités
- DELETE : Suppression d'entités

### 8.3 Détails des Contrôleurs

#### 8.3.1 ClientController
- `GET /clients` : Liste tous les clients
- `GET /clients/{id}` : Récupère un client par son ID
- `POST /clients` : Crée un nouveau client
- `PUT /clients/{id}` : Met à jour les informations d'un client
- `DELETE /clients/{id}` : Supprime un client
- `GET /clients/{id}/credits` : Liste tous les crédits d'un client

#### 8.3.2 CreditController
- `GET /credits` : Liste tous les crédits
- `GET /credits/{id}` : Récupère un crédit par son ID
- `POST /credits` : Crée une nouvelle demande de crédit
- `PUT /credits/{id}` : Met à jour un crédit
- `DELETE /credits/{id}` : Supprime un crédit
- `GET /credits/{id}/remboursements` : Liste les remboursements d'un crédit
- `PUT /credits/{id}/status` : Met à jour le statut d'un crédit
- `POST /credits/{id}/simulate` : Simule un plan de remboursement

#### 8.3.3 RemboursementController
- `GET /remboursements` : Liste tous les remboursements
- `GET /remboursements/{id}` : Récupère un remboursement par son ID
- `POST /remboursements` : Enregistre un nouveau remboursement
- `PUT /remboursements/{id}` : Met à jour un remboursement
- `GET /remboursements/credit/{creditId}` : Liste les remboursements par crédit
- `GET /remboursements/status/{status}` : Filtre les remboursements par statut



## 9. Sécurité et Validation

### 9.1 Validation des Données
- Validation des montants
- Vérification des dates
- Contrôle des statuts

### 9.2 Gestion des Erreurs
- Messages d'erreur personnalisés
- Codes HTTP appropriés
- Logging des exceptions

## 10. Installation et Configuration

### 10.1 Prérequis
- Java 17 ou supérieur
- Maven
- MySQL

### 10.2 Configuration
1. Cloner le repository
2. Configurer la base de données dans `application.properties`
3. Exécuter `mvn clean install`
4. Lancer l'application avec `mvn spring-boot:run`

# Application de Gestion de Crédits Bancaires - frontend
