# skype2gitter

[![release](https://img.shields.io/badge/release-v0.0.1-brightgreen.png?style=default)](https://github.com/last-khajiit/skype2gitter/releases/latest) [![Build Status](https://travis-ci.org/last-khajiit/skype2gitter.svg?branch=master)](https://travis-ci.org/last-khajiit/skype2gitter)

A Skype bot that echoes activity to Gitter.

#### How to build and run
- build with Gradle `gradle dist`, it will generate zip archive in `build/dist` directory
- unpack archive and populate `skype2gitter.properties`, there are 4 required property (skype.login, skype.password, gitter.chatname, gitter.token)
- run startup script (.bat or .sh)
- to be sure that app was started check console output of log file
- for viewing of statistics open Statistics page in browser (default url `localhost:8080/`, can be changed in properties)


#### ToDo
1. remove hardcode & refactor ugly code
2. add filtering for Skype chats (listen only mentioned chats, not all)
3. write unit/functional tests
4. write documentation

*Feel free to make pull requests!*


---

**Copyright © 2016 Last Khajiit <last.khajiit@gmail.com>**

This work is free. You can redistribute it and/or modify it under the
terms of the Do What The Fuck You Want To Public License, Version 2,
as published by Sam Hocevar. See the [COPYING](https://raw.githubusercontent.com/last-khajiit/skype2gitter/master/copying.txt) file for more details.

