---
layout: post
title: Gentoo on the Java x86 emulator
date: 2015-02-01 04:51:01.000000000 -05:00
categories: []
tags: []
status: publish
type: post
published: true
comments: true
---
Java has an <a href="http://jpc.sourceforge.net/download_application.html">x86 emulator</a> that claims to run linux. After attempting the demo links for other distributions that do not work I decided to try to install gentoo on it myself.

1. create a disk from the disks menu.

    hda 2gb

2. start jpc

    java -jar JPCApplication.jar -boot cdrom -cd
    rom gentoo\install-x86-minimal-20141209.iso -hda gentoo\hda.imgj

Note: could not use absolute paths (bad programming for c:)

3. File -> Start

4. Error

```
C:\Users\megan\Dropbox\Software\jcp>java -jar JPCApplication.jar -boot cdrom -cd
rom gentoo\install-x86-minimal-20141209.iso -hda gentoo\hda.img
Jan 31, 2015 10:05:09 PM org.jpc.j2se.JPCApplication main
INFO: Using configuration specified on command line
Jan 31, 2015 10:05:09 PM org.jpc.support.HDBlockDevice <init>
INFO: no geometry information, guessing CHS 1,040:16:63
Jan 31, 2015 10:05:09 PM org.jpc.emulator.memory.codeblock.CodeBlockManager <init>
INFO: JVM allows classloader creation: using advanced compilers.
Jan 31, 2015 10:05:16 PM org.jpc.emulator.motherboard.Bios print
INFO: $Revision: 1.209 $ $Date: 2008/06/02 20:08:10 $
Jan 31, 2015 10:05:16 PM org.jpc.emulator.motherboard.Bios print
INFO: VGABios $Id: vgabios.c,v 1.67 2008/01/27 09:44:12 vruppert Exp $
Jan 31, 2015 10:05:16 PM org.jpc.emulator.motherboard.Bios print
INFO: VBE Bios $Id: vbe.c,v 1.60 2008/03/02 07:47:21 vruppert Exp $
Jan 31, 2015 10:05:16 PM org.jpc.emulator.motherboard.Bios print
INFO: Starting rombios32
Jan 31, 2015 10:05:17 PM org.jpc.emulator.motherboard.Bios print
INFO: ram_size=0x10000000
Jan 31, 2015 10:05:17 PM org.jpc.emulator.motherboard.Bios print
INFO: Found 1 cpu(s)
Jan 31, 2015 10:05:17 PM org.jpc.emulator.motherboard.Bios print
INFO: bios_table_addr: 0x000fb778 end=0x000fcc00
Jan 31, 2015 10:05:17 PM org.jpc.emulator.motherboard.Bios print
INFO: PIIX3 init: elcr=00 0a
Jan 31, 2015 10:05:17 PM org.jpc.emulator.motherboard.Bios print
INFO: PCI: bus=0 devfn=0x00: vendor_id=0x8086 device_id=0x1237
Jan 31, 2015 10:05:17 PM org.jpc.emulator.motherboard.Bios print
INFO: PCI: bus=0 devfn=0x08: vendor_id=0x10ec device_id=0x8029
**


**

Address is c000
Jan 31, 2015 10:05:17 PM org.jpc.emulator.motherboard.Bios print
INFO: region 0: 0x0000c000
Jan 31, 2015 10:05:17 PM org.jpc.emulator.motherboard.Bios print
INFO: PCI: bus=0 devfn=0x10: vendor_id=0x8086 device_id=0x7000
Jan 31, 2015 10:05:17 PM org.jpc.emulator.motherboard.Bios print
INFO: PCI: bus=0 devfn=0x11: vendor_id=0x8086 device_id=0x7010
Jan 31, 2015 10:05:17 PM org.jpc.emulator.motherboard.Bios print
INFO: region 4: 0x0000c100
Jan 31, 2015 10:05:17 PM org.jpc.emulator.motherboard.Bios print
INFO: PCI: bus=0 devfn=0x18: vendor_id=0x1234 device_id=0x1111
Jan 31, 2015 10:05:17 PM org.jpc.emulator.motherboard.Bios print
INFO: region 0: 0xe0000000
Jan 31, 2015 10:05:17 PM org.jpc.emulator.motherboard.Bios print
INFO: MP table addr=0x000fb850 MPC table addr=0x000fb780 size=0xd0
Jan 31, 2015 10:05:17 PM org.jpc.emulator.memory.EPROMMemory writeAttempted
INFO: Write of 4 bytes attempted at address 0x8d3
Jan 31, 2015 10:05:17 PM org.jpc.emulator.memory.EPROMMemory writeAttempted
INFO: Write of 4 bytes attempted at address 0x8e4
Jan 31, 2015 10:05:17 PM org.jpc.emulator.memory.EPROMMemory writeAttempted
INFO: Write of 4 bytes attempted at address 0x8e8
Jan 31, 2015 10:05:17 PM org.jpc.emulator.memory.EPROMMemory writeAttempted
INFO: Write of 4 bytes attempted at address 0x904
Jan 31, 2015 10:05:17 PM org.jpc.emulator.memory.EPROMMemory writeAttempted
INFO: Write of 4 bytes attempted at address 0x92f
Jan 31, 2015 10:05:17 PM org.jpc.emulator.memory.EPROMMemory writeAttempted
INFO: Write of 4 bytes attempted at address 0x933
Jan 31, 2015 10:05:17 PM org.jpc.emulator.memory.EPROMMemory writeAttempted
INFO: Write of 4 bytes attempted at address 0x940
Jan 31, 2015 10:05:17 PM org.jpc.emulator.memory.EPROMMemory writeAttempted
INFO: Write of 4 bytes attempted at address 0x944
Jan 31, 2015 10:05:17 PM org.jpc.emulator.memory.EPROMMemory writeAttempted
INFO: Write of 4 bytes attempted at address 0x878
Jan 31, 2015 10:05:17 PM org.jpc.emulator.motherboard.Bios print
INFO: SMBIOS table addr=0x000fb860
Jan 31, 2015 10:05:17 PM org.jpc.emulator.motherboard.Bios print
INFO: bios_table_cur_addr: 0x000fb964
Jan 31, 2015 10:05:17 PM org.jpc.emulator.motherboard.Bios print
INFO: ata0-0: PCHS=1040/16/63 translation=lba LCHS=1024/16/63
Jan 31, 2015 10:05:17 PM org.jpc.emulator.motherboard.Bios print
INFO: IDE time out
Jan 31, 2015 10:05:17 PM org.jpc.emulator.motherboard.Bios print
INFO: IDE time out
Jan 31, 2015 10:05:19 PM org.jpc.emulator.motherboard.Bios print
INFO: Booting from 0000:7c00
Jan 31, 2015 10:05:19 PM org.jpc.emulator.memory.codeblock.fastcompiler.FASTComp
iler compileRealModeBlock
INFO: java.lang.NullPointerException: unimplemented microcode: PUSHAD_A16
Jan 31, 2015 10:05:20 PM org.jpc.emulator.motherboard.Bios print
INFO: KBD: unsupported int 16h function 03
Jan 31, 2015 10:05:20 PM org.jpc.emulator.motherboard.Bios print
INFO: *** int 15h function AX=e980, BX=0000 not yet supported!
```

5. Started damn small linux

    java -jar JPCApplication.jar -boot cdrom -cd rom dsl\dsl-4.4.10.iso -hda gentoo\gentoo.img

```
Java HotSpot(TM) 64-Bit Server VM warning: CodeCache is full. Compiler has been
disabled.
Java HotSpot(TM) 64-Bit Server VM warning: Try increasing the code cache size us
ing -XX:ReservedCodeCacheSize=
CodeCache: size=245760Kb used=239785Kb max_used=240710Kb free=5974Kb
 bounds [0x000000000f010000, 0x000000001e010000, 0x000000001e010000]
 total_blobs=13616 nmethods=13063 adapters=463
 compilation: enabled
 ```
 
6. Restarted damn small linux

    java -XX:ReservedCodeCacheSize=2048m -jar JPCApplication.jar -boot cdrom -cdrom dsl\dsl-4.4.10.iso -hda gentoo\gentoo.img

This is still not starting correctly. Also noticed uptime was 45 minutes after about 10 minutes.

7. Switching to System Rescue CD

    java -XX:ReservedCodeCacheSize=2048m -jar JPCApplication.jar -boot cdrom -cdrom gentoo\systemrescuecd-x86-4.4.1.iso -hda gentoo\gentoo.img

Not working!

8. Damn Small Linux in text mode (dsl 2)

Works in run level 2!

9. After looking into the ethernet setup I found that an internal network can be setup with the -net argument but it only works between two vms on the same computer. It doesn't actually connect to the internet through the host system.

Since this emulator cannot connect to the internet. I have to rethink the usefulness of jpc. If it is not possible to setup a network how can it be used for any form of processing? It either needs to have networking or access to files on the host system. It needs I/O to the host system other than the keyboard, mouse, and monitor. It may be possible to forward everything from the EthernetHubServer to the host system network card somehow in java but I'm not sure how.

This was a fun experiment but JPC is only useful for playing dos games.

