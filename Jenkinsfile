pipeline {
    agent any
 
    tools {
        // Install the Maven version configured as "M3" and add it to the path.
        maven "maven3"
    }

    environment {
        DOCKER_CREDENTIALS_ID = "docker-credentials"
        DOCKER_REGISTRY = "ioannismac"
        DOCKER_IMAGE_NAME = "projet_m2i_app"
        DOCKER_IMAGE_TAG = "latest"
    }
 
    stages {
        stage('Checkout Code') {
            steps {
                // Get some code from a GitHub repository
                git branch: 'main' , url: 'https://github.com/ioannis-mac/proje_m2i.git', credentialsId: 'ioannis-mac-projet-fil-rouge'
            }
        }
        stage('Maven clean test') {
            steps {
                sh "mvn clean"
                sh "docker-compose -f docker-compose-test.yml up -d --build"
                sh "mvn test"
                script{
                    sh 'docker stop $(docker ps -a -q) || true'
                    sh 'docker container prune -f'
                }
            }
        }
        stage('Build Maven') {
            steps {
                sh "mvn -DskipTests clean package"
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
        }
        
        stage('Docker Login') {
            steps {
                script {
                    // Login to Docker Hub
                    withCredentials([usernamePassword(credentialsId: "${DOCKER_CREDENTIALS_ID}", usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                        sh "echo $PASSWORD | docker login -u $USERNAME --password-stdin"
                    }
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    // Push the Docker image to Docker Hub
                    sh "docker push ${DOCKER_REGISTRY}/${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG}"
                }
            }
        }
        stage('Clean Up') {
            steps {
                script {
                    // Clean up local Docker images to free space
                    sh "docker rmi ${DOCKER_REGISTRY}/${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG} || true"
                }
            }
        }

        /*
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