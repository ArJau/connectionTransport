pipeline {
    agent any

    environment {
        DOCKERHUB_CREDENTIALS=credentials('docker-hub-cred')
    }
    
    stages {
        stage('1-Checkout from git') {
            steps {
                echo '1 - Checkout project'
                git branch: 'master', url:'https://github.com/ArJau/connectionTransport.git'
            }
        }
        stage('2-Compile') {
            steps {
                echo '2 - Compile project'
                sh 'mvn clean compile'
            }
        }
        stage('3-Test') {
            steps {
                echo '3 - Test project'
                //sh 'mvn test'
            }
            
            post{
                success {//publication des tests
                    //junit 'target/surefire-reports/*.xml'
                    echo 'junit target/surefire-reports/*.xml'
                }
            }
        }
        stage('4-Package') {
            steps {
                echo '4 - Package project'
                sh 'mvn package -DskipTests'
            }
            
            post{
                always {//archive de tous les jar
                    archiveArtifacts artifacts: 'target/*.jar', fingerprint:true
                }
            }
        }
        
        stage('5-Nettoyage des containers') {//suppression des anciens containers de la machine docker de test()192.168.33.11) et de la machine jenkins
            steps {
                echo 'Clean docker image and container'
                sh 'ssh -v -o StrictHostKeyChecking=no vagrant@192.168.33.11 sudo docker stop backTransport || true'
                sh 'ssh -v -o StrictHostKeyChecking=no vagrant@192.168.33.11 sudo docker rm backTransport || true'
                sh 'ssh -v -o StrictHostKeyChecking=no vagrant@192.168.33.11 sudo docker rmi jaujau31/backTransport || true'
                sh 'docker rmi backTransport || true'
            }
            
        } 
        stage('6-Docker build') {
            steps {
                echo 'Docker Build'
                sh 'docker build -t backTransport . ' //lance le container sur le docker de test
            }
            
        }
        
        stage('7-Tag image') {
            steps {
                echo '9 - Tag image'
                sh 'docker tag backTransport jaujau31/backTransport'     
            }
            
        }
        stage('8-Login dockerhub') {
            steps {
                echo '10 - Login dockerhub'
                sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'     
            }
            
        }
        stage('9-Push image dockerhub') {
            steps {
                echo '11 - Push image dockerhub'
                sh 'docker push jaujau31/backTransport'   
            }
        }
        stage('10-Run Container to local') {
            steps {
                echo 'Docker run'
                sh 'ssh -v -o StrictHostKeyChecking=no vagrant@192.168.33.11 sudo docker run -d --name backTransport -p8080:8080 jaujau31/backTransport'   
            }
            
        }
        
        stage ('11-Deploy To Prod AWS'){
              input{
                message "Do you want to proceed for production deployment?"
              }
            steps {
                sh 'echo "Deploy into Prod"'
                sh 'ssh -v -o StrictHostKeyChecking=no ubuntu@35.180.117.10 sudo docker stop backTransport || true'
                sh 'ssh -v -o StrictHostKeyChecking=no ubuntu@35.180.117.10 sudo docker rm backTransport || true'
                sh 'ssh -v -o StrictHostKeyChecking=no ubuntu@35.180.117.10 sudo docker rmi jaujau31/backTransport || true'
                sh 'ssh -v -o StrictHostKeyChecking=no ubuntu@35.180.117.10 sudo docker run -d --name backTransport -p8080:8080 jaujau31/backTransport'   
            

            }
        }

    }
    post {
        always {
            sh 'docker logout'
        }
    }
}
