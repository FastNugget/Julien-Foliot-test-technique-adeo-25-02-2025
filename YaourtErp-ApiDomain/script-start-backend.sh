#!/bin/bash

# -- To wait cluster parametrization in docker compose local
echo "Local docker compose environment detected, begin wait 15sec to cluster parametrization in docker compose local"
[ "$PROFILE" == 'dev-docker' ] && sleep 15
echo "End of wait, start backend !"

# -- Start app
java -jar backend.jar