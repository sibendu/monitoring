export DOCKER_USER=sibendu

cd ..

cd coms-api-flow
docker build -t $DOCKER_USER/coms-api-flow .
docker push $DOCKER_USER/coms-api-flow

cd ..

cd coms-api-user
docker build -t $DOCKER_USER/coms-api-user .
docker push $DOCKER_USER/coms-api-user

cd ..

cd coms-ui
docker build -t $DOCKER_USER/coms-ui .
docker push $DOCKER_USER/coms-ui
