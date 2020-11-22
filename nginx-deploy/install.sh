mkdir /web && chmod 777 /web
cp colors.html /web/index.html
kubectl apply -f deploy-nginx.yml
kubectl port-forward deployments/nginx --address 0.0.0.0 80:80