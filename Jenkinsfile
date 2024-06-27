pipeline {
    agent any
 
    tools {
        // Install the Maven version configured as "M3" and add it to the path.
        maven "maven3"
    }
 
    stages {
        stage('Checkout Code') {
            steps {
                // Get some code from a GitHub repository
                git branch: 'main' , url: 'https://github.com/ioannis-mac/proje_m2i.git', credentialsId: 'ioannis-mac-projet-fil-rouge'
            }
        }
        stage('Maven clean') {
            steps {
                sh "mvn clean"
                echo "Stage clean has finished"
            }
        }
        stage('Maven test') {
            steps {
                sh "docker-compose down -v && docker-compose -f docker-compose-test.yml up --build"
                sh "mvn test"
                echo "Stage test has finished"
                sh "docker-compose down -v"
            }
        }
        stage('Build Maven') {
            steps {
                sh "mvn -DskipTests clean package"
                echo "Stage build has finished"
               }
        }
        stage('Archive Artifacts') {
            steps {
                // Archive the artifacts
                archiveArtifacts artifacts: '**/target/*.jar', allowEmptyArchive: true
            }
        }
        stage('SonarQube Analysis') {
            steps {
                // Execute SonarQube analysis
                script {
                    withSonarQubeEnv('sonar_server') {
                        sh 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.7.0.1746:sonar'
                    }
                }
            }
        }/*
        stage('SonarQube Analysis') {
            steps {
                // Execute SonarQube analysis
                script {
                    def scannerHome = tool 'sonar_scanner_tool'
                    withSonarQubeEnv('sonarqubeserver') {
                        sh "${scannerHome}/bin/sonar-scanner -Dsonar.projectKey=my-app \
                        -Dsonar.java.binaries=target/**"
                    }
                }
            }
        }
        stage("Quality Gate") {
            steps {
              timeout(time: 3, unit: 'MINUTES') {
                waitForQualityGate abortPipeline: true
              }
            }
        }
        stage("Nexus"){
            steps{
                nexusArtifactUploader artifacts: [[artifactId: 'my-app', classifier: '', file: 'target/my-app-1.2.jar', type: 'jar']], credentialsId: 'nexus_pwd', groupId: 'com.mycompany.app', nexusUrl: '10.185.11.74:8081', nexusVersion: 'nexus3', protocol: 'http', repository: 'repo1',
                    version: "$BUILD_TIMESTAMP"
            }
        }*/
    }
}