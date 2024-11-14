pipeline {
    agent any
    
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        
        stage('Compile') {
            steps {
                sh 'javac Dec2Hex.java'
            }
        }
        
        stage('Test') {
            steps {
                // Download JUnit and Hamcrest if not present
                sh '''
                    if [ ! -f junit-4.13.2.jar ]; then
                        curl -O https://repo1.maven.org/maven2/junit/junit/4.13.2/junit-4.13.2.jar
                    fi
                    if [ ! -f hamcrest-core-1.3.jar ]; then
                        curl -O https://repo1.maven.org/maven2/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar
                    fi
                '''
                
                // Compile test code with dependencies
                sh 'javac -cp .:junit-4.13.2.jar:hamcrest-core-1.3.jar Dec2HexTest.java'
                
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
                    def scannerHome = tool 'SonarQubeScanner'
                    withSonarQubeEnv('SonarQube') {
                        sh """
                            ${scannerHome}/bin/sonar-scanner \
                            -Dsonar.projectKey=dec2hex \
                            -Dsonar.sources=. \
                            -Dsonar.java.binaries=. \
                            -Dsonar.host.url=http://100.26.51.149:9000
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

