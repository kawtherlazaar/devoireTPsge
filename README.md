
# 📘 Système de Gestion des Étudiants (SGE)

Mini‑projet réalisé dans le cadre des TP de développement avec **Spring Boot** et **Thymeleaf**.  
Il s’agit d’une application  permettant de gérer les étudiants, leurs filières, leurs modules, ainsi que les **notes et bulletins**.

---

##  Fonctionnalités principales
- Gestion des **filières, modules et étudiants** avec persistance en base MySQL.  
- Saisie et calcul automatique des **notes** avec mise à jour de la moyenne.  
- Génération des **bulletins de notes** (moyenne pondérée, mention, statut Admis/Ajourné).  
- Interfaces web avec **Thymeleaf** pour consulter la liste des bulletins et le détail par étudiant.  
- Sécurisation par **JWT** avec rôles **ADMIN** et **USER** (droits différenciés).  
- Tests réalisés via **thunder Client** et intégration d’images de démonstration.  

---

##  Prérequis
- Java 17+  
- Maven  
- MySQL  

---

## Sécurité
Authentification par JWT.

Rôles :

ADMIN : gestion complète (CRUD sur étudiants, filières, modules, notes).

USER : consultation des bulletins et notes.


##  Organisation du projet

- **model/** : entités JPA (`Etudiant`, `Module`, `Note`, `Utilisateur`, `Filiere`)  
- **repository/** : interfaces JPA (`EtudiantRepository`, `ModuleRepository`, `UtilisateurRepository`, `NoteRepository`, etc.)  
- **service/** : logique métier (calcul de la moyenne, attribution de la mention, génération des bulletins)  
- **controller/** : API REST et contrôleurs Thymeleaf (`EtudiantController`, `NoteController`, `BulletinController`, etc.)  
- **dto/** : objets de transfert (`BulletinDTO`, `AuthResponse`, `LoginRequest`, etc.)  
- **security/** : configuration JWT et filtres (`JwtService`, `JwtAuthFilter`, `UserDetailsServiceImpl`, `SecurityConfig`)  
- **templates/** : vues Thymeleaf (`bulletins/liste.html`, `bulletins/detail.html`, `etudiants/liste.html`, etc.)  

## Captures d’écran pour test 


### * post : http://localhost:8082/auth/register


![alt text](images/image1.png)


### * post : http://localhost:8082/auth/login

![alt text](images/image2.png)


### * post : http://localhost:8082/filieres


![alt text](images/image3.png)


### * post : http://localhost:8082/module

![alt text](images/image5.png)

![alt text](images/image4.png)

![alt text](images/image6.png)

### * post : http://localhost:8082/etudiants

![alt text](images/image7.png)

 ### * post : http://localhost:8082/notes

 ![alt text](images/image8.png)

### * L'Interface http://localhost:8083/bulletins


![alt text](images/image9.png)


### * linterface d'un seul etudiant http://localhost:8083/bulletins/1


![alt text](images/image10.png)



##  Auteur

- **Nom** : Kawther  
- **class** : DSI2  
- **Projet** : Mini‑projet Spring Boot – Système de Gestion des Étudiants (SGE)  
 
- **Année universitaire** : 2025 – 2026  
