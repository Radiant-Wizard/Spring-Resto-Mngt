# Spring Resto Management

![Java](https://img.shields.io/badge/Java-22-informational)
![Spring Boot](https://img.shields.io/badge/SpringBoot-3.3.3-success)
![Maven](https://img.shields.io/badge/Maven-Project-blue)
![License](https://img.shields.io/badge/License-MIT-lightgrey)

Backend en **Java Spring Boot** pour la gestion d’un restaurant : ingrédients, plats, commandes et ventes.  
L’application expose une **REST API** conforme à OpenAPI 3.0.

---

## 📋 Description

Cette application permet de gérer les opérations principales d’un restaurant :

- **Ingrédients** : gestion, prix, mouvements de stock  
- **Plats** : création, composition avec ingrédients, quantités disponibles  
- **Commandes** : création, mise à jour, suivi des statuts  
- **Ventes** : récupération des plats vendus (attachés aux commandes livrées)

---

## 🛠️ Technologies

- **Java 22**  
- **Spring Boot 3.3.3**  
- **Maven**  
- **PostgreSQL** (ou H2 pour tests)  
- **Lombok** pour réduire le code boilerplate  
- **OpenAPI 3.0** pour la documentation de l’API

---

## 📂 Structure du projet

```markdown
src/
├── main/java/com/Radiant_wizard/GastroManagementApp
│    ├── configuration   # Configuration datasource
│    ├── Controller      # Contrôleurs REST (Dish, Ingredient, Order, Sale, Health)
│    ├── entity          # DTOs, entités et enums
│    ├── mapper          # Mappers entre entités et DTOs
│    ├── repository      # DAO / implémentations pour les entités
│    └── Service         # Services métiers
└── main/resources
├── application.properties
├── db/migration    # Scripts de création de tables
├── db/mockData     # Données de test
└── resto-mng-OAS.yaml # Spécification OpenAPI

````

---

## 🚀 Installation & Lancement

1. **Cloner le dépôt**  
```bash
git clone https://github.com/Radiant-Wizard/Spring-Resto-Mngt.git
cd Spring-Resto-Mngt
````

2. **Configurer la base de données**

* Modifier `src/main/resources/application.properties` pour la connexion PostgreSQL ou H2.

3. **Compiler et lancer l’application**

```bash
mvn clean install
mvn spring-boot:run
```

4. **Tester l’API**

* L’API est exposée sur `http://localhost:8080`
* Importer `resto-mng-OAS.yaml` dans Postman / Insomnia pour tester les endpoints.

---

## 📌 Endpoints principaux

### Ingrédients

* `GET /ingredients` : Liste des ingrédients
* `PUT /ingredients/{id}/prices` : Ajouter des prix à un ingrédient
* `PUT /ingredients/{id}/stockMovements` : Ajouter des mouvements de stock

### Plats

* `GET /dishes` : Liste des plats
* `PUT /dishes/{id}/ingredients` : Composer un plat avec des ingrédients

### Commandes

* `GET /orders/{reference}` : Récupérer une commande par référence
* `POST /orders/{reference}` : Créer une nouvelle commande
* `PUT /orders/{reference}/dishes` : Définir les plats d’une commande
* `PUT /orders/{reference}/dishes/{dishId}` : Mettre à jour le statut d’un plat

### Ventes

* `GET /sales` : Récupérer les plats vendus (attachés aux commandes livrées)

---

## 🛠️ Modèles principaux

* **Ingredient** : prix, stockMovements, quantité disponible
* **Dish** : liste des ingrédients, prix, quantité disponible
* **Order** : plats commandés, quantités, statut (`CREATED`, `CONFIRMED`, `IN_PROGRESS`, `FINISHED`, `DELIVERED`)
* **DishSold** : informations sur les ventes des plats

---

