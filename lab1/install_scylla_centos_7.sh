#!/bin/sh
sudo yum -y install https://dl.fedoraproject.org/pub/epel/epel-release-latest-7.noarch.rpm
sudo yum install -y epel-release wget net-tools
sudo yum install -y python36-PyYAML.x86_64

sudo wget -O /etc/yum.repos.d/scylla.repo http://repositories.scylladb.com/scylla/repo/false/centos/scylladb-2018.1.repo

sudo yum install -y scylla-enterprise-2018.1.6

sudo scylla_setup --disks /dev/md0 --nic eth1 --ntp-domain centos --setup-nic --no-ec2-check --no-cpuscaling-setup --no-fstrim-setup
