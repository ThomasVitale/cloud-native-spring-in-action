# Working with macOS on Apple Silicon

This guide shares some tips you might find helpful when using a macOS machine on Apple Silicon (ARM64) to follow along the samples in the book.

## Using Cloud Native Buildpacks on ARM64

Cloud Native Buildpacks doesn't currently provide official support for ARM64. There's work in progress to achieve that in the [Paketo](https://paketo.io) implementation of Buildpacks, which is the one used in the book.

By default, Spring Boot uses the builder image from the Paketo Buildpacks project. If you work on ARM64 machines, you can use the `ghcr.io/thomasvitale/java-builder-arm64` image based on the [work](https://dashaun.com/posts/teamwork-makes-the-dream-work-for-multiarch-builder/) done by Dashaun Carter and the Paketo community.

In each Spring Boot project, you can configure Buildpacks in your `build.gradle` file as follows:

```groovy
bootBuildImage {
    if (System.getProperty( "os.arch" ).toLowerCase().startsWith('aarch')) {
	    builder = "ghcr.io/thomasvitale/java-builder-arm64"
    }
    builder = "paketobuildpacks/builder:tiny"
}
```

Alternatively, you can provide the builder image via CLI:

```groovy
./gradlew bootBuildImage --builder ghcr.io/thomasvitale/java-builder-arm64
```

If you configure the `bootBuildImage` task directly in the `build.gradle` file, you won't need any additional change. If you don't, then you need to remember to add the extra parameter when running the command from the CLI and also update the `Tiltfile` for each Spring Boot application.

```python
custom_build(
    ref = 'catalog-service',
    command = './gradlew bootBuildImage --builder ghcr.io/thomasvitale/java-builder-arm64 --imageName $EXPECTED_REF',
    deps = ['build.gradle', 'src']
)
```
