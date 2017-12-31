---
title: pipe-service
layout: post
comments: true
---
I have been working on an idea for a microservice called [pipe-service](https://github.com/moaxcp/pipe-service). It will take output from one service 
and send it as input to another service. The services I have in mind are http(s) and the variety of ftp services.

The project is called pipe-service as a reference to command line pipes. Pipes allow the output of one command to be
used as input to another command. One example of this is ps and grep. When a user needs to see if a specific process
is running he will use a pipe between the two commands.

    ps aux | grep --color jboss

Bash will take the output stream of `ps aux` and make it available as the input stream of the grep program for processing.

pipe-services will work in a similar fashion.

    get http://example.com/service... | post http://nextexample.com/service2...

Another example is to send the input to ftp. This will transfer a file from http to ftp.

    get http://example.com/file.zip | put ftp://nextexample.com/home/john/file.zip

Other services could be created to faciliate an entire pipeline of processing.

    get rest object | post transform-service
    get transform-service | post neo4j-service

I plan on using [spring-boot](http://projects.spring.io/spring-boot/) since it provides so much out of the box. It would also be nice to write an [angular](https://angularjs.org/) front end.
Using something like [JHipster](https://jhipster.github.io/) may help with that.
