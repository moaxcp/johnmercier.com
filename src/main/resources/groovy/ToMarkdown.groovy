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