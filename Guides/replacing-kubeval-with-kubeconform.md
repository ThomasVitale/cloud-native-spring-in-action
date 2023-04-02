# Replacing Kubeval with Kubeconform

[Kubeval](https://github.com/instrumenta/kubeval) is the tool used in the book to validate the conformance of Kubernetes manifests. Since the book got published, Kubeval stopped being maintained. All the examples still work and run successfully, so you don't need to change anything.

Should you prefer replacing Kubeval with an actively developed tool (highly recommended for production scenarios), you can use [Kubeconform](https://github.com/yannh/kubeconform) as a drop-in replacement.

## Installing Kubeconform

On macOS and Linux, you can install Kubeconform as follows.

```shell
brew install kubeconform
```

Refer to the [official documentation](https://github.com/yannh/kubeconform) for more information.

## Using Kubeconform in GitHub Actions

The `commit-stage.yml` workflow used for all the applications built throughout the book uses Kubeval to validate Kubernetes manifests.

You can replace this step in _all_ applications:

```yaml
- name: Validate Kubernetes manifests
  uses: stefanprodan/kube-tools@v1
  with:
    kubectl: 1.26.3
    kubeval: 0.16.1
    command: |
      kubeval --strict k8s
```

with the following step (also in Edge Service):

```yaml
- name: Validate Kubernetes manifests
  uses: stefanprodan/kube-tools@v1
  with:
    kubectl: 1.26.3
    kubeconform: 0.6.1
    command: |
      kubeconform --strict k8s
```

When using Kustomize (starting from chapter 14), this is the new configuration to use with Kubeconform:

```yaml
- name: Validate Kubernetes manifests
  uses: stefanprodan/kube-tools@v1
  with:
    kubectl: 1.26.3
    kubeconform: 0.6.1
    command: |
      kustomize build k8s | kubeconform --strict -
```
