pipeline {
    agent any
	    
    stages {
    	stage ('Initialize') {
            steps {
                   echo "PATH = ${PATH}"
                   echo "M2_HOME = ${M2_HOME}"
                   echo "JAVA_HOME = ${JAVA_HOME}"
            }
        }
        stage('Build') {
            steps {
            	echo 'Building..'
                sh 'mvn -Dmaven.test.failure.ignore=true install' 
	        }
            post {
                success {
                    junit 'target/surefire-reports/**/*.xml' 
                }
            }
        }
        stage('Test') {
            steps {
                echo 'Testing..'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}