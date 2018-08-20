# Infrastructure

This folder contains everything to set up a pact broker (with postgres) and
a Jenkins with all consumer and provider jobs.

Prerequisites:
- docker-compose

Steps:
- ``cd infrastructure``
- ``cp -r jenkins/jenkins_home_init jenkins/jenkins_home``
- ``docker-compose up``

You should now be able to access Jenkins on <http://localhost:8080> and the Pact Broker on <http://localhost>.

The Jenkins should contain build jobs for all consumers and build and verifyPactContract jobs for each provider.
