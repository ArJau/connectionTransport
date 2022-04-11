Vagrant.configure("2") do |config|
  config.vm.box = "bento/ubuntu-20.04"
  
  config.vm.define "jenkins", autostart: false do |jenkins|
    jenkins.vm.hostname = "jenkins"
    jenkins.vm.network "private_network", ip: "192.168.33.10"
    jenkins.vm.provision "shell", path:"jenkins.sh"
    jenkins.vm.provider "virtualbox" do |vb|
      vb.memory = "2048"
	  vb.cpus = 1
	  vb.name = "myjenkins"
    end	
  end
  
  config.vm.define "docker", autostart: false do |docker|
    docker.vm.hostname = "docker"
    docker.vm.network "private_network", ip: "192.168.33.11"
    docker.vm.provision "shell", path:"docker.sh"
    docker.vm.provider "virtualbox" do |vb|
      vb.memory = "2048"
	  vb.cpus = 1
	  vb.name = "mydocker"
    end	
  end
end