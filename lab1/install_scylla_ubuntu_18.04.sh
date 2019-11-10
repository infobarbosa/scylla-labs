#!/bin/sh
sudo apt-key adv --keyserver keyserver.ubuntu.com --recv-keys 6B2BFD3660EF3F5B
sudo apt-key adv --keyserver keyserver.ubuntu.com --recv-keys 17723034C56D4B19

sudo wget -O /etc/apt/sources.list.d/scylla.list http://repositories.scylladb.com/scylla/repo/false/ubuntu/scylladb-2019.1-bionic.list

sudo apt-get update
sudo apt-get install scylla-enterprise

sudo add-apt-repository ppa:openjdk-r/ppa
sudo apt-get update
sudo apt-get install -y openjdk-8-jre-headless
sudo update-java-alternatives -s java-1.8.0-openjdk-amd64

#scylla_setup
