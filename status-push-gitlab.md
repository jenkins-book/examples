```groovy

pipeline {
    agent any
    triggers {
        gitlab(
        triggerOnPush: false,
        triggerOnMergeRequest: true, triggerOpenMergeRequestOnPush: "never",
        triggerOnNoteRequest: true,
        noteRegex: "Jenkins please retry a build",
        skipWorkInProgressMergeRequest: true,
        ciSkip: false,
        setBuildDescription: true,
        addNoteOnMergeRequest: true,
        addCiMessage: true,
        addVoteOnMergeRequest: true,
        acceptMergeRequestOnSuccess: false,
        branchFilterType: "NameBasedFilter",
        includeBranchesSpec: "release/qat",
        excludeBranchesSpec: "",
        pendingBuildName: "Jenkins",
        cancelPendingBuildsOnUpdate: false,
        secretToken: "abcdefghijklmnopqrstuvwxyz0123456789ABCDEF")
    }
    options {
        timeout(time: 1, unit: 'HOURS') 
        buildDiscarder(logRotator(numToKeepStr: '1'))
    }
    stages {
        stage('build') {
            steps {
                echo "hello world from gitlab trigger"
            }
        }
    }
}


```