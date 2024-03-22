pipeline {
    agent any

  stages {

	  
	//Rensar upp och tar bort gamla tillfälliga filer(clean) och kör compile och test(install) Skulle eventuellt vara mvn compile istället
        stage('Build trailrunnerProject') {
            steps {
                dir('labb2') {
                        bat 'mvn clean install'
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
			robot outputPath: 'Selenium/log', passThreshold: 80.0, unstableThreshold: 70.0, onlyCritical: false
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




	
	//Postar resultatet av enhetstesterna - oavsett om de lyckats eller inte.
	post {
		always {
			 junit '**/target/surefire-reports/*.xml'
		}
	}
}

