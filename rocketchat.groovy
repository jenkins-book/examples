pipeline {
  agent any
  stages {
      stage('deploy') {
        steps {
            echo "deploy"
        }
      }
  }
  post {
    always{
      rocketSend message: "Build Started - ${env.JOB_NAME} ${env.BUILD_NUMBER} (<${env.BUILD_URL}|Open>)",channel: 'general',emoji: ':sob:'
    }
  }
}