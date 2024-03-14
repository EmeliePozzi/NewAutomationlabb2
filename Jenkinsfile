pipeline {
    agent any

    stages {

        stage('Clean Workspace') {
            steps {
                cleanWs()
            }
        }
        
        stage('Checkout (Hämtar senaste kodversionen för den valda grenen)') {
            steps {
                git branch: "${params.branch}", url: 'https://github.com/EmeliePozzi/NewAutomationlabb2.git'
            }    
        }
        stage('Build trailrunnerProject') {
            steps {
                dir('labb2') {
                    
                    script {
                        sh 'mvn clean install'
                    }
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
        stage('Post Test from trailrunnerProject') {
            steps {
                dir('labb2') {
                    script {
                        junit '**/target/surefire-reports/*.xml'
                    }
                }
            }
        }
        stage('Run Robot and Post Test') {
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