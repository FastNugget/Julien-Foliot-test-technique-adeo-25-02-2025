FROM eclipse-temurin:21

## -- Create base repository
RUN mkdir /opt/app_repository
WORKDIR /opt/app_repository

## -- Create user/groups and so on
RUN groupadd -g 666 app_group
RUN useradd -rm -s /bin/bash -g app_group -G sudo -u 666 app_user
RUN chown app_user /opt/app_repository
RUN chgrp app_group /opt/app_repository

## -- Switch (nintendo) user
USER app_user

## -- Build repo
COPY --chown=app_user:app_group target/backend.jar .
COPY --chown=app_user:app_group src/main/resources/* .
COPY --chown=app_user:app_group script-start-backend.sh .

## -- Add rights
## It seem to be a bug on kube, initally, it was 700 to let all right to app_user
## It work on local environment, but on kube, it say error :'canno't access jarfil'
## So for the moment, we just let full read execute right, no possiblity to write, so it's ok
RUN chmod -R 555 /opt/app_repository

## -- Debug
RUN echo 'List files in /opt/app_repository'
RUN ls -al

## -- Let's go boy
WORKDIR /opt/app_repository

## -- Create script (avoid mounting it on build)
ENTRYPOINT ["/opt/app_repository/script-start-backend.sh"]