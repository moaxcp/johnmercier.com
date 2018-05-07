---
title: Spring Boot Auto-completion breaks xdm
layout: post
comments: true
---
After setting up the spring boot cli auto-completion feature everything was working fine. Typing spring and then hitting tab in the console brought up the list of spring commands as promised. What I did not expect was after restarting my computer the xdm service complained about spring shell-completion.

     * Caching service dependencies ...                  [ ok ]
     * Stopping xdm ...
     * start-stop-daemon: no matching processes found    [ ok ]
     * Setting up xdm ...
	/opt/spring-1.2.5.RELEASE/shell-completion/bash/spring: line 16: syntax error near unexpected token `<'
	/opt/spring-1.2.5.RELEASE/shell-completion/bash/spring: line 16: `      done < <(spring hint ${cword} ${words[*] [ ok ]

In a previous post I setup profile.d to create a spring cli environment for all users on my system. Once I remove the sourcing of shell-completion everything is fine but I want this to be fixed.

After some research on the bash [here document](https://github.com/spring-projects/spring-boot/pull/3848) the [completion script](https://github.com/spring-projects/spring-boot/pull/3848) is using, I was not able to find the solution. The problem is not in the here-document but in the header of the script. After adding the correct sha-bang `#!/bin/bash` I was able to start xdm normally. I'm guessing that the double redirection with a here-document is not allowed in sh but is in bash and that xdm runs scripts in sh unless the sha-bang is present.

I added a [pull](https://github.com/spring-projects/spring-boot/pull/3848) request to spring-boot to fix the problem. 
