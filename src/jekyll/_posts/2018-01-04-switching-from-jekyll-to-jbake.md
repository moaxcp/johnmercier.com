---
title: Switching From Jekyll To JBake
layout: post
comments: true
---

In a previous post [JBake](http://jbake.org/) was added to nixos. Now it is time to convert this blog to
[JBake](http://jbake.org/). As with any user blog hosted by [github](https://github.com) the content needs to be
posted to a git repository for the user. My repository is [moaxcp.github.com](https://github.com/moaxcp/moaxcp.github.io).
I'm using a [second repository](https://github.com/moaxcp/johnmercier.com) for the sources of the site.

There are a few tools I want to use. [Gradle](https://gradle.org/) to build the
[sources](https://github.com/moaxcp/johnmercier.com) and publish the blog to github.
[Travis CI](https://travis-ci.org/) to run the [Gradle](https://gradle.org/) tasks anytime the
[sources](https://github.com/moaxcp/johnmercier.com) change.

1. Convert posts from jekyll Markdown format to JBake asciidoc format
2. Setup Gradle to build and publish the blog
3. Setup travis-ci to update the blog when sources change

To get started I had to setup gradle and JBake in a workflow for converting posts and viewing the site.

# Setup Gradle

Gradle has a JBake plugin that can be used to bake the site.

This is good for automating the creation of the site using gradle. Here is an example build.gradle file using the
plugin:

```
plugins {
    id 'org.jbake.site' version '1.0.0'
}


repositories {
    jcenter()
}

tasks.assemble.dependsOn 'bake'
```

JBake sources need to be created in `src/jbake`. This is done with `jbake -i`.

```
mkdir -p src/jbake
cd src/jbake
jbake -i
```

Once the sources are setup gradle can bake the site.

```
./gradlew build
```

# Using JBake

A few changes need to be made to `jbake.properties`. First `site.host` needs to point to the host url of the site.

```
site.host=http://johnmercier.com
```

Next the destination folder needs to match where gradle will build the jbake site.

```
destination.folder=../../build/jbake
```

With these settings `jbake` commands will match the output directory for gradle.

Here is the complete `jbake.properties` file.

```
site.host=http://johnmercier.com
render.tags=true
render.sitemap=true
destination.folder=../../build/jbake
```

## Viewing content

There are a few options to viewing content. First, you can build with gradle and then navigate to index.html and open
it in a browser. Another option is `jbake -s`. This command will serve the content locally and update the content when
the source files change. Gradle does not support this option so `jbake -s` needs to be executed manually. This is the
best option but jbake doesn't seem to update content when the templates change.

## Workflow

1. start jbake server

`jbake -s`

2. Open browser to localhost:8082
3. Convert posts and refresh browser to view changes

Note: when changing templates restart the server

# Converting Posts

Now that there is an established workflow to view the blog the posts can be converted. The blog posts are written in
Markdown with a jekyll header. A simple groovy script can be used to read the files and convert them.

## Jekyll files

Jekyll posts are added under src/jekyll in the gradle project. The file name starts with the date followed by the
title. For example:

```
2015-05-30-notion-ebuild.md
```

The following code gets this information.

```
Date fileNameDate = Date.parse('yyyy-MM-dd', file.name.substring(0, 10))
String fileNameTitle = file.name.substring(11, file.name.lastIndexOf('.'))
```

## JBake files

In the jekyll blog all posts are in one dir. The file names start with a timestamp. When the site is generated all post
URIs convert the timestamps into directories. A file named `2015-07-12-post-title.md` has a URI of
2015/07/12/post-title.html. In JBake it would have a URI of 2015-07-12-post-title.html. This can cause problems for
posts that have comments in [disqus](https://disqus.com/) because the uri is also an id. It would be nice to have the
disqus id match for old posts and organize the posts in any way possible.

Fortunately for me I have no comments on this blog. So I can organize the files in anyway without a problem. JBake does
allow for custom metadata. A disqus id can be added to each post which matches the id used in Jekyll. The post.ftl can
check for this id. If it is present use it. If the id is not present default to the uri. I'm not going to implement
this solution though since I have no comments.

## Reading the posts

Reading jekyll's markdown files involves reading the header and reading the content.

The header is always at the top of the file starting and ending with `---`. This gets the header text.

```
assert file.text.startsWith('---\n'), file.name
def removeStart = file.text.substring(4)
assert removeStart.contains('---\n'), file.name
def headerEnd = removeStart.indexOf('---\n')
def headerText = removeStart.substring(0, headerEnd)
```

The rest of the text is the Markdown content.

```
def content = removeStart.substring(headerEnd + 4)
```

Jekyll's header is in yaml format Yaml can be read in groovy using [snakeyaml](https://bitbucket.org/asomov/snakeyaml).

```
@Grab('org.yaml:snakeyaml:1.19')
import org.yaml.snakeyaml.Yaml

...

Yaml parser = new Yaml()
Map header = parser.load(headerText)
```

### Alternative: using atlasian commonmark

An alternative I tried using to read the files is atlassian's
[commonmark](https://github.com/atlassian/commonmark-java) libraries. commonmark is able to fully parse the header and
content as a Markdown syntax tree. The tree can be manipulated which as it turns out is important for converting posts
asciidoc. Unfortunately, commonmark does not have the ability to render the tree back into Markdown. To setup
commonmark I added the dependencies for commonmark and the front matter extension.

```
@Grab('com.atlassian.commonmark:commonmark:0.10.0')
@Grab('com.atlassian.commonmark:commonmark-ext-yaml-front-matter:0.10.0')

import org.commonmark.parser.Parser
import org.commonmark.renderer.text.TextContentRenderer
import org.commonmark.node.*
import org.commonmark.ext.front.matter.*
```

Next, the Parser is created with the front matter extension.

```
commonmark = Parser.builder()
        .extensions([YamlFrontMatterExtension.create()])
        .build()
```

Parsing a document is easy with the parser and two visitors.

```
Document document = commonmark.parse(file.text)
Map data = getHeaderAndUnlink(document)
convertHeadings(document)
```

`getHeaderAndUnlink` uses a `YamlFrontMatterVisitor` to parse the header and remove it from the document. This is so
the document can be rendered without the header.

```
Map getHeaderAndUnlink(Document document) {
    def visitor = new AbstractVisitor() {
        YamlFrontMatterVisitor yamlVisitor = new YamlFrontMatterVisitor()
        void visit(CustomNode node) {
            yamlVisitor.visit(node)
            if(node.class == YamlFrontMatterNode) {
                node.unlink()
            }
            visitChildren(node)
        }
        Map<String, List<String>> getData() {
            return yamlVisitor.data
        }
    }
    document.accept(visitor)
    return visitor.data
}
```

`convertHeadings` increases all of the headings levels by 1. This is because in an asciidoc post the title should be
the only level 1 heading in the file.

```
void convertHeadings(Document document) {
    document.accept(new AbstractVisitor() {
        void visit(Heading heading) {
            heading.level += 1
            visitChildren(heading)
        }
    })
}
```

The problem now is how to render the Markdown content again so it may be converted to asciidoc. This currently doesn't
seem possible. I tried using

```
TextContentRenderer.builder().build().render(document)
```

This only renders the text without Markdown formatting. Until commonmark gets a MarkdownRenderer it will not be of
help. This could be a good open source contribution in the future.

## Converting Jekyll Markdown to JBake Markdown Instead of AsciiDoc

At this point there is enough information to make a script which can convert the files to JBake's Markdown format. Here
is the full script:

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

This was enough to see what the content looks like in JBake. The problem with markdown in JBake is all newlines are
displayed. I couldn't figure out how to fix this even with the pegdown extensions enabled.

## Converting Jekyll Markdown to AsciiDoc

### Converting the Header

The header can be converted to an asciidoc format with JBake attibutes.

```
it.println "= $header.title"
it.println 'John Mercier'
it.println fileNameDate.format('yyyy-MM-dd')
it.println ':jbake-type: post'
it.println ':jbake-status: published'
```

As mentioned earlier the title should be the only level 1 heading. The rest need to be converted. Without a parser I
used regex to fix the headings.

```
content = content.replaceAll('(?m)^####\\s', '##### ')
content = content.replaceAll('(?m)^###\\s', '#### ')
content = content.replaceAll('(?m)^##\\s', '### ')
content = content.replaceAll('(?m)^#\\s', '## ')
```

Using regex where a parser is required is a common mistake programmers make. In this case the mistake is in codeblocks.
Any codeblock containing `#` will be read as a Markdown heading.

```
\```
# comments
\```
```

would become

```
\```
## comments
\```
```

I'm willing to accept this risk and fix any issues manually.

### Converting the content

It would be really difficult to write an entire solution to this problem. In my search for a solution I found
[bodiam/markdown-to-asciidoc](https://github.com/bodiam/markdown-to-asciidoc). markdown-to-asciidoc is able to convert
a Markdown string into asciidoc. This is exactly what I need.

The markdown content can be converted to asciidoc in one line.

```
it.write Converter.convertMarkdownToAsciiDoc(content)
```




## Adding images

Images from jekyll are simply placed into the assets directory in JBake. Many of the images in my posts use html tags.
This works in Markdown but not in AsciiDoc. The converter does not convert html tags to AsciiDoc and AsciiDoc does not
recognize the tags. As a result the tags are in plain text when viewing the posts. These posts were fixed manually to
use Markdown instead of html.

# Gradle git-publish

Publishing posts can be accomplished using a gradle plugin called git-publish. After adding the plugin a task called
`gitPublishPush` is added which can publish the blog.

```
plugins {
    id 'org.jbake.site' version '1.0.0'
    id 'org.ajoberstar.git-publish' version '0.4.0-rc.2'
}


repositories {
    jcenter()
}

tasks.assemble.dependsOn 'bake'

gitPublish {
    repoUri = 'https://github.com/moaxcp/moaxcp.github.io.git'

    branch = 'master'

    contents {
        from bake.output
        from 'src/github/CNAME'
    }

    commitMessage = 'Publishing from gradle.'
}

tasks.gitPublishPush.dependsOn 'bake'
```

# Automatic publishing with travis-ci

Travis can use the github api to publish. Travis first needs a github token which can be passed to the `gitPublishPush`
task in gradle.