pipeline {

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Unit & Integration Tests') {
            steps {
                script {
                    try {
                        sh './gradlew clean test --no-daemon' //run a gradle task
                    } finally {
                        junit '**/build/test-results/test/*.xml' //make the junit test results available in any case (success & failure)
                    }
                }
            }
        }
        // stage('Publish Artifact to Nexus') {
        //     steps {
        //         sh './gradlew publish --no-daemon'
        //     }
        // }
    }
    
}