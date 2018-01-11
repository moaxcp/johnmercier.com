---
title: Switching From Jekyll To JBake
layout: post
comments: true
---

In a previous post JBake was added to nixos. Now it is time to convert
this blog to JBake.

# Using Gradle

Gradle has a JBake plugin. This is good for automating the creation of
the site.

```
plugins {
    id 'org.jbake.site' version '1.0.0'
}


repositories {
    jcenter()
}

tasks.assemble.dependsOn 'bake'
```

Next JBake sources need to be created in `src/jbake`.

```
mkdir -p src/jbake
cd src/jbake
jbake -i
```

Once the sources are setup you can use gradle to bake the site.

# Using JBake

JBake is able to watch the content directory for changes and bake them.
The default output directory needs to be changed to match the gradle
build. In `jbake.properties`:

```
destination.folder=../../build/jbake
```

# Converting Posts

Converting jekyll's markdown files to JBake only should require
updating the header to the JBake format. Jekyll's header is in yaml
while JBake's is a key value list where a value may be json.

To start the jekyll posts are added under src/jekyll.

## Converting Jekyll Markdown to JBake Markdown

A groovy script can be used to convert the files. First a dependency
is needed to read the header.

```
@Grab('org.yaml:snakeyaml:1.19')
import org.yaml.snakeyaml.Yaml
```

The `Yaml` class is used to read `headerText`

```
Yaml parser = new Yaml()
Map header = parser.load(headerText)
```

The fileNameDate is also extracted.

```
Date fileNameDate = Date.parse('yyyy-MM-dd', file.name.substring(0, 10))
```

With this information a JBake header is generated.

```
newFile.withWriter {
    it.println 'type=post'
    it.println "title=$header.title"
    it.println "date=${fileNameDate.format('yyyy-MM-dd')}"
    it.println 'status=published'
    it.println '~~~~~~'
    it.write content
}
```

Here is the full script.

```
@Grab('org.yaml:snakeyaml:1.19')
import org.yaml.snakeyaml.Yaml

def source = new File('src/jekyll/_posts')
def destination = new File('src/jbake/content/blog')
source.eachFile { file ->
    println file
    assert file.text.startsWith('---\n'), file.name
    def removeStart = file.text.substring(4)
    assert removeStart.contains('---\n'), file.name
    def headerEnd = removeStart.indexOf('---\n')
    def headerText = removeStart.substring(0, headerEnd)

    assert headerText

    def content = removeStart.substring(headerEnd + 4)

    assert content

    Date fileNameDate = Date.parse('yyyy-MM-dd', file.name.substring(0, 10))

    assert fileNameDate

    Yaml parser = new Yaml()
    Map header = parser.load(headerText)

    assert header.keySet()*.toString().every {
        it in [
                'layout',
                'title',
                'date',
                'categories',
                'tags',
                'status',
                'type',
                'published',
                'meta',
                'author',
                'comments'
        ]
    }

    destination.mkdirs()
    def newFile = new File(destination.toString() + '/' + file.name)

    newFile.withWriter {
        it.println 'type=post'
        it.println "title=$header.title"
        it.println "date=${fileNameDate.format('yyyy-MM-dd')}"
        it.println 'status=published'
        it.println '~~~~~~'
        it.write content
    }
}
```

This was enough to see what the content looks like in JBake. The
The problem with markdown in JBake is all newlines are displayed. I
couldn't figure out how to fix this even with the pegdown extensions
enabled. Instead of worrying about this problem I decided to convert
the markdown files to asciidoctor.

## Converting Jekyll Markdown to AsciiDoc

This is much more interesting since it would be nice to use AsciiDoc.
In my search for a solution I found [bodiam/markdown-to-asciidoc](https://github.com/bodiam/markdown-to-asciidoc).

The header can be converted to an asciidoc format with JBake attibutes.

```
it.println "= $header.title"
it.println 'John Mercier'
it.println fileNameDate.format('yyyy-MM-dd')
it.println ':jbake-type: post'
it.println ':jbake-status: published'
```

The markdown content can be converted to asciidoc in one line.

```
it.write Converter.convertMarkdownToAsciiDoc(content)
```

## Adding images

Images from jekyll got into the assets directory in JBake. Many of the
images in my posts use html tags. This works in Markdown but not in
AsciiDoc. The converter does not convert html tags to AsciiDoc and
AsciiDoc does not recognize the tags. As a result the tags are in
plain text when viewing the posts. These will need to be fixed
manually after the conversion.

## Draft posts

In jekyll, I created a few draft posts that were not published. To convert these files I put them in a
separate directory.

# Automating deployment

When a post is added or a template updated the deployment should be updated automatically.