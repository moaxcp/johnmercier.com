---
layout: post
title: moaxcp-gentoo-overlay
date: 2015-05-23 01:56:03.000000000 -04:00
categories: []
tags: []
status: publish
type: post
published: true
comments: true
---
I created an [gentoo overlay](https://github.com/moaxcp/moaxcp-gentoo-overlay) with the latest version of x11-wm/notion and my own project dev-java/recMD5. Go to the <a href="https://github.com/moaxcp/moaxcp-gentoo-overlay">overlay</a> and follow the README.md for instructions on using the [overlay](https://github.com/moaxcp/moaxcp-gentoo-overlay). Here are the notes from setting things up.

I'm going through this [tutorial](https://wiki.gentoo.org/wiki/Overlay/Local_overlay) for creating an overlay. The base directory is

    /usr/local/portage/moaxcp

from the first section of the tutorial you create the repo

    mkdir -p /usr/local/portage/moaxcp/{metadata,profiles}
    echo 'moaxcp' > /usr/local/portage/moaxcp/profiles/repo_name
    echo 'masters = gentoo' > /usr/local/portage/moaxcp/metadata/layout.conf
    chown -R portage:portage /usr/local/portage/moaxcp
    /usr/local/portage is already owned by portage
    mkdir /etc/portage/repos.conf
    nano /etc/portage/repos.conf/local.conf

add the following text, save and exit

    [moaxcp]
    location = /usr/local/portage/moaxcp
    masters = gentoo
    auto-sync = no

Now that the repo is create put a package in it

    mkdir -p /usr/local/portage/moaxcp/x11-wm/notion
    nano /usr/local/portage/moaxcp/x11-wm/notion/notion_p2014052800.ebuild

* Add text from [https://523566.bugs.gentoo.org/attachment.cgi?id=389597](https://523566.bugs.gentoo.org/attachment.cgi?id=389597). The file is called notion-3-2014052800-src.tar.bz2 on sourceforge. Which looks similar to what the ebuild looks for.
* save and exit

    chown -R portage:portage /usr/local/portage/moaxcp
    pushd /usr/local/portage/moaxcp/x11-wm/notion
    repoman manifest

now I get

    Invalid ebuild name: /usr/local/portage/moaxcp/x11-wm/notion/notion_p2014052800.ebuild
    >>> Creating Manifest for /usr/local/portage/moaxcp/x11-wm/notion
    !!! Invalid package name: 'x11-wm/notion_p2014052800'
    Unable to generate manifest.

This was caused by the file name not containing the version number. I changed the file name to notion-3_p2014052800.ebuild and everything worked. Now add a keyword for the protected package so it will emerge.

    nano /etc/portage/package.keywords/notion
    =x11-wm/notion-3_p2014052800 ~amd64

Or use -a when emerging the package and accept the changes (don't forget to dispatch-conf). On a side note once the overlay is setup I added everything in the overlay to github and setup the overlay to sync with github. Now when I do emerge --sync the overlay will sync with github. Any changes I make to the overlay can by committed using git.

To sync with git I changed the repos.conf/local.conf file

    nano /etc/portage/repos.conf/local.conf

Add text

    [moaxcp]
    location = /usr/local/portage/moaxcp
    sync-uri = https://github.com/moaxcp/moaxcp-gentoo-overlay.git
    masters = gentoo
    auto-sync = yes
    sync-type = git
