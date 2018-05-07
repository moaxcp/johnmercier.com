---
title: Testing graph-dsl
layout: post
comments: true
---
graph-dsl is a project I started so I could write graphs and algorithms easily. Over the past
year testing has become important to the way I write code. With graph-dsl I wanted to learn more 
about Test Driven Development and using different tools like gradle, spock, jacoco, and sonarqube.

Gradle is the build tool I am most familiar with. It combines the best features from groovy, ant,
ivy, and maven. It supports massive multiproject builds and it is easy to override any convention.
It also has a large community for support and it is constantly updated.

Spock is a testing framework built on top of JUnit. It provides an easy way to describe code and
what it is expected to do. Spock is not really about testing units of code and integrating them. 
It is all about writing specifications and testing expected features of the code. The perspective 
is different but the end result is similar.

Adding spock to a gradle project is easy. Just add the dependency.

```groovy
dependencies {
    testCompile 'org.spockframework:spock-core:1.0-groovy-2.4'
}
```


In spock it is not easy to separate unit tests from integration tests and run them separately.
There isn't really a convention for naming tests as there is in JUnit and it is common to end
test classes with *Spec. When testing with groovy (in JUnit and Spock) it is helpful to use strings
as method names.

    def 'can get correct second unvisited vertex'() {
        setup:
        def graph = new Graph()
        graph.with {
            vertex 'step1'
            vertex 'step2'
        }
        def colors = ['step1': Graph.DepthFirstTraversalColor.GREY]

        when:
        def name = graph.getUnvisitedVertexName(colors)

        then:
        name == 'step2'
    }

When a test method fails the string should try to point out what feature failed. Spock is just one
tool I am using for testing. I also rely on jacoco to ensure I have good test coverage.

Jacoco is a great tool for viewing code coverage for tests or anything else running in the jvm. Gradle
 makes using jacoco easy. To use jacoco in gradle apply the plugin.
                          
```groovy
apply plugin: 'jacoco'
```

If you plan on following TDD it is easy to see the coverage as you build tests and add code. It
is not so easy to see what code each individual test is covering. There is some support to view
this in sonar when using jacoco with maven but I haven't found support in gradle.

There is a trick to using jacoco with groovy that took me a while to figure out. When groovy is
compiled there are some optimizations added to the resulting bytecode. These optimizations
show up in jacoco as extra instructions that may not be covered in your tests. It is better to
turn off these optimizations when running tests with jacoco. To do this I added the following
to my build.gradle

```groovy
gradle.taskGraph.whenReady { graph ->
    if (graph.hasTask(':jacocoTestReport') || graph.hasTask(':sonarqube')) {
        compileGroovy.groovyOptions.optimizationOptions.all = false
    }
}
```

This will turn off optimizations any time jacocoTestReport or sonarqube will be run.

Sonarqube is a tool that performs static code analysis. It provides reports on bugs and vulnerabilities
like and has a nice user interface. It also provides a central place to view test reports and
code coverage from jacoco (I could only find info about this for maven). Metrics are rated in 
sonarqube which allows the developer to see the health of a project.

Adding sonarqube is easy

```groovy
apply plugin: 'org.sonarqube'
```

Using sonarqube.com is free. Once you have an account set up and access key and add some entries to gradle.properties.

```
systemProp.sonar.host.url=https://sonarqube.com/
systemProp.sonar.login=sonar login
```

sonar seems to calculate line coverage differently than jacoco. It is usually a few percentage points
higher.