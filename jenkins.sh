#!/bin/bash

# update the package index
sudo apt update

# install the JDK
echo "Installing default-java"
sudo apt install default-jdk -y

# ajoutez la clé du référentiel au système, wget telecharge
wget -q -O - https://pkg.jenkins.io/debian-stable/jenkins.io.key | sudo apt-key add -

# ajoutons l'adresse du référentiel Debian sur la sources.list du serveur
sudo sh -c 'echo deb http://pkg.jenkins.io/debian-stable binary/ > /etc/apt/sources.list.d/jenkins.list'

# nous exécuterons la update afin que apt utilise le nouveau référentiel.
sudo apt update


# installer Jenkins et ses dépendances
echo "Installing jenkins"
sudo apt install jenkins -y
sudo systemctl start jenkins

# attendre 1 minute
sleep 1m

# install git
echo "Installing git"
sudo apt-get -y install git

# install maven
echo "Installing Maven"
sudo apt-get install maven -y

# Get Jenkins password
JENKINSPWD=$(sudo cat /var/lib/jenkins/secrets/initialAdminPassword)
echo $JENKINSPWD

#Installation (uniquement) du client docker pour que jenkions puisse a distance build et run l'image docker
sudo apt-get update
sudo apt-get install ca-certificates curl gnupg lsb-release
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg
echo   "deb [arch=$(dpkg --print-architecture) signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
sudo apt-get update
sudo apt-get install docker-ce docker-ce-cli containerd.io