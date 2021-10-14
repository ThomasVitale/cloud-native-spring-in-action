#!/bin/sh

echo "\n-----------------------------------------------------\n"

echo "📦 Deploying platform services..."

kubectl apply -f platform

echo "\n-----------------------------------------------------\n"

echo "📦 Deployment completed"
