#!/bin/bash

# 시스템 업데이트
sudo apt-get update -y
sudo apt-get -y install apt-transport-https ca-certificates curl software-properties-common

# CodeDeploy Agent 설치 확인 및 설치
if ! systemctl status codedeploy-agent &> /dev/null; then
    echo "CodeDeploy Agent가 설치되어 있지 않습니다. 설치를 진행합니다..."
    
    # 패키지 설치
    sudo apt-get install -y ruby wget
    
    # AWS 리전 설정 (서울 리전으로 설정)
    REGION="ap-northeast-2"
    
    # CodeDeploy Agent 설치 패키지 다운로드
    wget https://aws-codedeploy-${REGION}.s3.amazonaws.com/latest/install
    
    # 실행 권한 부여 및 설치
    chmod +x ./install
    sudo ./install auto > /tmp/codedeploy-agent-install.log
    
    # 설치 후 파일 정리
    rm -f ./install
    
    # 서비스 시작 및 자동 시작 설정
    sudo systemctl start codedeploy-agent
    sudo systemctl enable codedeploy-agent
    
    echo "CodeDeploy Agent 설치 완료"
else
    echo "CodeDeploy Agent가 이미 설치되어 있습니다."
fi

# Java 21 설치 확인 및 설치
if ! command -v java &> /dev/null || ! java -version 2>&1 | grep -q "version \"21"; then
    echo "Java 21이 설치되어 있지 않습니다. 설치를 진행합니다..."
    sudo add-apt-repository -y ppa:openjdk-r/ppa
    sudo apt-get update
    sudo apt-get -y install openjdk-21-jdk
fi

# 애플리케이션 디렉토리 생성
if [ ! -d /home/ubuntu/app ]; then
    echo "애플리케이션 디렉토리를 생성합니다..."
    sudo mkdir -p /home/ubuntu/app
    sudo chown ubuntu:ubuntu /home/ubuntu/app
fi

# 로그 파일 초기화
if [ ! -f /home/ubuntu/app/deploy.log ]; then
    echo "로그 파일을 초기화합니다..."
    sudo touch /home/ubuntu/app/deploy.log
    sudo chown ubuntu:ubuntu /home/ubuntu/app/deploy.log
fi

# 배포 시간 기록
echo "$(date +%c) > 새로운 배포 시작" >> /home/ubuntu/app/deploy.log

# 기존 배포 파일 정리
if [ -d /home/ubuntu/app/build ]; then
    echo "기존 빌드 파일을 정리합니다..."
    sudo rm -rf /home/ubuntu/app/build
fi

echo "배포 준비가 완료되었습니다."
