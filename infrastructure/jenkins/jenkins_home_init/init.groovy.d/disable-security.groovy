#!groovy
import jenkins.model.Jenkins

def instance = Jenkins.getInstance()

Jenkins.getInstance().disableSecurity()

instance.save()
