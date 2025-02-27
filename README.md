# YaourtErp
___


### This is a mono repo
### 👉🏻 Open [YaourtErp-ApiDomain]

- to start the mongo cluster, run ```./script-start-compose-dev.sh start-db```
- then start the server<br>

### 👉🏻 Open [YaourtErp-front]
- start the webserver

### Now enjoy the app at http://localhost:5173/
<br>

___
### Here is the subject of the project
Sujet FullStack : Optimisation de Stock de Yaourts
Vous souhaitez optimiser la gestion de votre stock de yaourts dans votre réfrigérateur en
automatisant la recommandation des quantités à acheter. Cette recommandation sera basée
sur la consommation quotidienne, le jour d'achat, un délai de livraison fixe et un format de
paquet de yaourts.
Fonctionnalités attendues :
1. Gestion des données :
   ○ Historique de consommation : Par défaut, votre famille consomme 3 yaourts
   par jour du lundi au vendredi, et 4 yaourts par jour le samedi et le dimanche.
   ○ Jour d'achat : Vous effectuez vos achats sur internet le dimanche.
   ○ Délai de livraison : Un délai de livraison fixe de 2 jours est utilisé, ce qui signifie
   que les yaourts achetés le dimanche seront livrés le mardi.
   ○ Format du paquet de yaourt : 2 yaourts par paquet (vous ne pouvez pas acheter
   de yaourts à l’unité)

2. Calcul de la quantité optimale à acheter :
   ○ Déterminez les quantités à acheter en partant du dimanche 6 janvier 2025 (pour
   une durée de un an), pour éviter les ruptures de stock tout en ne surchargeant
   pas votre frigo.
   ○ Le stock de départ est de 6 yaourts.

3. Interface utilisateur :
   ○ Permettre à l'utilisateur de modifier le délai de livraison, le profil
   hebdomadaire de consommation, et de lancer le calcul des quantités à
   acheter.
   ○ Afficher les résultats dans un tableau (dates et quantités à acheter).
   ○ Afficher une courbe des stocks quotidiens.

4. Recherche d'un multiple alternatif :
   ○ Tester l'existence d'un autre multiple de paquet de yaourts (supérieur à 4) qui
   permettrait de réduire le stock moyen tout en évitant les ruptures de stock.

L'idée est de gérer de manière optimale votre stock de yaourts dans votre réfrigérateur, afin de
toujours avoir suffisamment de yaourts sans excéder la capacité de stockage de votre frigo.
Contraintes:
● Java SpringBoot / Vuejs
Critères d'évaluation techniques:
● Organisation et lisibilité du code.
● Test