#!/bin/sh

echo "\n-----------------------------------------------------\n"

echo "🍃 Starting Polar Bookshop deployment"

echo "\n-----------------------------------------------------\n"

echo "🍃 Deploying Config Service..."

kubectl apply -f ../../../config-service/k8s

sleep 15

echo "\n-----------------------------------------------------\n"

echo "🍃 Deploying Dispatcher Service..."

kubectl apply -f ../../../dispatcher-service/k8s

sleep 15

echo "\n-----------------------------------------------------\n"

echo "🍃 Deploying Catalog Service..."

kubectl apply -f ../../../catalog-service/k8s

sleep 15

echo "\n-----------------------------------------------------\n"

echo "🍃 Deploying Order Service..."

kubectl apply -f ../../../order-service/k8s

sleep 15

echo "\n-----------------------------------------------------\n"

echo "🍃 Deploying Edge Service..."

kubectl apply -f ../../../edge-service/k8s

sleep 15

echo "\n-----------------------------------------------------\n"

echo "🍃 Deployment completed"
