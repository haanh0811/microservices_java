* CLI pour déployer :
sudo service docker start
\
minikube start --driver=docker
\
eval $(minikube docker-env)
\
kubectl apply -f pod.yml
\
kubectl apply -f app-deployment.yml
\
kubectl apply -f app-service.yml
\
minikube service spring-app-service

* URL:  http://127.0.0.1:34779/api/spec/all
* Tutoriel : https://www.javainuse.com/devOps/kubernetes/kub5
