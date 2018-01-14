@Grab('org.yaml:snakeyaml:1.19')
@Grab('com.atlassian.commonmark:commonmark:0.10.0')
@Grab('com.atlassian.commonmark:commonmark-ext-yaml-front-matter:0.10.0')
@Grab('nl.jworks.markdown_to_asciidoc:markdown_to_asciidoc:1.0')


import org.yaml.snakeyaml.Yaml
import nl.jworks.markdown_to_asciidoc.Converter
import org.commonmark.parser.Parser
import org.commonmark.renderer.text.TextContentRenderer
import org.commonmark.node.*
import org.commonmark.ext.front.matter.*


commonmark = Parser.builder()
        .extensions([YamlFrontMatterExtension.create()])
        .build()

def source = new File('src/jekyll/_posts')
def destination = new File('src/jbake/content/blog')
source.eachFile { file ->
    Date fileNameDate = Date.parse('yyyy-MM-dd', file.name.substring(0, 10))
    String fileNameTitle = file.name.substring(11, file.name.lastIndexOf('.'))

    Document document = commonmark.parse(file.text)
    Map data = getHeaderAndUnlink(document)
    convertHeadings(document)
    String markdown = renderDocument(document)
    println markdown





    println file
    assert file.text.startsWith('---\n'), file.name
    def removeStart = file.text.substring(4)
    assert removeStart.contains('---\n'), file.name
    def headerEnd = removeStart.indexOf('---\n')
    def headerText = removeStart.substring(0, headerEnd)

    assert headerText

    def content = removeStart.substring(headerEnd + 4)

    assert content



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
    def fileName = file.name.replaceAll('\\.md$', '.adoc')
    def newFile = new File(destination.toString() + '/' + fileNameDate.format('yyyy') + '/' + fileName.substring(5))

    newFile.getParentFile().mkdirs()

    newFile.withWriter {
        it.println "= $header.title"
        it.println 'John Mercier <moaxcp@gmail.com>'
        it.println fileNameDate.format('yyyy-MM-dd')
        it.println ':jbake-type: post'
        it.println ':jbake-status: published'
        content = content.replaceAll('(?m)^####\\s', '##### ')
        content = content.replaceAll('(?m)^###\\s', '#### ')
        content = content.replaceAll('(?m)^##\\s', '### ')
        content = content.replaceAll('(?m)^#\\s', '## ')
        it.write Converter.convertMarkdownToAsciiDoc(content)
    }
}

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
    assert visitor.data
    assert visitor.data.keySet()*.toString().every {
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
    return visitor.data
}

void convertHeadings(Document document) {
    document.accept(new AbstractVisitor() {
        void visit(Heading heading) {
            heading.level += 1
            visitChildren(heading)
        }
    })
}

String renderDocument(Document document) {
    TextContentRenderer.builder().build().render(document)
}