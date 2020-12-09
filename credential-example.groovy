pipeline {
    agent any
    stages {
        stage('build') {
            steps {
                withCredentials([string(credentialsId: 'secretText', variable: 'secretText')]) {
                  echo "${secretText}"
                }
            }
        }
    }
}