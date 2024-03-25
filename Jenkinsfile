pipeline {
    agent any

  stages {

	  
	//Bygger projektet
        stage('Build trailrunnerProject') {
            steps {
                dir('labb2') {
                        bat 'mvn compile'
                }
            }
        }




	  
	//Kör testerna
        stage('Test trailrunnerProject') {
            steps {
                dir('labb2') {
                        bat 'mvn test'
                }
            }
            //Postar resultatet av Trailrunner-testerna
            post {
		always {
			junit '**/target/surefire-reports/*.xml'
			jacoco(execPattern: '**/labb2/target/*.exec',classPattern: '**/labb2/target/classes/automation/labb',sourcePattern: '**/labb2/src/main/java/automation/labb')
		        }
	        }
        }




	  
        //kör Robot Framework-testet, förhindrar att statusinformation läggs till i testfilen och returnerar statuskoden för kommandot när det har körts.
        stage('Run Robot framework tests') {
            steps {
                 dir('Selenium') {
                        bat script: "robot --nostatusrc test.robot", returnStatus: true
                }
            }
	}
    }




	
	//Postar resultatet av robot framework-testerna - oavsett om de lyckats eller inte.
	post {
		always {
			robot outputPath: 'Selenium/log', passThreshold: 80.0, unstableThreshold: 70.0, onlyCritical: false
		}
	}
}

