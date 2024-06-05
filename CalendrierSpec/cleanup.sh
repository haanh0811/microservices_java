#!/bin/bash

kubectl delete deployments --all
kubectl delete services --all
kubectl delete pods --all
minikube stop
sudo service docker stop
