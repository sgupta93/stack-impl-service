
# Steps to bring up the stack-impl-service application:

1) minikube start
2) docker pull sgupta108/stack-impl-service:stack-impl-service (Note: Docker image of the stack-impl-service application is pushed into docker hub registry)
3) kubectl create -f manifest.yml
4) kubectl get deploy
5) kubectl get po
6) POST http://"ip-address-of-the-node":3000/StackImplService/stack/push/765
7) DELETE http://"ip-address-of-the-node":3000/StackImplService/stack/pop


# stack-impl-service Endpoints:

// For pushing the item into stack and database\
POST http://"ip-address-of-the-node":3000/StackImplService/stack/push/765

// For popping the item from stack and database\
DELETE http://"ip-address-of-the-node":3000/StackImplService/stack/pop


# Docker Commands:

// For building the docker image\
docker build -t stack-impl-service .

// For tagging the docker image\
docker tag stack-impl-service sgupta108/stack-impl-service:stack-impl-service

// For pushing the docker image into remote repository i.e Docker Hub Registry\
docker push sgupta108/stack-impl-service:stack-impl-service

// For running the exposed stack-impl-service as a docker container\
docker run -d -p 8080:8080 --name stack-impl sgupta108/stack-impl-service:stack-impl-service

# Minikube Commads:

// To start the minikube\
minikube start

// Set the environment variable to run the docker image from local registry\
eval $(minikube docker-env)

// For Creating the maifest yaml file for deployment of the application\
kubectl create -f manifest.yml

// For deploying the stack-impl-service application\
kubectl get deploy

// To see the list of stack-impl-service application's nodes\
kubectl get po

// Unset the environment variable to run the docker image from local registry\
eval $(minikube docker-env -u)
