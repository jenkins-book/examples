pipeline {
    agent any
    stages {
        stage("deploy") {
            steps {
              error('xx')
            }
        }
    }
    post {
        always {
          script {
                  def version = VersionNumber versionPrefix: "${JOB_NAME}-", versionNumberString: 'v1.1.1.${BUILDS_ALL_TIME}',worstResultForIncrement: 'SUCCESS'
                  echo "${version}"
              }
        }
    }
}