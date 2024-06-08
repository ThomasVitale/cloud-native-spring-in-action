# Replacing Octant

[Octant](https://octant.dev) is the tool used in the book to provision a Kubernetes management console in your local environment.
Since the book got published, Octant stopped being maintained.

This guide describes two alternatives.

## Replacing Octant with HeadLamp

[HeadLamp] is an open-source and CNCF Sandbox project providing a convenient Kubernetes web UI. On your local environment, you can run it as a desktop application.

On macOS and Linux, you can install HeadLamp as follows.

```shell
brew install --cask headlamp
```

You can then open HeadLamp as any other desktop application on your machine.

Refer to the [official documentation](https://headlamp.dev/docs/latest/installation/) for more information.

## Replacing Octant with Kubernetes Dashboard

The Kubernetes project provides an optional dashboard that can be installed in a cluster. If you're using minikube like explained in the book, you can provision the dashboard via the official addon.

If you have already a cluster provisioned with minikube, you can access the dashboard UI with the following command.

```shell
minikube dashboard
```

Refer to the [official documentation](https://minikube.sigs.k8s.io/docs/handbook/dashboard/) for more information.
