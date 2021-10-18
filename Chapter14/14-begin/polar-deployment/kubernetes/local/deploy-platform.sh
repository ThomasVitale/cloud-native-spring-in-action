#!/bin/sh

echo "\n-----------------------------------------------------\n"

echo "🍃 Starting platform services deployment"

echo "\n-----------------------------------------------------\n"

echo "📦 Deploying Keycloak..."

kubectl apply -f platform/keycloak-config.yml
kubectl apply -f platform/keycloak.yml

sleep 5

kubectl wait \
  --for=condition=ready pod \
  -l app=polar-keycloak \
  --timeout=300s

echo "\n-----------------------------------------------------\n"

echo "📦 Deploying PostgreSQL Catalog..."

kubectl apply -f platform/postgresql-catalog.yml

sleep 5

kubectl wait \
  --for=condition=ready pod \
  -l db=polar-postgres-catalog \
  --timeout=90s

echo "\n-----------------------------------------------------\n"

echo "📦 Deploying PostgreSQL Order..."

kubectl apply -f platform/postgresql-order.yml

sleep 5

kubectl wait \
  --for=condition=ready pod \
  -l db=polar-postgres-order \
  --timeout=90s

echo "\n-----------------------------------------------------\n"

echo "📦 Deploying RabbitMQ..."

kubectl apply -f platform/rabbitmq.yml

sleep 5

kubectl wait \
  --for=condition=ready pod \
  -l db=polar-rabbitmq \
  --timeout=120s

echo "\n-----------------------------------------------------\n"

echo "📦 Deploying Redis..."

kubectl apply -f platform/redis.yml

sleep 5

kubectl wait \
  --for=condition=ready pod \
  -l db=polar-redis \
  --timeout=120s

echo "\n-----------------------------------------------------\n"

echo "📦 Deploying Spring Cloud Kubernetes Configuration Watcher..."

kubectl apply -f platform/config-watcher.yml

sleep 5

kubectl wait \
  --for=condition=ready pod \
  -l app=spring-cloud-kubernetes-configuration-watcher \
  --timeout=120s

echo "\n-----------------------------------------------------\n"

echo "📦 Deployment completed"
