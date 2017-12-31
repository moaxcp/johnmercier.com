---
title: Adding JBake to nixpkgs
layout: post
comments: true
---

[JBake](http://jbake.org/) is a static site/blog generator. I have been
using jekyll for this blog but I would like to try something new. My
main operating system is [NixOS](https://nixos.org/) which does not
have a package to install [JBake](http://jbake.org/). It will need to
be added. I have already updated
[visualvm](https://visualvm.github.io/) and
[notion](https://github.com/raboof/notion) for
[NixOS](https://nixos.org/) but adding a new package is something I
haven't tried yet.

This post will walk through the process of developing the
[JBake](http://jbake.org/) nix expression for the
[nixpkgs](https://github.com/NixOS/nixpkgs) repository.

# nixpkgs workflow

Contributing to [nixpkgs](https://github.com/NixOS/nixpkgs) is a
great workflow with the goal of having an easy to understand log. It is
not a typical workflow and some basic understanding is required to
contribute to it. [nixpkgs](https://github.com/NixOS/nixpkgs) expects
only one commit when adding a new package. Normally I would add a
commit message for any small change. With the
[nixpkgs](https://github.com/NixOS/nixpkgs) workflow a local branch can
be used to make small commits which can then be squashed on the master
branch. There is also two upstream repositories that are used for
development. One is used to develop from and the other is used for
pull requests. Soon this will make more sense but for now lets setup
the fork.

## creating a fork, adding channels, nixpkgs to fork

Creating a fork is easy with GitHub. Click fork in the top-right corner
of the [page](https://github.com/NixOS/nixpkgs). More information can
be found [here](https://help.github.com/articles/fork-a-repo).

Once the fork under your github account clone it to your local machine.

```
git clone https://github.com/moaxcp/nixpkgs.git
```

Next, add two remotes.

```
git remote add channels git://github.com/NixOS/nixpkgs-channels.git
git remote add nixpkgs https://github.com/NixOS/nixpkgs.git
```

`channels` are collections of packages for the nix package manager. It
is useful when you want to reuse what is already built on your system.
`channels` will be used for the local branch to develop the package.

`nixpkgs` is the remote the fork was made from. It is the upstream of
this fork. It is used to update master of this fork and to submit pull
requests.

## syncing master with upstream when outdated

From time to time the master will be outdated and need to be synced
with [nixpkgs](https://github.com/NixOS/nixpkgs).

```
git fetch nixpkgs
git checkout master
git reset --hard nixpkgs/master
git push origin master --force
```

This will help prevent conflicts when a pull request is made.

## creating local branch for current commit of NixOS

The version of [NixOS](https://nixos.org/) being used contains the git commit from `channels`.

```
nix-version
17.09.2476.53e6d671a96 (Hummingbird)
```

`53e6d671a96` should be used to create the local branch. This is a good
practice. If you use a different commit you risk building all of [NixOS](https://nixos.org/)
just to test a few changes.

If your system is ever updated the local branch will need to be rebased
with that commit.

```
git checkout local
git rebase <NixOS commit>
```

It is also ok to delete the local branch if the pull request is already.

```
git branch -d local
```

# adding JBake to local branch of nixpkgs

Within [nixpkgs](https://github.com/NixOS/nixpkgs) there is a large
hierarchy of files. For [JBake](http://jbake.org/) it makes sense to
add it under `development/tools/jbake/default.nix`. `all-packages.nix`
also needs to be changed to include [JBake](http://jbake.org/) so it
can be installed by users. [JBake](http://jbake.org/) should be added
to `all-packages.nix` first so it can be found by `nix-build` during
the development process.

## all-packages.nix

First, edit `pkgs/top-level/all-packages.nix` and add the `jbake`
attribute.

```
jbake = callPackage ../development/tools/jbake { };
```

## default.nix

Starting out we know a few things about [JBake](http://jbake.org/).
Nix needs to download the package. And the name to use is
`jbake-2.5.1`. This is enought to start writing the expression.

Edit `pkgs/development/tools/jbake/default.nix` and add the following:

```
{stdenv, fetchurl}:

stdenv.mkDerivation {
    name = "jbake-2.5.1";
    src = fetchurl {
        url = "";
        sha256="";
    };
}
```

The the attribute added to `all-packages.nix` and `default.nix` added
we can attempt to build `jbake`. Lets build this file and see what
happens. Run nix-build from the root of repository.

```
nix-build -A jbake
error: assertion failed at /etc/user/john/projects/nixpkgs/pkgs/build-support/fetchurl/default.nix:90:1
(use ‘--show-trace’ to show detailed location information)
```

This is probably because the file is missing a url. Set the url in
`default.nix`.

```
url = "http://jbake.org/files/jbake-2.5.1-bin.zip";
```

Notice that how the url also contains the name of the derivation. It
would be nice to reuse the name value.

```
{stdenv, fetchurl}:

stdenv.mkDerivation {
    name = "jbake-2.5.1";
    src = fetchurl {
        url = "http://jbake.org/files/${name}-bin.zip";
        sha256="";
    };
}
```

Save the changes and run `nix-build` again.

```
nix-build -A jbake
error: undefined variable ‘name’ at 
/etc/user/john/projects/nixpkgs/pkgs/development/tools/jbake/default.nix:6:41
```

We get a different error...

In a nix expression attributes cannot be reused in a set unless the set
is recursive. Add rec to the set:

```
{stdenv, fetchurl}:

stdenv.mkDerivation rec {
    name = "jbake-2.5.1";
    src = fetchurl {
        url = "http://jbake.org/files/${name}-bin.zip";
        sha256="";
    };
}
```

The name can now be reused within the expression. Run `nix-build`
again.

```
nix-build -A jbake
error: Specify hash for fetchurl fixed-output derivation: http://jbake.org/files/jbake-2.5.1-bin.zip
(use ‘--show-trace’ to show detailed location information)
```

This is a result of the missing hash. I don't know the hash but using
an invalid hash will give us the real hash to use. Add an invalid hash
to the file and run `nix-build` again.

```
sha256="1gkdkxssh51jczhgv680i42jjrlia1vbpcqhxvf45xcq9xj95bm5";
```

`nix-build` will download the file but give the following error:

```
output path ‘/nix/store/4lj8xga9nxwc2my60z9pjh7r37arhxmx-jbake-2.5.1-bin.zip’ has sha256 hash 
‘1r46y84q5x915055hx2vxydaqng3cz0clwz0yhwapgmi4sliygjd’ when 
‘1gkdkxssh51jczhgv680i42jjrlia1vbpcqhxvf45xcq9xj95bm5’ was expected
cannot build derivation ‘/nix/store/wnmw6yq876ga9anf59lxwjrc6nddwskx-jbake-2.5.1.drv’: 1 dependencies couldn't 
be built
error: build of ‘/nix/store/wnmw6yq876ga9anf59lxwjrc6nddwskx-jbake-2.5.1.drv’ failed
```

This gives the hash to use. Set the hash in `default.nix`.

```
sha256="1r46y84q5x915055hx2vxydaqng3cz0clwz0yhwapgmi4sliygjd";
```

Running nix-build again results in

```
nix-build -A jbake
these derivations will be built:
  /nix/store/r0zvmmjc36wdssyhj1q1ja9lsqwqqlav-jbake-2.5.1.drv
building path(s) ‘/nix/store/a5492adfv7sx9qrilgqf591s3pdqly0k-jbake-2.5.1’
unpacking sources
unpacking source archive /nix/store/4xwch46y38rdca1zh3zx6z0kk2janiyr-jbake-2.5.1-bin.zip
do not know how to unpack source archive /nix/store/4xwch46y38rdca1zh3zx6z0kk2janiyr-jbake-2.5.1-bin.zip
builder for ‘/nix/store/r0zvmmjc36wdssyhj1q1ja9lsqwqqlav-jbake-2.5.1.drv’ failed with exit code 1
error: build of ‘/nix/store/r0zvmmjc36wdssyhj1q1ja9lsqwqqlav-jbake-2.5.1.drv’ failed
```

Which means `stdenv` does not know how to unpack the downloaded file
because it is a `zip` rather than a `tar.gz`. Looking back at
[JBake files](http://jbake.org/files/) there is no `tar.gz` download.
There are some examples of this in
[nixpkgs](https://github.com/NixOS/nixpkgs) where a `zip` file is
downloaded and unzipped in the nix expression using `unzip`. In these
cases, `unpackPhase` is changed, adding the `unzip` command. Add the
code to `unzip` the file:

```
{stdenv, fetchurl, unzip}:

stdenv.mkDerivation rec {
    name = "jbake-2.5.1";
    src = fetchurl {
        url = "http://jbake.org/files/jbake-2.5.1-bin.zip";
        sha256="1r46y84q5x915055hx2vxydaqng3cz0clwz0yhwapgmi4sliygjd";
    };

    buildInputs = [unzip];
    unpackPhase = "unzip ${src}";
}
```

When this is run with `nix-build` we get a new error.

```
...
  inflating: jbake-2.5.1/lib/thymeleaf-3.0.3.RELEASE.jar
patching sources
configuring
no configure script, doing nothing
building
no Makefile, doing nothing
installing
install flags: install
make: *** No rule to make target 'install'.  Stop.
builder for ‘/nix/store/wjhxbm803yagw32kz1hyfys0k8n3kdnq-jbake-2.5.1.drv’ failed with exit code 2
error: build of ‘/nix/store/wjhxbm803yagw32kz1hyfys0k8n3kdnq-jbake-2.5.1.drv’ failed
```

This means `stdenv` was not able to build the package using `make`.
[JBake](http://jbake.org/) is already built and we only need to install
it. This can be done by changing the `installPhase`.

```
{stdenv, fetchurl, unzip}:

stdenv.mkDerivation rec {
    name = "jbake-2.5.1";
    src = fetchurl {
        url = "http://jbake.org/files/${name}-bin.zip";
        sha256="1r46y84q5x915055hx2vxydaqng3cz0clwz0yhwapgmi4sliygjd";
    };

    buildInputs = [unzip];
    unpackPhase = "unzip ${src}";
    installPhase = ''
        mkdir -p $out
        cp -r $name/* $out
    '';
}
```

`stdenv` provides `$out` as the directory to install the package into.
It needs to be created and the files copied over. The `zip` contains
one directory called `jbake-2.5.1` and all of the files in it.
`cp -r $name/* $out` will copy all of those files into `$out` Now when
we run nix-build we get

```
...
  inflating: jbake-2.5.1/lib/thymeleaf-3.0.3.RELEASE.jar
patching sources
configuring
no configure script, doing nothing
building
no Makefile, doing nothing
installing
post-installation fixup
shrinking RPATHs of ELF executables and libraries in /nix/store/1cqhcxd4d64r6bgr4ssvk1yi6h5jyx37-jbake-2.5.1
stripping (with flags -S) in /nix/store/1cqhcxd4d64r6bgr4ssvk1yi6h5jyx37-jbake-2.5.1/lib  
/nix/store/1cqhcxd4d64r6bgr4ssvk1yi6h5jyx37-jbake-2.5.1/bin
patching script interpreter paths in /nix/store/1cqhcxd4d64r6bgr4ssvk1yi6h5jyx37-jbake-2.5.1
/nix/store/1cqhcxd4d64r6bgr4ssvk1yi6h5jyx37-jbake-2.5.1/bin/jbake: interpreter directive changed from 
"/bin/bash" to "/nix/store/hqi64wjn83nw4mnf9a5z9r4vmpl72j3r-bash-4.4-p12/bin/bash"
checking for references to /tmp/nix-build-jbake-2.5.1.drv-0 in 
/nix/store/1cqhcxd4d64r6bgr4ssvk1yi6h5jyx37-jbake-2.5.1...
/nix/store/1cqhcxd4d64r6bgr4ssvk1yi6h5jyx37-jbake-2.5.1
```

nix-build creates a link to $out called `result` in the current
directory for us.

```
ls result
bin  example_project_freemarker.zip  example_project_groovy-mte.zip  example_project_groovy.zip  example_project_jade.zip  example_project_thymeleaf.zip  jbake-core.jar  lib  LICENSE
```

This looks correct. `stdenv` will automatically add the `bin` directory
to `PATH` when a user installs [JBake](http://jbake.org/). Executables
in `bin` need to be checked for anything that may not work. Viewing
`bin/jbake` shows the application uses `java`. This means `java` is a
dependency. It needs to be installed by adding `jre` to the expression.

```
{stdenv, fetchurl, unzip, jre}:
```

There is still a problem though. The executable may not be using the
verison of `java` installed by `nix`. To fix this the executable needs
to use the real path to the `java` executable installed by the `jre`
dependency. `stdenv` provides the function `substituteInPlace` which
will fix the `jbake` script during the installPhase.

```
{stdenv, fetchurl, unzip, jre}:

stdenv.mkDerivation rec {
    name = "jbake-2.5.1";
    src = fetchurl {
        url = "http://jbake.org/files/${name}-bin.zip";
        sha256="1r46y84q5x915055hx2vxydaqng3cz0clwz0yhwapgmi4sliygjd";
    };

    buildInputs = [unzip];
    unpackPhase = "unzip ${src}";
    installPhase = ''
        substituteInPlace $name/bin/jbake --replace "java" "${jre}/bin/java"
        mkdir -p $out
        cp -r $name/* $out
    '';
}
```

The executable now uses the `java` dependency installed in the
nix-store.

```
/nix/store/jgpc401wdfg1gibvnlp60ddav4fi68ys-openjdk-8u152b16-jre/bin/java -jar "${EXEC_PARENT}/jbake-core.jar" $@
```

The results look good and now `meta` needs to be added to the file.
`meta` contains details about the package for users to view. It is also
a way to add yourself as a maintainer.

First make sure you are in `maintainers.nix`.

```
  moaxcp = "John Mercier <moaxcp@gmail.com>";
```

The file is in alphabetical order by attribute name.

Then, add `meta` to `default.nix`.

This is the final version of `default.nix`.

```
{stdenv, fetchurl, unzip, jre}:

stdenv.mkDerivation rec {
    name = "jbake-2.5.1";
    src = fetchurl {
        url = "http://jbake.org/files/${name}-bin.zip";
        sha256="1r46y84q5x915055hx2vxydaqng3cz0clwz0yhwapgmi4sliygjd";
    };

    buildInputs = [unzip];
    unpackPhase = "unzip ${src}";
    installPhase = ''
        substituteInPlace $name/bin/jbake --replace "java" "${jre}/bin/java"
        mkdir -p $out
        cp -r $name/* $out
    '';

    meta = with stdenv.lib; {
        description = "JBake is a Java based, open source, static site/blog generator for developers & designers";
        homepage = "http://jbake.org/";
        license = licenses.mit;
        maintainers = with maintainers; [ moaxcp ];
    };
}
```

Make sure the file works with `nix-build` and also install it with
`nix-env`.

From nixpkgs run.

```
nix-build
nix-env -f . -iA jbake
```

# squashing commits in local branch

Once the expression is ready for a pull request a single commit needs
to be added to the master branch. If there were many commits during
development they can be squashed. Squashing commits can be done
following
[this](http://gitready.com/advanced/2009/02/10/squashing-commits-with-rebase.html)
guide. Make sure the commit message follows the
[guidelines](https://github.com/NixOS/nixpkgs/blob/master/.github/CONTRIBUTING.md)
for [nixpkgs](https://github.com/NixOS/nixpkgs). The commit message for
this change is.

```
jbake: init at 2.5.1
```

# cherry-pick commits to master branch

View `git log` and find the commit hash.

```
commit dc521d4ad609743008bfa31c0d681c8b4141e815 (HEAD -> local, origin/local)
Author: John Mercier <moaxcp@gmail.com>
Date:   Tue Dec 26 20:06:19 2017 -0500

    jbake: init at 2.5.1
```

Switch to master and cherry-pick the commit.

```
git cherry-pick dc521d4ad609743008bfa31c0d681c8b4141e815
```

# create pull request to add jbake

Using GitHub navigate to NixOS/nixpkgs and add a pull request. Follow
the template. The review should only take a few days. The review for
[JBake](http://jbake.org/) is
[here](https://github.com/NixOS/nixpkgs/pull/33101).

# Conclusion

Adding packages to [nixpkgs](https://github.com/NixOS/nixpkgs) can be
fun. There is a lot of packages especially for java that should be
added/maintained. I hope this helps in some way.
