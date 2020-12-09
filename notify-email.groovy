pipeline {
  agent any
  tools {
    maven 'mvn-3.5.4'
  }

  stages {
      stage('code analysis') {
        steps {
            mail bcc: '', body: 'build success', cc: '', charset: 'UTF-8', from: 'jenkinsbooksample@163.com', replyTo: '', subject: 'build status', to: 'jenkinsbooksample@163.com'
        }
      }
  }
}