pipelineJob('test-job') {
  definition {
    cpsScm {
      scm {
        git {
          remote {
            url('https://github.com/tinexw/jenkins-pipeline-test')
          }
          branch('master')
          extensions {}
        }
      }
      scriptPath('Jenkinsfile')
    }
  }
}
