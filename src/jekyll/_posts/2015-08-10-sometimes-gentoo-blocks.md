---
title: sometime gentoo blocks
layout: post
comments: true
---
I was trying to update my system which resulted in a blocked package.

    emerge -avuUND --withb-deps=y @world

    * Error: The above package list contains packages which cannot be
    * installed at the same time on the same system.

      (x11-libs/libfm-extra-1.2.3:0/4.3.0::gentoo, ebuild scheduled for merge) pulled in by
        ~x11-libs/libfm-extra-1.2.3 required by (x11-libs/libfm-1.2.3-r1:0/4.3.0::gentoo, ebuild scheduled for merge)
        x11-libs/libfm-extra required by (lxde-base/menu-cache-1.0.0-r1:0/2::gentoo, ebuild scheduled for merge)

After reading the change log for the ebuild it turns out the package was changed so users would unmerge the package and then merge it again.

    emerge --unmerge x11-libs/libfm

Now my system is happy and being updated.
