---
title: Gentoo Audit Log
layout: post
comments: true
---
Gentoo does not seem to have a lot of info on auditing unless it is under the hardened project. I am trying to get basic audit logging of users attempting to log into the system. It seems two things are needed: a properly configure kernel and the auditd service running.

My kernel already has Auditing support enabled so the only thing I need to install is sys-process/audit.

This added /etc/init.d/auditd. Now I can add it to the correct run level and run it.

After looking at syslog-ng, it looks like that is what I should go with. It can be configured to create an auth.log which shows what I want. The default config that comes with gentoo is not very helpful. It only saves messages to the messages log file.

By following [this](http://www.gentoo-wiki.info/Syslog-ng) guide I was able to get logging setup.

