# Working with macOS on Intel

This guide shares some tips you might find helpful when using a macOS machine on Intel (AMD64) to follow along the samples in the book.

## Loading Images to Minikube

When loading images to minikube with the command `minikube image load`, you might experience [issues](https://github.com/kubernetes/minikube/issues/18021) when using Docker 25+. Please, refer to these [workarounds](https://github.com/ThomasVitale/cloud-native-spring-in-action/issues/63) until the problem is fixed in the minikube project. 
