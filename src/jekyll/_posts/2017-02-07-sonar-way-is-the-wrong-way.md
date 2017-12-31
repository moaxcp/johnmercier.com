---
title: sonar way is the wrong way
layout: post
comments: true
---

Codenarc is a static code analysis tool for groovy. It is the same tool [sonarqube.com](http://sonarqube.com) uses to to publish its results.
The [graph-dsl](https://github.com/moaxcp/graph-dsl) project is setup to use codenarc for local development and [sonarqube.com](http://sonarqube.com) for
 continuous integration. There is just one problem with [sonarqube.com](http://sonarqube.com), it only uses 59 rules rather than the 353 rules available.
 I wanted to find a way to send all of those rules to sonarqube.com that were missing.
 
To start I found [sonar-groovy](https://github.com/SonarQubeCommunity/sonar-groovy) this is a plugin for sonar-runner which
scans groovy code. It can be configured to look at a codenarc report within groovy.

Note: this porject uses gradle so the following is gradle configurations to accomplish this.

First, codenarc needs to be setup to output xml formatted reports.

    codenarc {
        reportFormat = 'xml'
    }

Next, the sonarqube plugin needs to be configured to use that report.

    sonarqube {
        properties {
            property 'sonar.groovy.codenarc.reportPath', codenarcMain.getReports().getXml().getDestination()
        }
    }


This results in lots a messages that look like this

    No such rule in Sonar, so violation from CodeNarc will be ignored: 

It appears that [sonarqube.com](http://sonarqube.com) is setup with Quality Profiles. These profiles determine which rules are
actually turned on. The default for groovy projects only enables 59 of 353 rules. For [graph-dsl](https://github.com/moaxcp/graph-dsl) 
it is not possible to modify the Quality Profile and as a result [sonarqube.com](http://sonarqube.com) does not seem very useful for groovy projects. 