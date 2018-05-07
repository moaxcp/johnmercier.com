---
title: Installing Spring Boot CLI in Gentoo
layout: post
comments: true
---

First, download the latest spring-boot-cli

Then untar to /opt

    untar -xf spring-boot-cli-1.2.5.RELEASE-bin.tar.gz

Next, create a file in profile.d to setup the environment `vi /etc/profile.d/spring-cli.sh`

    \#!/bin/bash
    export SPRING_HOME=/opt/spring-1.2.5.RELEASE
    PATH=${SPRING_HOME}/bin:${PATH}
    export PATH
    
Finally, restart the terminal or source the file `. vi /etc/profile.d/spring-cli.sh`
