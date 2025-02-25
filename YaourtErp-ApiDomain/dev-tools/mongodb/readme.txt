# Pour lancer le cluster, executer le script
script-start-compose-mongodb-cluster.sh

# INFOS: If you wanna check replicaset status after start compose ->
 docker exec -it onetranslate-database-dev-1 mongosh --eval "rs.status()"

