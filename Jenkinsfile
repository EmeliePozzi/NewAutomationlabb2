pipeline {
    agent any

    stages {
        // Bygger projektet
        stage('Build trailrunnerProject') {
            steps {
                dir('labb2') {
                    bat 'mvn compile'
                }
            }
        }

        // Kör testerna
        stage('Test trailrunnerProject') {
            steps {
                dir('labb2') {
                    bat 'mvn test'
                }
            }
        }

        // Kör Robot Framework-testet
        stage('Run Robot framework tests') {
            steps {
                dir('Selenium') {
                    bat script: "robot --nostatusrc test.robot", returnStatus: true
                }
            }


            
            // Postar resultatet
            post {
                always {
                    jacoco(execPattern: 'labb2/target/*.exec',classPattern: 'labb2/target/classes/automation/labb',sourcePattern: 'labb2/src/main/java/automation/labb')
                    junit '**/target/surefire-reports/*.xml'
                    robot outputPath: 'Selenium/log', passThreshold: 80.0, unstableThreshold: 70.0, onlyCritical: false
                }
            }
        }
    }
}
