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
		stage('2-Changer URL de prod') {
            steps {
                sh 'sed -i -E -r "s|.*spring.datasource.url.*|spring.datasource.url=$MYSQL_PROD_URL|g" src/main/resources/application.properties'
                sh 'sed -i -E -r "s|.*spring.datasource.username.*|spring.datasource.username=$MYSQL_PROD_USR|g" src/main/resources/application.properties'
                sh 'sed -i -E -r "s|.*spring.datasource.password.*|spring.datasource.password=$MYSQL_PROD_PWD|g" src/main/resources/application.properties'
            }
        }
        stage('3-Compile') {
            steps {
                echo '2 - Compile project'
                sh 'mvn clean compile'
            }
        }
        stage('4-Test') {
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
        stage('5-Package') {
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
        
        stage('6-Nettoyage des containers') {//suppression des anciens containers de la machine docker de test()192.168.33.11) et de la machine jenkins
            steps {
                echo 'Clean docker image and container'
                sh 'ssh -v -o StrictHostKeyChecking=no vagrant@192.168.33.11 sudo docker stop back-transport || true'
                sh 'ssh -v -o StrictHostKeyChecking=no vagrant@192.168.33.11 sudo docker rm back-transport || true'
                sh 'ssh -v -o StrictHostKeyChecking=no vagrant@192.168.33.11 sudo docker rmi jaujau31/back-transport || true'
                sh 'docker rmi back-transport || true'
            }
            
        } 
        stage('7-Docker build') {
            steps {
                echo 'Docker Build'
                sh 'docker build -t back-transport . ' //lance le container sur le docker de test
            }
            
        }
        
        stage('8-Tag image') {
            steps {
                echo '9 - Tag image'
                sh 'docker tag back-transport jaujau31/back-transport'     
            }
            
        }
        stage('9-Login dockerhub') {
            steps {
                echo '10 - Login dockerhub'
                sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'     
            }
            
        }
        stage('10-Push image dockerhub') {
            steps {
                echo '11 - Push image dockerhub'
                sh 'docker push jaujau31/back-transport'   
            }
        }
        stage('11-Run Container to local') {
            steps {
                echo 'Docker run'
                sh 'ssh -v -o StrictHostKeyChecking=no vagrant@192.168.33.11 sudo docker run -d --name back-transport -p8080:8080 jaujau31/back-transport'   
            }
            
        }
        
        stage ('12-Deploy To Prod AWS'){
              //input{
              //  message "Do you want to proceed for production deployment?"
              //}
            steps {
                sh 'echo "Deploy into Prod"'
                sh 'ssh -v -o StrictHostKeyChecking=no ubuntu@15.237.111.102 sudo docker stop back-transport || true'
                sh 'ssh -v -o StrictHostKeyChecking=no ubuntu@15.237.111.102 sudo docker rm back-transport || true'
                sh 'ssh -v -o StrictHostKeyChecking=no ubuntu@15.237.111.102 sudo docker rmi jaujau31/back-transport || true'
                sh 'ssh -v -o StrictHostKeyChecking=no ubuntu@15.237.111.102 sudo docker run -d --name back-transport -p8080:8080 jaujau31/back-transport'   
            }
        }

    }
    post {
        always {
            sh 'docker logout'
        }
    }
}
