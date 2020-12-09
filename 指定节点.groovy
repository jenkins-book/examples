pipeline {
    agent {
      label 'docker'
    }
    stages {
        stage('Build') {
            steps {
              echo "build"
            }
        }
    }
}