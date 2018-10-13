def gitUrl = 'https://github.com/tinexw/cdc-with-pact'

['messaging-app', 'user-service'].each {
    def app = it
    pipelineJob("$app-build-and-deploy") {
        definition {
            cpsScm {
                scm {
                    git {
                        remote {
                            url(gitUrl)
                        }
                        branch('master')
                        extensions {}
                    }
                }
                scriptPath("$app/jenkins/cd/Jenkinsfile")
            }
        }
    }
}

pipelineJob("user-service-run-contract-tests") {
    definition {
        cpsScm {
            scm {
                git {
                    remote {
                        url(gitUrl)
                    }
                    branch('master')
                    extensions {}
                }
            }
            scriptPath("user-service/jenkins/cd/Jenkinsfile-contract-tests")
        }
    }
}