#!/bin/sh
sudo yum -y install https://dl.fedoraproject.org/pub/epel/epel-release-latest-7.noarch.rpm
sudo yum install -y epel-release wget

sudo wget -O /etc/yum.repos.d/scylla.repo http://repositories.scylladb.com/scylla/repo/false/centos/scylladb-2018.1.repo

sudo yum install -y scylla-enterprise-2018.1.6

#scylla_setup
