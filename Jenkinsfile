pipeline {
    environment {
        registry = "god0nlyknows/tbk5zad3"
        DOCKERHUB_CREDENTIALS = credentials('docker-login-pwd')
    }
   agent none
    options {
        skipStagesAfterUnstable()
    }
    stages {
        stage('Test') {
            agent {
                    docker {
                        image 'maven:3.8.6-amazoncorretto-17'
                        args '-u root:root'
                        args '-v /var/run/docker.sock:/var/run/docker.sock'
                        args '-w /app'
                    }
                }
            steps {

                echo 'Testing'
                sh 'mvn test'
            }
        }
        stage('Build') {
        agent {
                docker {

                    image 'maven:3.8.6-amazoncorretto-17'
                    args '-u root:root'
                    args '-v /var/run/docker.sock:/var/run/docker.sock'
                    args '-w /app'
                }
            }
            steps {
                echo 'Building'
                sh 'mvn package'
            }
        }
        stage('Deploy to hub'){
        agent{
            docker {
                                    image 'mmiotkug/node-curl'
                                    args '-p 3000:3000'
                                    args '-w /app'
                                    args '-v /var/run/docker.sock:/var/run/docker.sock'
                                }
        }
            steps{
                echo 'Deploying to docker hub'
                script {
                    sh 'docker image build -t $registry:$BUILD_NUMBER .'
                    sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u god0nlyknows --password-stdin'
                    sh 'docker image push $registry:$BUILD_NUMBER'
                    sh "docker image rm $registry:$BUILD_NUMBER"
                }
            }
        }
    }
}