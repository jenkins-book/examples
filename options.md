```groovy
pipeline {
    agent any
    options {
        timeout(time: 1, unit: 'HOURS') 
        buildDiscarder(logRotator(numToKeepStr: '1'))
    }
    stages {
        stage('build') {
            options {
                timeout(time: 1, unit: 'HOURS')
                retry(3)
            }
            steps {
                echo "hello world"
                error("Build failed because of this and that..")
            }
        }
    }
}
```
buildDiscarder(logRotator(numToKeepStr: '1'))