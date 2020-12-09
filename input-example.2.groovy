// 变量名，用于存储input步骤的返回值
// def approvalMap

pipeline {
    agent any
    environment {
      approvalMap    = ''
    }

    stages {
        stage('pre deploy') {
            steps {
                timeout(60) {
                    script {
                        approvalMap = input(
                        message: '准备发布到哪个环境？',
                        ok: '确定', 
                        // submitter: 'admin,admin2,releaseGroup',
                        submitterParameter: 'APPROVER',
                        parameters: [
                            choice(choices: 'dev\ntest\nprod', description: '发布到什么环境？', name: 'ENV'),
                            booleanParam(defaultValue: true, description: '', name: 'userFlag'),
                            string(name: 'DEPLOY_ENV', defaultValue: 'staging', description: ''),
                            text(name: 'DEPLOY_TEXT', defaultValue: 'One\nTwo\nThree\n', description: ''),
                            password(name: 'PASSWORD', defaultValue: 'SECRET', description: 'A secret password')


                            // string(defaultValue: '', description: '', name: 'myparam')
                            ]
                  
                        )
                    }
                }
            }
        }
        stage('deploy') {
            steps {
                // echo "操作者是 ${approvalMap['APPROVER']}"
                echo "发布到什么环境？${approvalMap}"
                // echo "自定义参数: ${approvalMap['myparam']}"
            }
        }
    }
}