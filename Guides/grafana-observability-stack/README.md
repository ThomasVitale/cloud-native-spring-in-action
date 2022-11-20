# Setting up the Grafana observability stack on Kubernetes

Run the following command to install the Grafana observability stack on your Kubernetes cluster. The stack
will be deployed in the `observability-stack` namespace.

```shell
./deploy.sh
```

Upon completing, the script will print the credentials you can use for logging into Grafana.
By default, the username is `user`. You can find out the password at any time by running the following
command:

```shell
kubectl get secret --namespace observability-stack loki-stack-grafana -o jsonpath="{.data.admin-password}" | base64 --decode; echo
```

You can access the Grafana console via port-fowarding to your local machine:

```shell
kubectl port-forward --namespace observability-stack service/loki-stack-grafana 3000:80
```

Now, you can access Grafana at http://localhost:3000.

Logs and metrics from your Spring Boot applications are automatically scraped and visualized in Grafana.

If you want to collect traces as well, you need to configure your applications to send traces
to Tempo at http://tempo.observability-stack.svc.cluster.local:4317. For example, you can add
the configuration via a Kustomize patch (as included in the production configuration
for the final project available [here](https://github.com/ThomasVitale/cloud-native-spring-in-action/tree/main/PolarBookshop/polar-deployment/kubernetes/applications)).

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: catalog-service
spec:
  template:
    spec:
      containers:
        - name: catalog-service
          env:
            - name: JAVA_TOOL_OPTIONS
              value: "-javaagent:/workspace/BOOT-INF/lib/opentelemetry-javaagent-1.17.0.jar"
            - name: OTEL_SERVICE_NAME
              value: catalog-service
            - name: OTEL_EXPORTER_OTLP_ENDPOINT
              value: http://tempo.observability-stack.svc.cluster.local:4317
            - name: OTEL_METRICS_EXPORTER
              value: none
```
