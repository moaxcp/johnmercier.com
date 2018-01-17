@Grab('org.yaml:snakeyaml:1.19')
@Grab('nl.jworks.markdown_to_asciidoc:markdown_to_asciidoc:1.0')


import org.yaml.snakeyaml.Yaml
import nl.jworks.markdown_to_asciidoc.Converter

def source = new File('src/jekyll/_posts')
def destination = new File('src/jbake/content/blog')

source.eachFile { file ->
    Date fileNameDate = Date.parse('yyyy-MM-dd', file.name.substring(0, 10))
    assert fileNameDate

    println file
    assert file.text.startsWith('---\n'), file.name
    def removeStart = file.text.substring(4)
    assert removeStart.contains('---\n'), file.name
    def headerEnd = removeStart.indexOf('---\n')
    def headerText = removeStart.substring(0, headerEnd)
    assert headerText

    def content = removeStart.substring(headerEnd + 4)
    assert content

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