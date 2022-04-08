# Applications deployment on a local Kubernetes cluster

Tilt is a convenient tool for setting up a local development workflow on Kubernetes.
We defined a Tiltfile in each application project to configure Tilt. When working on a specific
application, we can run `tilt up` from within the project.

In case we need to run all the applications locally, Tilt allows to combine multiple Tiltfiles
together and run multiple applications at once. If you want to do that, run `tilt up`
in this folder. Remember to start a local Kubernetes cluster and deploy the platform services first.

When each project is tracked in a separate Git repository, you can either ensure you check them out
in sibling folders on your localhost or let Tilt do that.

For more information, refer to [Many Tiltfiles and Many Repos](https://docs.tilt.dev/multiple_repos.html).
