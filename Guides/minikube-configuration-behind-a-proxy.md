# Minikube configuration behind a proxy

In some scenarios, you might need to install Minikube behind a proxy. For example, if you're in China, you cannot access the Kubernetes image repository directly (`registry.k8s.io`).

Here's some information on how to configure Minikube for this scenario:

* [How to use minikube with a VPN or HTTP/HTTPS Proxy](https://minikube.sigs.k8s.io/docs/handbook/vpn_and_proxy/)
* [Configure the Docker daemon to use a proxy server](https://docs.docker.com/config/daemon/systemd/#httphttps-proxy).

For more information on how to configure Minikube from China, refer to [these instructions](https://github.com/kubernetes/minikube/issues/16828#issuecomment-1716160177) kindly shared by @ongiant.
