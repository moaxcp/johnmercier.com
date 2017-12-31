---
title: notion 9999 ebuild.
layout: post
comments: true
---
Today I am investigating installing the latest version of x11-wm/notion on my machine. I got an email from the developer that it should be safe. He said that there are no plans for a new release but many fixes have been made in the commit logs. I'm going to risk it and try out the latest development source code.

Gentoo has a 9999 ebuild for notion. 9999 ebuilds directly install what is currently being developed in a project's source code repository. It takes the current revision of the sources, compiles them, and installs the project on your system. For certain projects this can be dangerous. It depends a lot on the [workflow](https://www.atlassian.com/git/tutorials/comparing-workflows) of the project and the policy for commiting broken code.

Notion seems to use a [forking workflow](https://www.atlassian.com/git/tutorials/comparing-workflows/forking-workflow) with a main repository located on sourceforge. There are a few repositories on github. My guess is the accepted changes all get put into the sourceforge repo and releases are made from it. With the forking workflow The main repository should not be broken and it should be safe to install software from it without problems. So I am going to install the 9999 package.

Here is how I unmasked the 9999 ebuild and installed it.

        echo "=x11-wm/notion-9999" >> /etc/portage/package.unmask
        echo "=x11-wm/notion-9999 **" >> /etc/portage/package.accept_keywords
        emerge -av x11-wm/notion

This results in an error. Probably because the dependencies have changed since last year. To fix this I am copying the ebuild to my [git overlay](https://github.com/moaxcp/moaxcp-gentoo-overlay) and updating it.

It turns out that there is a problem with the current code. The lua detector is not able to detect the version of lua installed on the system. It uses the name of the lua executable rather than using lua -v to get the version. I will need to wait on installing the 9999 version of notion until this is resolved. The developer said it may not be until next weekend.
