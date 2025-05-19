# Application de Gestion de Crédits Bancaires

## Table des matières
1. [Introduction](#introduction)
2. [Architecture Technique](#architecture-technique)
3. [Backend](#backend)
   - [Structure du Projet](#structure-du-projet)
   - [Modèle de Données](#modèle-de-données)
   - [Repositories](#repositories)
   - [Services](#services)
   - [Controllers](#controllers)
   - [Configuration de la Base de Données](#configuration-de-la-base-de-données)
   - [Dépendances Maven](#dépendances-maven)
4. [Frontend](#frontend)
   - [Structure du Projet](#structure-du-projet-frontend)
   - [Composants](#composants)
   - [Services](#services-frontend)
   - [Modèles de Données](#modèles-de-données-frontend)
   - [Configuration](#configuration-frontend)
   - [Dépendances](#dépendances-frontend)
5. [Perspectives d'Évolution](#perspectives-dévolution)

## Introduction

Cette application est un système de gestion de crédits bancaires développé avec Spring Boot pour le backend et Angular pour le frontend. Elle permet la gestion complète des clients, des demandes de crédit et des remboursements.

## Architecture Technique

L'application suit une architecture en couches avec :
- Backend : Spring Boot (Java)
- Frontend : Angular (TypeScript)
- Base de données : MySQL
- Communication : REST API

## Backend

### Structure du Projet
```
backend/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── bank/
│   │   │           ├── controller/
│   │   │           ├── model/
│   │   │           ├── repository/
│   │   │           └── service/
│   │   └── resources/
│   │       └── application.properties
│   └── test/
└── pom.xml
```

### Modèle de Données

#### Client
```java
@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String email;
    @OneToMany(mappedBy = "client")
    private List<Credit> credits;
}
```

#### Credit
```java
@Entity
public class Credit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate dateDemande;
    private String statut;
    private LocalDate dateAcceptation;
    private double montant;
    private int dureeRemboursement;
    private double tauxInteret;
    @ManyToOne
    private Client client;
    private String type;
    private String motif;
    private String typeBien;
    private String raisonSociale;
}
```

#### Remboursement
```java
@Entity
public class Remboursement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private double montant;
    @ManyToOne
    private Credit credit;
}
```

### Repositories

- `ClientRepository` : Gestion des opérations CRUD pour les clients
- `CreditRepository` : Gestion des opérations CRUD pour les crédits
- `RemboursementRepository` : Gestion des opérations CRUD pour les remboursements

### Services

- `ClientService` : Logique métier pour la gestion des clients
- `CreditService` : Logique métier pour la gestion des crédits
- `RemboursementService` : Logique métier pour la gestion des remboursements

### Controllers

- `ClientController` : Endpoints REST pour la gestion des clients
- `CreditController` : Endpoints REST pour la gestion des crédits
- `RemboursementController` : Endpoints REST pour la gestion des remboursements

### Configuration de la Base de Données

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/bank_credit
spring.datasource.username=root
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### Dépendances Maven

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
    </dependency>
</dependencies>
```

## Frontend

### Structure du Projet
```
frontend/
├── src/
│   ├── app/
│   │   ├── components/
│   │   │   ├── client/
│   │   │   ├── credit/
│   │   │   └── remboursement/
│   │   ├── services/
│   │   ├── models/
│   │   └── app.component.ts
│   ├── assets/
│   └── environments/
└── package.json
```

### Composants

#### ClientComponent
- Gestion des clients (CRUD)
- Formulaire d'ajout/modification
- Liste des clients avec actions
- Validation des formulaires

#### CreditComponent
- Gestion des crédits (CRUD)
- Formulaire dynamique selon le type de crédit
- Liste des crédits avec statuts
- Calcul automatique des intérêts

#### RemboursementComponent
- Gestion des remboursements
- Suivi des paiements
- Historique des remboursements
- Calcul des montants restants

### Interface Utilisateur

#### Navigation
```html
<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
  <div class="container">
    <a class="navbar-brand" routerLink="/">{{ title }}</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
      <ul class="navbar-nav">
        <li class="nav-item">
          <a class="nav-link" routerLink="/clients" routerLinkActive="active">Clients</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" routerLink="/credits" routerLinkActive="active">Crédits</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" routerLink="/remboursements" routerLinkActive="active">Remboursements</a>
        </li>
      </ul>
    </div>
  </div>
</nav>
```

#### Styles
```css
.navbar {
  margin-bottom: 2rem;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.navbar-brand {
  font-weight: bold;
  font-size: 1.5rem;
}

.nav-link {
  font-size: 1.1rem;
  padding: 0.5rem 1rem;
}

.nav-link.active {
  font-weight: bold;
}

.container {
  padding: 1rem;
}
```

#### Caractéristiques de l'Interface
- Barre de navigation responsive avec Bootstrap
- Navigation fluide entre les composants
- Indication visuelle de la page active
- Design moderne et professionnel
- Interface adaptative pour tous les appareils
- Formulaires intuitifs avec validation
- Messages de confirmation pour les actions importantes
- Gestion des erreurs avec feedback utilisateur

### Services Frontend

```typescript
@Injectable({
  providedIn: 'root'
})
export class ClientService {
  private apiUrl = 'http://localhost:8080/api/clients';

  constructor(private http: HttpClient) {}

  getClients(): Observable<Client[]> {
    return this.http.get<Client[]>(this.apiUrl);
  }

  // Autres méthodes CRUD
}
```

### Modèles de Données Frontend

```typescript
export interface Client {
  id?: number;
  nom: string;
  email: string;
}

export interface Credit {
  id?: number;
  dateDemande: string;
  statut: 'EN_COURS' | 'ACCEPTE' | 'REJETE';
  montant: number;
  dureeRemboursement: number;
  tauxInteret: number;
  clientId: number;
  type: 'PERSONNEL' | 'IMMOBILIER' | 'PROFESSIONNEL';
  // Champs spécifiques selon le type
}

export interface Remboursement {
  id?: number;
  date: string;
  montant: number;
  creditId: number;
}
```

### Configuration Frontend

```typescript
// app.module.ts
@NgModule({
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    RouterModule.forRoot([
      { path: 'clients', component: ClientComponent },
      { path: 'credits', component: CreditComponent },
      { path: 'remboursements', component: RemboursementComponent }
    ])
  ]
})
```

### Dépendances Frontend

```json
{
  "dependencies": {
    "@angular/core": "^17.0.0",
    "@angular/common": "^17.0.0",
    "@angular/forms": "^17.0.0",
    "@angular/router": "^17.0.0",
    "@angular/platform-browser": "^17.0.0",
    "bootstrap": "^5.3.0",
    "rxjs": "~7.8.0"
  }
}
```

## Perspectives d'Évolution

1. **Sécurité**
   - Implémentation de l'authentification JWT
   - Gestion des rôles utilisateurs
   - Validation des données côté serveur

2. **Fonctionnalités**
   - Tableau de bord avec statistiques
   - Génération de rapports PDF
   - Notifications par email
   - Calcul automatique des échéances

3. **Interface**
   - Thème sombre/clair
   - Responsive design amélioré
   - Animations et transitions
   - Graphiques et visualisations

4. **Performance**
   - Mise en cache des données
   - Pagination côté serveur
   - Optimisation des requêtes
   - Lazy loading des modules 