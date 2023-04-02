# Working with Windows

This guide shares some tips you might find helpful when using a Windows machine to follow along the samples in the book.

My recommendation is to use a WSL2 environment so to run all the samples in a Linux environment within Windows. Such a setup has been used to test all the samples from the book.

## Fix file encoding between Windows and WSL2

When sharing data between Windows and Linux (WSL2), you might encounter some issues related to file encoding. For example, if you keep your source code on a Windows file path and then you run the application from WSL2, you might experience errors about `./gradlew` or `/bin/sh` not existing.

If that's the case, install the [dos2unix](https://dos2unix.sourceforge.io) tool in your WSL2 environment.

```shell
sudo apt install dos2unix
```

Let's say the errors are related to the Catalog Service project. You can fix the file encoding as follows.

```shell
dos2unix catalog-service/**
```

## Using Tilt on Windows

In the book, I assume you run Tilt from WSL2 in case you work on a Windows machine. If you want to run Tilt from Windows directly, a couple of changes are required to the `Tiltfile`:

1. replace `./gradlew` with `gradlew` to run Gradlew from the BAT script
1. replace `$EXPECTED_REF` with `%EXPECTED_REF%` to get the value for this environment variable using the Windows syntax.

```python
custom_build(
    ref = 'catalog-service',
    command = 'gradlew bootBuildImage --imageName %EXPECTED_REF%',
    deps = ['build.gradle', 'src']
)
```
