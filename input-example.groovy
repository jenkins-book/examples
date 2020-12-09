pipeline {
  agent any
  stages {
      stage('deploy') {
        steps 
            input message: "发布或停止"
        }
      }
  }
}