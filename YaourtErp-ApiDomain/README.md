# OneTranslate-ApiDomain

#### This project act as part of OneTranslate stack, as a microservice for OneTranslate-ApiDomain <br>

---------
<U>Infos:</U>  
Swagger-ui: http://localhost:8080/swagger-ui/index.html#/  
Swagger open-api spec JSON: http://localhost:8080/api-docs  
Swagger open-api spec YAML: http://localhost:8080/api-docs.yaml

---------
### Update swagger for docapi
Execute the script [script-update-swagger-doc-api.sh](script-update-swagger-doc-api.sh)  <br> <br>
_Pay attention to start the backend before running the script_


---------
### Credentials
###### Credentials are set in env variable, thus you need a '.env' file with required environment variables.
###### Put it on at OneTranslate-ApiDomain env directory like 'env/.env' (it is in the .gitignore to avoid push on github)
###### Make it available in intellij by going Edit Configuration > Modify options > Operating System > Environment Variables
###### You can find a skeleton file here 'env/.env-skeleton', it contains all the keys you need

--------- 
### FlyWay
###### Doc: https://documentation.red-gate.com/flyway/flyway-cli-and-api/supported-databases/mongodb <br>
###### Mandatory jar at repo root (mvn repo version is bugged)
```bash

# /!\ Don't forget to add the jar mongo-jdbc-standalone-1.19.jar in your .gitignore
wget https://github.com/DataGrip/mongo-jdbc-driver/releases/download/v1.19/mongo-jdbc-standalone-1.19.jar
```

```bash
# Install the jar in your local .m2 repo
mvn install:install-file -Dfile=mongo-jdbc-standalone-1.19.jar -DgroupId=com.github.kornilova203 -DartifactId=mongo-jdbc-driver -Dversion=1.19 -Dpackaging=jar -DgeneratePom=true
```


---------
### Start swagger standalone (pay attention to give rights to the script)
##### Start the container
```bash
cd dev-tools/swagger-open-api
./script-swagger.sh start
```

##### Stop the container
```bash
cd dev-tools/swagger-open-api
./script-swagger.sh stop
```
