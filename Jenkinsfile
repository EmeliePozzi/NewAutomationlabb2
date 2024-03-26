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
             post {
                always {
                    jacoco(execPattern: 'labb2/target/*.exec',classPattern: 'labb2/target/classes/automation/labb',sourcePattern: 'labb2/src/main/java/automation/labb')
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }

        // Kör Robot Framework-testet
        stage('Run Robot framework tests') {
            steps {
                dir('Selenium') {
                    bat "robot test.robot"
                }
            }


            
            // Postar resultatet
            post {
                always {
                    robot outputPath: 'C:/Users/Emelie/.jenkins/workspace/Emelie_pozzi_Redovisning/Selenium', passThreshold: 80.0
                }
            }
        }
    }
}
