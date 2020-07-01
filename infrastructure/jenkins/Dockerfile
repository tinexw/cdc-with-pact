FROM jenkins/jenkins:2.242

# Install required plugins
RUN /usr/local/bin/install-plugins.sh git:3.12.1 blueocean:1.8.4 job-dsl:1.70 workflow-aggregator:2.6

# Skip initial setup
ENV JAVA_OPTS -Djenkins.install.runSetupWizard=false
