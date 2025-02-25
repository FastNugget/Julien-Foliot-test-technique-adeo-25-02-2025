#!/bin/bash

## -- FUNCTIONS

function initiate() {

  mongosh --host "$1":"$2" --eval "rs.initiate({
   _id: \"myReplicaSet\",
   members: [
     {_id: 0, host: \"yaourtErpDatabaseDev1\"},
     {_id: 1, host: \"yaourtErpDatabaseDev2\"},
     {_id: 2, host: \"yaourtErpDatabaseDev3\"}
   ]
  })"

}

function setPrimaryReplica() {

    mongosh --host "$1":"$2" --eval "
    cfg = rs.conf()
    cfg.members[0].priority = 1
    cfg.members[1].priority = 0.5
    cfg.members[2].priority = 0.5
    rs.reconfig(cfg)
    "
}


## -- MAIN
sleep 7

## -- Initiate replica set
initiate yaourtErpDatabaseDev1 27017
initiate yaourtErpDatabaseDev2 27017
initiate yaourtErpDatabaseDev3 27017

sleep 3

## -- Set 1 node as primary replica (you connect your compass to it, 27017) // --> optional || echo "" 2>&1 >/dev/null
setPrimaryReplica yaourtErpDatabaseDev1 27017
setPrimaryReplica yaourtErpDatabaseDev2 27017
setPrimaryReplica yaourtErpDatabaseDev3 27017