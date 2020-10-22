mkdir /web && chmod 777 /web
kubectl apply -f deploy-nginx.yml
kubectl port-forward deployments/nginx --address 0.0.0.0 80:80