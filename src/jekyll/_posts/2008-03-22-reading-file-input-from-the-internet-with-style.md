---
layout: post
title: Reading File Input from the Internet with Style
date=2008-03-22 06:58:00.000000000 -04:00
categories:
- Programming
tags: []
status: publish
type: post
published: true
comments: true
---
<a href="/images/parserstream.png"><img src="/images/parserstream.png" border="0" /></a>

Parsing Problem: Write a ParserStream that can parse tag attributes and simple text patterns. The Stream must also be able to replace those attributes with what the programmer wants.

Solution: The picture is a diagram of my solution (made with <a href="http://live.gnome.org/Dia">http://live.gnome.org/Dia</a>).

Decorator Pattern: I got the idea to use the decorator pattern from "Head First Design Patterns". There is a great example of a Java I/O decorator that converts it's input into lower case. This is the basic pattern that I started with and it grew from there. The main idea is to replace calls to read() with my own methods. My own methods of read will parse and replace the attributes. More on that later.

Factory Pattern: The next chapter in Head First Design Patterns discusses the Factory Pattern. I use the Factory Pattern to return the ParserStream that works with each of the different content types that I would like to parse (html, jpeg, text). In order to create one of these Streams the Factory has to have the content type, the input stream for the content, and the two interfaces for storing the parsed attributes and replacing the attributes.

Interface Pattern: I'm not sure if this is a more specific pattern but the basic idea is to allow the implementation of storing and overwriting the parsed attributes to be programmable. The parser interface is used to store attributes. The information passed to it will allow the programmer to determine the type of attribute (src, href) and the original value of the attribute before it is replaced by the formatting interface. The formattingInterface is used to change the attributes to whatever the programmer wants.

Application: The main reason why I made this is so I can make programs that download web pages and store them locally and crawl to all the source files for each link. In order for the html to link properly on the local computer the src and href attribute values have to be changed. I'm not sure if I will need to parse jpeg or text files but the capability is there.

Things to change: The only problem I see with this approach is that if I want to search for new attributes I will have to rewrite the ParserStream itself. While this is fine for me to do the class wouldn't not be versatile in a library. One thing I will look into is making the attribute strings that the parser looks for work like Regular Expressions. I'm sure there is a way to do it but for now this will work.
