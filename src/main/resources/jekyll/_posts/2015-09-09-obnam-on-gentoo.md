---
title: obnam on gentoo
layout: post
comments: true
---

My system is a Dell Precision M4600 laptop with gentoo installed. It is great for travel and moving to different rooms in the home. Unfortunately, it does not have full backups. I currently have dropbox for my personal data but after after working on this system for a year, I would like to have backups for the entire system. The solution is to have real backups along with dropbox. 

##Dropbox

I love dropbox but there are a few problems with only using dropbox as a backup system. First, it does not work well with gir repositories. It is a duplicate version control system that sometimes has conflicts. When dropbox has a conflict for a git repository it can be annoying but I really want my repositories to be backed up. Second, if my system goes down I have to download the entire dropbox folder (40+GB). Finally, it doesn't back up the entire system.

This is what I like about dropbox

1. quickly sync with phone (pictures/videos)
2. over the network sync with other computers
3. 30 days of history
4. works anywhere with internet

##The Plan

My plan is to have a backup system that works well for me. Something that doesn't require an external hard drive and allows me to do backups while traveling. Right now I only have an external drive so this is it for now:

1. plug in and mount external drive
2. setup obnam to backup system to drive
3. move git repositories outside of Dropbox folder

In the future:

1. install internal drive (2TB)
2. setup obnam to backup system to drive

If I am able to get a server setup at home:

1. create repository on server
2. setup obnam to backup system to server along with internal drive

Obnam looks like a great system. It has one command with many features. The features I'm interested in are the ease of use and encryption. Eventually, I would like to have my system encrypted and have the backup encrypted as well.

Currently, I have an external 1TB drive to put backups on. It is not ideal because I hate having extra things attached to my laptop but it will let me setup backups without having to buy a new harddrive (I tried taking it apart but the usb is permanently attached to the drive).

###Mounting the drive
I have autofs installed and it will automatically mount my drive to /mnt/auto/sdc1.

###Configuring obnam
Gentoo comes with a well documented config file for obnam. First, I set the repository to /mnt/auto/sdc1/obnam. I enabled compression with

    compress-with = deflate
    
I added root, exclude, and one-file-system

    root = /bin, /boot, /etc, /home, /lib32, /lib64, /opt, /root, /sbin, /usr, /var
    exclude = ^/var/tmp/, .*\.pid$, \.cache/, ^/usr/src/linux.*/, ^/var/tmp/portage/, .*/.local/share/Trash/
    one-file-system = true

I may just change root to / because one-file-system skips everything I don't want to backup.

I enabled logging

    log = /var/log/obnam/obnam.log
    log-level = debug
    log-keep = 10
    log-max = 0
    log-mode = 0600
    
I forgot to setup the forget policy but according to the documentation this isn't needed until the backups build up.

Once the config is finished it is easy to start obnam

    obnam backup

