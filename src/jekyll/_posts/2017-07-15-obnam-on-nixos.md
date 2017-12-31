---
title: obnam on nixos
layout: post
comments: true
---
[obnam](http://www.obnam.org) is a backup program I have used 
[used](http://moaxcp.github.io/2015/09/09/obnam-on-gentoo.html) 
in the past to backup my gentoo system. Now that I am using nixos I would like to setup backups for a number of reasons:

1. I am on satalite internet so building the nix store, and downloading files from dropbox again could cost a lot of time.
2. there are still a lot of files that are not in the nixstore.

Setting up obnam on nixos starts with installing it. This is done by adding obnam to the systemPackages.

```nix
environment.systemPackages = with pkgs; [
  obnam
];
```

The next part is setting up the configuration. obnam search for a config in the following order.

```
/etc/obnam.conf
/etc/obnam/*.conf
~/.obnam.conf
~/.config/obnam/*.conf
```

Nixos is able to create an etc file from it's configuration with the `environment.etc` attribute. To create `/etc/obnam.conf` 
add this code to `configuration.nix`.

```nix
environment.etc."obnam.conf" = {
  enable = true;
  text = ''
    [config]
    repository = /data/obnam/all
    client-name = n1
    log = /data/obnam/all.log
    root = /
    one-file-system = yes
    exclude = /data
  '';
};
```

At this point (once `nixos-rebuild switch` is run) obnam can be run manually as root. It would be nice if backups were run 
daily though. This can be setup with fcron.

```nix
services.fcron = {
  enable = true;
  allow = [ "john" ];
  systab = ''
    @ 1d obnam backup
  '';
};
```

The above code will install fcron and setup a job to run obnam once a day. Since systemd is used on nixos it would be better 
to use a timer instead of fcron. This can be done by removing `services.fcron` and adding a systemd service and timer for 
obnam.

```nix
systemd.services.obnam = {
  description = "Perform system backup";
  script = "${pkgs.obnam}/bin/obnam backup";
}; 
 
systemd.timers.obnam = {
  description = "run obnam every day";
  wantedBy = [ "timers.target" ];
  timerConfig = {
    OnCalendar = "daily";
    Persistent = true;
  };
};
```
