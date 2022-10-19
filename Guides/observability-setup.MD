# Observability Setup on Kubernetes

As a prerequisite, install the Observability stack kustomization.

```bash
kubectl apply -k observability-stack
```

Install Grafana, Loki, Prometheus, and Fluent Bit:

```bash
helm upgrade --install loki-stack --namespace=observability-stack grafana/loki-stack \
  --values observability-stack/helm/loki-stack-values.yml
```

Install Tempo:

```bash
helm upgrade --install tempo --namespace=observability-stack grafana/tempo \
  --values observability-stack/helm/tempo-values.yml
```

Get the Grafana admin password:

```bash
kubectl get secret --namespace observability-stack loki-stack-grafana -o jsonpath="{.data.admin-password}" | base64 --decode ; echo
```

Expose Grafana to your local machine:

```bash
kubectl port-forward --namespace observability-stack service/loki-stack-grafana 3000:80
```
