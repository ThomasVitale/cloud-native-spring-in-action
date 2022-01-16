#!/bin/sh

set -euo pipefail

echo "\n🗝️  Keycloak deployment started.\n"

echo "📦 Installing Keycloak..."

kubectl apply -f resources/namespace.yml
sed "s/polar-keycloak-secret/$KEYCLOAK_CLIENT_SECRET/" resources/keycloak-config.yml | kubectl apply -f -
kubectl apply -f resources/keycloak-external-service.yml

echo "\n📦 Configuring Helm chart..."

helm repo add bitnami https://charts.bitnami.com/bitnami
helm upgrade --install polar-keycloak bitnami/keycloak \
  --set auth.adminUser=$KEYCLOAK_ADMIN_USERNAME \
  --set auth.adminPassword=$KEYCLOAK_ADMIN_PASSWORD \
  --values values.yml \
  --namespace keycloak-system

echo "\n⌛ Waiting for Keycloak to be deployed..."

sleep 15

while [ $(kubectl get pod -l app.kubernetes.io/component=keycloak -n keycloak-system | wc -l) -eq 0 ] ; do
  sleep 15
done

echo "\n⌛ Waiting for Keycloak to be ready..."

kubectl wait \
  --for=condition=ready pod \
  --selector=app.kubernetes.io/component=keycloak \
  --timeout=600s \
  --namespace=keycloak-system

echo "\n✅  Keycloak cluster has been successfully deployed."

echo "\n🔐 Your Keycloak Admin credentials...\n"

echo "Admin Username: $KEYCLOAK_ADMIN_USERNAME"
echo "Admin Password: $KEYCLOAK_ADMIN_PASSWORD"

echo "\n🔑 Generating Secret with Keycloak client secret."

kubectl delete secret polar-keycloak-client-credentials || true

kubectl create secret generic polar-keycloak-client-credentials \
    --from-literal=spring.security.oauth2.client.registration.keycloak.client-secret=$KEYCLOAK_CLIENT_SECRET

echo "\n🍃 A 'polar-keycloak-client-credentials' has been created for Spring Boot applications to interact with Keycloak."

echo "\n🗝️  Keycloak deployment completed.\n"
