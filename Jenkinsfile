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
			 junit '**/target/surefire-reports/*.xml'

		}
	}

          
        
    
}
