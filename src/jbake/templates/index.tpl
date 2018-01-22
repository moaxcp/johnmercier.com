layout 'layout/main.tpl', true,
        projects: projects,
        bodyContents: contents {

            published_posts[0..9].each { post ->
                if(!post.summary) {
                    def h = post.body.indexOf("<h")
                    def p = post.body.indexOf("</div>")
                    if(h > 0) {
                        post.summary = post.body.substring(0, h)
                    } else if(post.body.contains("</div>")) {
                        post.summary = post.body.substring(0, p + 7)
                    }
                }
                layout 'post-brick.tpl', config:config, post:post, summary:true
            }
            div(class:"row"){
                div(class:"small-12 columns"){
                    hr()
                    yield "Older post are available in the "
                    a(href:"${config.site_contextPath}${config.archive_file}","archive")
                }
            }

        }
