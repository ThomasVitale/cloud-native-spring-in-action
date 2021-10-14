#!/bin/sh

echo "\n-----------------------------------------------------\n"

echo "🏴‍☠️ Destroying Kubernetes cluster..."

kind delete cluster --name polar-cluster

echo "\n"

echo "🏴‍☠️ Cluster destroyed"
