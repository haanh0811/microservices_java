#!/bin/bash

# Démarrer le service Docker
sudo service docker start

# Démarrer Minikube avec le pilote Docker
minikube start --driver=docker

# Configurer l'environnement Docker pour Minikube
eval $(minikube docker-env)

# Appliquer les configurations Kubernetes
kubectl apply -f pod.yml
kubectl apply -f app-deployment.yml
kubectl apply -f app-service.yml

# Exposer le service de l'application Spring Boot
minikube service spring-app-service
