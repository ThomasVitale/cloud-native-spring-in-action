#!/bin/sh

export RABBITMQ_USERNAME=$(kubectl get secret polar-rabbitmq-default-user -o jsonpath='{.data.username}' --namespace=polar-rabbitmq | base64 --decode)
export RABBITMQ_PASSWORD=$(kubectl get secret polar-rabbitmq-default-user -o jsonpath='{.data.password}' --namespace=polar-rabbitmq | base64 --decode)
export RABBITMQ_SERVICE=$(kubectl get service polar-rabbitmq -o jsonpath='{.spec.clusterIP}' --namespace=polar-rabbitmq)

kubectl run perf-test --image=pivotalrabbitmq/perf-test --namespace=polar-rabbitmq -- --uri amqp://$RABBITMQ_USERNAME:$RABBITMQ_PASSWORD@$RABBITMQ_SERVICE

unset RABBITMQ_USERNAME
unset RABBITMQ_PASSWORD
unset RABBITMQ_SERVICE