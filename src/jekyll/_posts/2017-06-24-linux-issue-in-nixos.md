After installing nixos I had some issues with the linux kernel and possibly kde. At boot I would get
errors like this.

https://www.dropbox.com/s/bj50sqgfgy9yp1b/2017-06-20%2020.43.20.jpg?dl=0
https://www.dropbox.com/s/16mj6taamdekqyw/2017-06-21%2021.08.43.jpg?dl=0
https://www.dropbox.com/s/o29rerznr38dkw0/2017-06-21%2021.06.05.jpg?dl=0

And once in Konsole I got this error just before the system froze.


https://www.dropbox.com/s/3rub75hpzfbludm/2017-06-20%2020.30.58.jpg?dl=0

After various searches for smp.c line 127 it seems like a bug in linux. I found 
[this](https://bugs.archlinux.org/task/52246) issue for arch linux which indicates it was fixed for 
linux 4.9.6.

After asking about the issue on reddit it was suggested to add this to my configuration.nix

    boot.kernelPackages = pkgs.linuxPackages_latest

After adding this and performing a full update it appears to have solved my problem.

As a note this command will get the previous log for X

    journalctl -u display-manager --boot=-1