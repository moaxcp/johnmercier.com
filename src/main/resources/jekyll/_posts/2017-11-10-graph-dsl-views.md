---
title: graph-dsl views
layout: post
comments: true
---
I'm adding a view method to graph-dsl. This method will generate a string which can be used 
by graphviz to create an image which can be displayed. This is the fist time 
graph-dsl will be 
integrated 
with 
another product.

Spock tests can use a Mock to ensure the view calls the correct graphviz command.

To test the view manually graph-dsl will need to be installed to the local maven repo. Then 
grape will need to be configured to use the local maven repo. Once grape can grab the 
dependency it can be used in a script.

# spock test



# gradle install

# grape config

The grape dependency management system allows groovy scripts to bring in dependencies. It 
allows the script to be independent of any other build system. Users of groovy scripts do not 
need to worry about dependencies for the script. It just works.

To add maven-local as a repository in grape the following should be added to the 
downloadGrapes chain in grapeConfig.xml.

```xml

      <filesystem name="local-maven2" checkmodified="true" changingPattern=".*" 
changingMatcher="regexp" m2compatible="true" cache="nocache">
        <artifact pattern="${user.home}/.m2/repository/[organisation]/[module]/[revision]/[module]-[revision](-[classifier]).[ext]"/>
        <ivy pattern="${user.home}/.m2/repository/[organisation]/[module]/[revision]/[module]-[revision](-[classifier]).pom"/>
      </filesystem>
```

This tells grape to use the default location of .m2 to grab dependencies.

# grab graph-dsl in script and use it

Now that everything is set up a dsl script to use graph-dsl can be setup which calls the view 
method to display a graph using graphviz.

```groovy

```
