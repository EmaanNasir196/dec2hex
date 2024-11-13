pipeline {
    agent any

    tools {
        jdk 'JDK11' // Ensure 'JDK11' is configured in Jenkins
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        
        stage('Compile') {
            steps {
                // Ensure JUnit JAR is downloaded before compilation
                sh '''
                    if [ ! -f junit-4.13.2.jar ]; then
                        wget https://repo1.maven.org/maven2/junit/junit/4.13.2/junit-4.13.2.jar
                    fi
                    if [ ! -f hamcrest-core-1.3.jar ]; then
                        wget https://repo1.maven.org/maven2/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar
                    fi
                '''
                sh 'javac -cp .:junit-4.13.2.jar Dec2Hex.java'
                sh 'javac -cp .:junit-4.13.2.jar:hamcrest-core-1.3.jar Dec2HexTest.java'
            }
        }
        
        stage('Test') {
            steps {
                // Run tests
                sh 'java -cp .:junit-4.13.2.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore Dec2HexTest'
            }
        }
        
        stage('Run') {
            steps {
                sh 'java Dec2Hex 15'
            }
        }
        
        stage('SonarQube Analysis') {
            steps {
                script {
                    def scannerHome = tool 'SonarQubeScanner' // Ensure 'SonarQubeScanner' is configured
                    withSonarQubeEnv('SonarQube') { // Ensure 'SonarQube' is configured in Jenkins
                        sh """
                            ${scannerHome}/bin/sonar-scanner \
                            -Dsonar.projectKey=dec2hex \
                            -Dsonar.sources=. \
                            -Dsonar.java.binaries=. \
                            -Dsonar.host.url=http://sonarqube:9000
                        """
                    }
                }
            }
        }
    }
    
    post {
        always {
            cleanWs()
        }
    }
}

