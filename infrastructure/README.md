# Infrastructure

This folder contains everything to set up a pact broker (with postgres) and
a Jenkins with all consumer and provider jobs. The jobs are created with the [Job DSL plugin](https://jenkinsci.github.io/job-dsl-plugin/)

Prerequisites:
- docker-compose

Steps:
- ``cd infrastructure``
- ``cp -r jenkins/jenkins_home_init jenkins/jenkins_home``
- ``docker-compose up``

You should now be able to access Jenkins on <http://localhost:8080> and the Pact Broker on <http://localhost>.

The Jenkins contains:
- run-contract-tests job for provider that only executes the contract tests
- build-and-deploy job for consumer and provider (Continuous Deployment case)
- separate build and deploy jobs for consumer and provider each (manual production deployment case)
- branch build job for consumer
