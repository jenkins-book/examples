pipeline {
    agent any
    tools {
        maven 'mvn-3.5.4'
    }

    stages {
        stage('Code Analysis') {
            steps {
                git branch: "master", changelog: false, poll: false, url: 'https://github.com/gabrielf/maven-samples.git'
                dir('maven-samples'){
                  script{
                    def sonarHome = tool name: 'sonarqube3.2.01', type: 'hudson.plugins.sonar.SonarRunnerInstallation'
                      withSonarQubeEnv('sonar') {
                        sh "printenv"
                        sh "${sonarHome}/bin/sonar-scanner"
                    }
                  }
                  
                }
                
            }
        }

        stage("Quality Gate") {
            steps {
                timeout(time: 1, unit: 'HOURS') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
    }
}