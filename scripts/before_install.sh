#!/bin/bash

sudo apt-get update
sudo apt-get -y install apt-transport-https ca-certificates curl software-properties-common

if ! command -v java &> /dev/null || ! java -version 2>&1 | grep -q "version \"21"; then
    echo "installing Java 21"
    sudo add-apt-repository -y ppa:openjdk-r/ppa
    sudo apt-get update
    sudo apt-get -y install openjdk-21-jdk
fi

if [ ! -d /home/ubuntu/app ]; then
    sudo mkdir -p /home/ubuntu/app
    sudo chown ubuntu:ubuntu /home/ubuntu/app
fi

if [ ! -f /home/ubuntu/app/deploy.log ]; then
    sudo touch /home/ubuntu/app/deploy.log
    sudo chown ubuntu:ubuntu /home/ubuntu/app/deploy.log
fi

echo "$(date +%c) > 새로운 배포 시작" >> /home/ubuntu/app/deploy.log

if [ -d /home/ubuntu/app/build ]; then
    sudo rm -rf /home/ubuntu/app/build
fi

echo "배포 준비가 완료되었습니다."
