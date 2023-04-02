# Configuring IntelliJ IDEA

When using IntelliJ IDEA Ultimate Edition, no additional configuration should be needed to run the code examples in the book. This guide describes a couple of optional settings.

## Support for processing custom properties

Depending on the IDEA version or the way the project is open in the IDE, additional settings are needed to get autocompletion and docs for custom configuration properties (introduced in chapter 4).

1. Go to Settings
1. Go to Build, Execution, Deployment > Compiler > Annotation Processors
1. Check "Enable annotation processing" and "Obtain processors from project classpath".

For more info, check how to [configure annotation processors](https://www.jetbrains.com/help/idea/annotation-processors-support.html) in IDEA.

## Kubernetes plugin

When working with Kubernetes manifests, you can install a dedicated plugin in IntelliJ IDEA to help you validate the syntax and identifying mistakes.

Check the [plugin documentation](https://plugins.jetbrains.com/plugin/10485-kubernetes) for instructions on how to install it.
