---
title: Java Navive Access in Groovy
layout: post
comments: true
---

Using [Java Native Access](https://github.com/java-native-access/jna) in a groovy script seems easy enough. Just use grape to grab the dependency and use it an a simple groovy script.

```groovy
#!/usr/bin/env groovy

// https://mvnrepository.com/artifact/net.java.dev.jna/jna
@Grapes(
    @Grab(group='net.java.dev.jna', module='jna', version='4.2.2')
)

import com.sun.jna.Library
import com.sun.jna.Native
import com.sun.jna.Platform

public interface CLibrary extends Library {
    CLibrary INSTANCE = (CLibrary) Native.loadLibrary((Platform.isWindows() ? 'msvcrt' : 'c'), CLibrary.class)

    void printf(String format, Object... args)
}

CLibrary.INSTANCE.printf("Hello, World\n")
for(int i = 0; i < args.length; i++) {
    CLibrary.INSTANCE.printf('Argument %d: %s\n', i, args[i])
}
```

The [Dynamically Typed Languages](https://github.com/java-native-access/jna/blob/master/www/DynamicallyTypedLanguages.md) article suggests that we can be smart about using jna in Jython and JRuby but what about groovy? Groovy supports intercepting missing methods but I'm going with just defining the methods. There doesn't seem to be a way to map the invoke methods with the correct return type for the c methods. For example printf is a void method but in the article it would use invokeInt. This is not correct.

```groovy
#!/usr/bin/env groovy

// https://mvnrepository.com/artifact/net.java.dev.jna/jna
@Grapes(
    @Grab(group='net.java.dev.jna', module='jna', version='4.2.2')
)
 
import com.sun.jna.NativeLibrary
import com.sun.jna.Platform
 
public class CLib {
    static def lib = NativeLibrary.getInstance(Platform.isWindows() ? 'msvcrt' : 'c')
    static def printf(format, args) { 
        lib.getFunction('printf').invokeVoid([format, args])
    }
}

CLib.printf("Hello, World\n")
for(int i = 0; i < args.length; i++) {
    CLib.printf('Argument %d: %s\n', i, args[i])
}
```

JNA makes it very easy to map native code in groovy. Please check out the [project](https://github.com/java-native-access/jna) for more details.
