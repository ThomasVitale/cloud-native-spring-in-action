# Knative Platform Cloud Installation

In the book, you learned how to install Knative locally and run your Spring Boot applications as workloads that can scale to zero, suitable for serverless architectures.

This guide shows you how to set up a Knative-based platform on a Kubernetes cluster in the cloud using the convenient open-source platform I develop at [Kadras](https://github.com/kadras-io), which configures everything for you to deploy easily Spring Boot applications and serve them over HTTPS. For the Kubernetes cluster, you can follow the same instructions in the book to set up a Kubernetes cluster on Digital Ocean.

## Before you begin

To follow the guide, Ensure you have the following tools installed in your local environment:

* Kubernetes [`kubectl`](https://kubectl.docs.kubernetes.io/installation/kubectl)
* Carvel [`kctrl`](https://carvel.dev/kapp-controller/docs/latest/install/#installing-kapp-controller-cli-kctrl)
* Carvel [`kapp`](https://carvel.dev/kapp/docs/latest/install).

## Deploy Carvel kapp-controller

The platform relies on the Kubernetes-native package management capabilities offered by Carvel [kapp-controller](https://carvel.dev/kapp-controller). You can install it with Carvel [`kapp`](https://carvel.dev/kapp/docs/latest/install) (recommended choice) or `kubectl`.

```shell
kapp deploy -a kapp-controller -y \
  -f https://github.com/carvel-dev/kapp-controller/releases/latest/download/release.yml
```

## Add the Kadras Package Repository

Add the Kadras repository to make the platform packages available to the cluster.

```shell
kctrl package repository add -r kadras-packages \
  --url ghcr.io/kadras-io/kadras-packages:0.18.0 \
  -n kadras-system --create-namespace
```

## Configure the Platform

The installation of the Kadras Engineering Platform can be configured via YAML. Create a `values.yml` file with any configuration you need for the platform. The following is a minimal configuration example for a cloud environment, based on the `run` installation profile.

```yaml title="values.yml"
platform:
  profile: run
  
  ingress:
    domain: <domain>
    issuer:
      type: letsencrypt
      email: <email>

contour:
  certificates:
    useCertManager: true
```

* `<domain>` is the base domain name the platform will use to configure the Ingress controller. It must be a valid DNS name. For example, `lab.thomasvitale.com`.
* `<email>` is an email address that [Let's Encrypt](https://letsencrypt.org) will use to issue a certificate for you to enable HTTPS in your cluster. It can be any email address you own.

## Install the Platform

Reference the `values.yml` file you created in the previous step and install the Kadras Engineering Platform.

```shell
kctrl package install -i engineering-platform \
  -p engineering-platform.packages.kadras.io \
  -v 0.16.0 \
  -n kadras-system \
  --values-file values.yml
```

## Verify the Installation

Verify that all the platform components have been installed and properly reconciled.

```shell
kctrl package installed list -n kadras-system
```

## Run an Application via the Knative CLI

You can now deploy your Spring Boot applications via the [Knative CLI]((https://knative.dev/docs/client)) on the platform you have just installed.

```shell
kn service create band-service \
  --image ghcr.io/thomasvitale/band-service \
  --security-context strict
```

The application will be available through a URL over HTTPS, protected by a TLS certificate issued by Let's Encrypt and with autoscaling capabilities (thanks to Knative). You can open the URL in the browser or use a CLI like [httpie](https://httpie.io).

```shell
https band-service.default.<domain>
```

Remember to replace `<domain>` with the DNS name you used in the `values.yml` file when installing the platform.

After testing the application, remember to delete it.

```shell
kn service delete band-service
```

## Uninstall the Platform

Once you're done trying out Knative in the cloud, you can go ahead and destroy the Kubernetes cluster. If you'd like to uninstall the platform only, you can do so as follows.

```shell
kctrl package installed delete -i engineering-platform \
  -n kadras-system \
```
