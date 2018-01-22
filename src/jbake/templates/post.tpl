model.put("projects",projects)
layout 'layout/main.tpl', true,
        bodyContents: contents {
            layout 'post-brick.tpl', config:config, post:content, summary:false
        }
