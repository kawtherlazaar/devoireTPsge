# Système de Gestion des Étudiants (SGE)

Le projet SGE est une application web développée avec Spring Boot permettant la gestion des étudiants, des modules, des notes et des bulletins universitaires.

L’application offre une interface permettant :

* la gestion des étudiants,
* l’ajout et la consultation des modules,
* l’enregistrement des notes,
* le calcul automatique des moyennes,
* la génération des bulletins de notes,
* l’affichage des résultats (Admis / Non admis).

Le système utilise :

* Spring Boot pour le backend,
* Thymeleaf pour les interfaces web,
* MySQL comme base de données,
* JPA / Hibernate pour la persistance des données.

Chaque étudiant possède plusieurs notes associées à différents modules avec leurs coefficients. Le système calcule automatiquement la moyenne générale ainsi que la mention.

Le projet permet également :

* l’affichage détaillé des bulletins,
* la navigation via navigateur ou Postman,
* une architecture MVC organisée avec :

 * Controllers,
 * Services,
 * Repositories,
 * DTO,
 * Templates HTML.

Ce projet a pour objectif de simplifier la gestion pédagogique et automatiser la génération des bulletins universitaires.

ex3:
 post :http://localhost:8081/etudiants

![img1.png](postex3.png)

get :http://localhost:8081/etudiants/1


![img2.png](img.png)

put :http://localhost:8081/etudiants/1
![img_3.png](img_2.png)

delete:http://localhost:8081/etudiants/1
![img_4.png](img_3.png)

ex4:

get :http://localhost:8081/etudiants/recherche?nom=ben

![img_4.png](img_4.png)

get:http://localhost:8081/etudiant/groupe?groupe=DSI2
![img_5.png](img_5.png)

get: http://localhost:8081/etudiant/admis?seuil=10
![img_6.png](img_6.png)

get 1:http://localhost:8081/etudiant/meilleurs?seuil=14
![img_7.png](img_7.png)

post:http://localhost:8081/etudiant
![img_9.png](img_9.png)
![img_10.png](img_10.png)
get 2:http://localhost:8081/etudiant/meilleurs?seuil=14
![img_11.png](img_11.png)


ex5 :
post : http://localhost:8081/modules
![img_12.png](img_12.png)

get: http://localhost:8081/modules

![img_13.png](img_13.png)

get par id:http://localhost:8082/modules/2/etudiant
![img_14.png](img_14.png)

delete : http://localhost:8082/modules/2
![img_16.png](img_16.png)

ex6:
get :http://localhost:8082/etudiant/page?page=0&size=2&sortBy=moyenne

![img_17.png](img_17.png) resltat partie1


![img_18.png](img_18.png) resltat partie2

![img_19.png](img_19.png) resltat partie 3

* post :http://localhost:8082/auth/register -->user
![img_20.png](img_20.png)
* post:http://localhost:8082/auth/login 
![img_21.png](img_21.png)

+ post :http://localhost:8082/auth/register  -->admin
![img_22.png](img_22.png)
+ post :http://localhost:8082/auth/login 
+ ![img_23.png](img_23.png)

*post: http://localhost:8082/notes 
![img_24.png](img_24.png)



le resultat de projet :
![img_25.png](img_25.png)

![img_26.png](img_26.png)

