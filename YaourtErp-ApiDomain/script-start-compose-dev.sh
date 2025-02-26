#!/usr/bin/env bash

# -- Vars
MODE=$1

# -- Check

if [[ -z $MODE ]]
then

  echo "Type 'start-db', 'start-all' (it start db and backend)', 'stop'  (it stop backend and db), or 'down' (stop backend, db and remove volumes)"
  read MODE

fi

# -- Exec

if [[ $MODE = 'start-db' ]]
then

  # -- Run
  docker image rm -f yaourt-erp/yaourt-erp-backend-apidomain-dev-docker:latest
  docker compose --env-file ./env/.env-docker-compose --progress plain build --no-cache
  docker compose --env-file ./env/.env-docker-compose up -d yaourtErpDatabaseDev1 yaourtErpDatabaseDev2 yaourtErpDatabaseDev3 databasePostSetup
  chmod 777 ./dev-tools/mongodb/script/mongodb_cluster_configuration.sh

elif [[ $MODE = 'start-all' ]]
then

  # -- Compile
  ./mvnw -Dmaven.test.skip clean package spring-boot:repackage

  # -- Run
  docker image rm -f yaourt-erp/yaourt-erp-backend-apidomain-dev-docker:latest
  docker compose --env-file env/.env-docker-compose --progress plain build --no-cache
  docker compose --env-file env/.env-docker-compose up -d
  chmod 777 ./dev-tools/mongodb/script/mongodb_cluster_configuration.sh

elif [[ $MODE = 'down' ]]
then

  # -- Stop
  docker compose --env-file env/.env-docker-compose down -v --rmi local

  # -- Remove image
  docker image rm -f yaourt-erp/yaourt-erp-backend-apidomain-dev-docker:latest

elif [[ $MODE = 'stop' ]]
then

  # -- Stop
  docker compose --env-file env/.env-docker-compose stop

  # -- Remove image
  docker image rm -f yaourt-erp/yaourt-erp-backend-apidomain-dev-docker:latest

else

  echo "Please choose either 'start-db', 'start-all' (it start db and backend)' or 'stop' (it stop backend and db)"

fi

