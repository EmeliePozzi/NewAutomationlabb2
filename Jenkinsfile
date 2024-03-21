pipeline {
    agent any

    stages {
        stage('Clean Workspace') {
            steps {
                cleanWs()
            }
        }

        stage('Build trailrunnerProject') {
            steps {
                dir('labb2') {
                    script {
                        sh 'mvn compile'
                    }
                }
            }
            post {
                success {
                    echo 'Byggsteg slutfört utan fel.'
                    junit '**/target/surefire-reports/*.xml'
			        jacoco(execPattern: '**/labb2/target/*.exec', classPattern: '**/labb2/target/classes/automation/labb',sourcePattern: '**/labb2/src/main/java/automation/labb')
                }
                failure {
                    echo 'Byggsteg misslyckades. Vidta åtgärder.'
                }
            }
        }

        stage('Test trailrunnerProject') {
            steps {
                dir('labb2') {
                    script {
                        sh 'mvn test'
                    }
                }
            }
        }

        stage('Run Robot framework tests') {
            steps {
                dir('Selenium') {
                    script {
                        sh script: "robot --nostatusrc test.robot", returnStatus: true
                    }
                }
            }
        }
    }

    post {
        always {
            robot outputPath: 'Selenium/log', passThreshold: 80.0, unstableThreshold: 70.0, onlyCritical: false
        }
    }
}
