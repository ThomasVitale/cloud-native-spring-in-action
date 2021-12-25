#!/bin/sh

echo "\n🏴‍☠️ Destroying Kubernetes cluster..."

kind delete cluster --name polar-cluster

echo "\n🏴‍☠️ Cluster destroyed\n"
