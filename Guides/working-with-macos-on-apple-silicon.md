# Working with macOS on Apple Silicon

This guide shares some tips you might find helpful when using a macOS machine on Apple Silicon (ARM64) to follow along the samples in the book.

## Using Cloud Native Buildpacks on ARM64

Cloud Native Buildpacks doesn't currently provide official support for ARM64. There's work in progress to achieve that in the [Paketo](https://paketo.io) implementation of Buildpacks, which is the one used in the book.

By default, Spring Boot uses the builder image from the Paketo Buildpacks project. If you work on ARM64 machines, you can use the experimental ARM support in Paketo.

In each Spring Boot project, you can configure Buildpacks in your `build.gradle` file as follows:

```groovy
bootBuildImage {
    if (System.getProperty( "os.arch" ).toLowerCase().startsWith('aarch')) {
        builder = "paketobuildpacks/builder-jammy-buildpackless-tiny"
        buildpacks = [ "gcr.io/paketo-buildpacks/java" ]
    } else {
        builder = "docker.io/paketobuildpacks/builder-jammy-tiny"
    }
}
```

Alternatively, you can provide the builder image via CLI:

```groovy
./gradlew bootBuildImage --builder paketobuildpacks/builder-jammy-buildpackless-tiny --buildpack gcr.io/paketo-buildpacks/java
```

If you configure the `bootBuildImage` task directly in the `build.gradle` file, you won't need any additional change. If you don't, then you need to remember to add the extra parameter when running the command from the CLI and also update the `Tiltfile` for each Spring Boot application.

```python
custom_build(
    ref = 'catalog-service',
    command = './gradlew bootBuildImage --builder paketobuildpacks/builder-jammy-buildpackless-tiny --buildpack gcr.io/paketo-buildpacks/java --imageName $EXPECTED_REF',
    deps = ['build.gradle', 'src']
)
```
