['messaging-app', 'user-service'].each {
    def jobName = it
    pipelineJob(jobName) {
        definition {
            cpsScm {
                scm {
                    git {
                        remote {
                            url('https://github.com/tinexw/cdc-with-pact')
                        }
                        branch('master')
                        extensions {}
                    }
                }
                scriptPath("$jobName/Jenkinsfile")
            }
        }
    }
}