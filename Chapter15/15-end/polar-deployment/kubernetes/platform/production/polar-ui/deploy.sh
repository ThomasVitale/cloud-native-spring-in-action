#!/bin/sh

set -euo pipefail

echo "\n📦 Deploying Polar UI..."

kubectl apply -f resources

echo "⌛ Waiting for Polar UI to be deployed..."

while [ $(kubectl get pod -l app=polar-ui | wc -l) -eq 0 ] ; do
  sleep 5
done

echo "\n⌛ Waiting for Polar UI to be ready..."

kubectl wait \
  --for=condition=ready pod \
  --selector=app=polar-ui \
  --timeout=180s

echo "\n📦 Polar UI deployment completed.\n"