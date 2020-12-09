@Library('zpipelinelib@master') _
pipeline {
    agent none
    tools {
        maven 'maven-3.5.2'
    }
    environment {
        __APP_NAME = 'reserve-service'
        __version = zGetVersion("${BUILD_NUMBER}", "${env.GIT_COMMIT}")
    }

    triggers {
    	gitlab(triggerOnPush: true, 
             triggerOnMergeRequest: true, 
             branchFilterType: 'All'
    }
    stages { 
        // 因为编译完成后，制品通常就在编译的那个Jenkins agent上，编译打包和上传制品，放在一个阶段就可以了

        stage('Build') {
          steps {
              zMvn("${__version}")
          }// steps
        } // build

        stage("Code analysis"){
          steps{
            codeAnalysis('')
          }
        }

        stage("upload artifact"){
          steps{
            uploadArtifact()
          }
        }

        stage("deploy to dev"){
          when {
              branch 'master'
          }
          steps{
            deployService("${__version}", 'dev')
          }
          post {
            always {
              notify()
            }
          }
        }
        
        stage("deploy to staging"){
          when {
              branch 'release-*'
          }
          steps{
            deployService("${__version}", 'staging')
          }
          post {
            always {
                notify()
            }
          }
        }

        stage("自动化集成测试"){
          when {
              branch 'release-*'
          }
          steps{
            accTest('staging')
          }
        }

        stage("Manual test"){
          when {
              branch 'release-*'
          }
          steps{
            input message: "手工测试是否通过"
          }
        }

        stage('pre deploy') {
          steps {
              script {
                  approvalMap = input(
                      message: '请输入部署密码',
                      ok: '确定', 
                      parameters: [
                          password(name: 'deployPassword', defaultValue: '', description: 'A secret password')
                      ], 
                      submitter: 'admin,admin2,releaseGroup',
                      submitterParameter: 'APPROVER'
                  )
              }
          }
          post {
            always {
                notify()
            }
          }
        }

        stage("deploy to prod"){
          when {
              branch 'release-*'
          }
          steps{
            script{ 
              withCredentials([string(credentialsId: 'secretText', variable: 'varName')]) {
                if($approvalMap['deployPassword'] == ${varName})
                  deployService("${__version}", 'prod')
              }
            }
          }
          post {
            always {
                notify()
            }
          }
        }
        stage("生产环境自验"){
          when {
              branch 'release-*'
          }
          steps{
            verify('prod')
          }
        }

    } // stages
    post {
        always {
            cleanWs()
        }
    }
}// pipeline
