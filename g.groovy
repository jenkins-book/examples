pipeline {
  agent any
  tools {
    maven 'mvn-3.5.4'
  }

  stages {
      stage('code analysis') {
        steps {
            sh '''
              mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.4.1.1168:sonar \
              -Dsonar.host.url=http://192.168.1.8:9000 \
              -Dsonar.login=365cc68f8819053bac043d35ae5abbd13e3a6799
              '''
        }
      }
  }
}