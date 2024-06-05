# microservices_java
Pour lancer le projet, veuillez démarrer chacun des 5 projets Spring Boot (dans les folders à la racine).
\
\
Pour tester les requêtes, veuillez vous diriger vers les swaggers suivants:
\
Service Calendrier Spectateur : http://localhost:8080/swagger-ui/index.html
\
Service Calendrier : http://localhost:5154/swagger-ui/index.html
\
Service Sport : http://localhost:5155/swagger-ui/index.html
\
Service Site : http://localhost:5153/swagger-ui/index.html
\
\
Un swagger illustrant tous les endpoints est disponible à l'adresse http://localhost:5212/webjars/swagger-ui/4.15.5/index.html. Cependant, il n'est pas possible de lancer les requêtes via cette URL.


## Connexion à la base de données Neo4j :
url : https://workspace-preview.neo4j.io/connection/connect
\
Protocol: neo4j+s://
\
Connection Url : bbcbf8ad.databases.neo4j.io:7687
\
Password : vuPOlsR9s9Vofen_GpzkQVejGBrVe9IWnYiXcAI3AZU
\
En cas d'erreur lors du lancement du service Site, cliquer sur Instance01 / neo4j et redémarrer la base.

## Connexion à la base de données MongoDB Atlas :
url : https://cloud.mongodb.com/v2/665084ddad061e534ca4a7cf#/metrics/replicaSet/6650854e7a3fdf4a5026170c/explorer/Olympic24
\
Connection :
- identifiant : hatrn.re@gmail.com
- mdp : MongoJava24
\
Database : Olympic24
\
Collections :  
- Sport2 : pour le microservice Sport
- spectateurs : pour le microservice Calendrier Spectateur
