FROM jenkins/jenkins:2.263.2

# Install required plugins
RUN /usr/local/bin/install-plugins.sh git:4.5.2 blueocean:1.24.3 job-dsl:1.77

# Skip initial setup
ENV JAVA_OPTS -Djenkins.install.runSetupWizard=false
