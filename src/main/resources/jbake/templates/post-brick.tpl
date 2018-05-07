div(class:"row"){
    div(class:"small-12 middle-12 large-12 columns"){
        article(class:"wrap"){
            header{
                div(class:"row"){
                    div(class:"small-3 medium-1 large-1 columns"){
                        include template: 'date-brick.tpl'
                    }

                    div(class:"small-9 medium-11 large-11 columns"){

                        div{
                            h2{
                                a(href:"${config.site_contextPath}${post.uri}","${post.title}")
                            }
                            include template: 'tags-brick.tpl'
                            a(href:"${config.site_contextPath}${post.uri}#disqus_thread", 'comments')
                            hr()
                        }
                    }
                }
            }

            div(class:"row"){
                div(class:"small-9 small-offset-3 medium-11 medium-offset-1 large-11 large-offset-1 columns"){
                    yieldUnescaped summary ? post.summary : post.body
                }
            }
            if(!summary) {
                div(class:'row') {
                    div(id:'disqus_thread') {
                        script {
                            yieldUnescaped """
                                /**
                                *  RECOMMENDED CONFIGURATION VARIABLES: EDIT AND UNCOMMENT THE SECTION BELOW TO INSERT DYNAMIC VALUES FROM YOUR PLATFORM OR CMS.
                                *  LEARN WHY DEFINING THESE VARIABLES IS IMPORTANT: https://disqus.com/admin/universalcode/#configuration-variables*/
                                /*
                                var disqus_config = function () {
                                this.page.url = '${config.site_host}/${post.uri}';  // Replace PAGE_URL with your page's canonical URL variable
                                this.page.identifier = '${post.uri}'; // Replace PAGE_IDENTIFIER with your page's unique identifier variable
                                };
                                */
                                (function() { // DON'T EDIT BELOW THIS LINE
                                var d = document, s = d.createElement('script');
                                s.src = 'https://moaxcp.disqus.com/embed.js';
                                s.setAttribute('data-timestamp', +new Date());
                                (d.head || d.body).appendChild(s);
                                })();
                            """
                        }
                        noscript {
                            yieldUnescaped 'Please enable JavaScript to view the <a href="https://disqus.com/?ref_noscript">comments powered by Disqus.</a>'
                        }
                    }
                }
            }
        }
    }
}