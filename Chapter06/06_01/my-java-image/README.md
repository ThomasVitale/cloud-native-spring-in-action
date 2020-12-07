# My Java Image

## Building and running images

**Build the image**

Run this command from the same folder where the Dockerfile is.

```bash
docker build -t my-java-image:1.0.0 .
```

**List the image layers**

```bash
docker history my-java-image:1.0.0
```

**Run image as a container**

```bash
docker run --rm --name my-java-container my-java-image:1.0.0
```

## Interacting with Docker Hub

**Login to Docker Hub**

Run this command and provide your Docker Hub username and password, if asked.

```bash
docker login
```

**Pull image from Docker Hub**

```bash
docker pull ubuntu:20.04
```

## Publishing your image on Docker Hub

**Tag image**

```bash
docker tag my-java-image:1.0.0 <your_docker_username>/my-java-image:1.0.0
```

**Push image to Docker Hub**

```bash
docker push <your_docker_username>/my-java-image:1.0.0
```

## Clean up

**Remove local image**

```bash
docker rmi -f <your_docker_username>/my-java-image:1.0.0
```
