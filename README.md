# skype2gitter

[![release](https://img.shields.io/badge/release-v0.0.1-brightgreen.png?style=default)](https://github.com/last-khajiit/skype2gitter/releases/latest) [![Build Status](https://travis-ci.org/last-khajiit/skype2gitter.svg?branch=master)](https://travis-ci.org/last-khajiit/skype2gitter)

A Skype bot that echoes activity to Gitter.

#### How to build and run
- build with Gradle `gradle dist`, it will generate zip archive in `build/dist` directory
- run Jar-file `java -jar skype2gitter-0.0.1.jar {{skype_login}} {{skype_password}} {{gitter_chat_name}} {{gitter_token}}`


#### ToDo
1. add errors handling
2. remove hardcode & refactor ugly code
3. add filtering for Skype chats (listen only mentioned chats, not all)
4. write unit/functional tests
5. write manual, examples

*Feel free to make pull requests!*


---

**Copyright © 2016 Last Khajiit <last.khajiit@gmail.com>**

This work is free. You can redistribute it and/or modify it under the
terms of the Do What The Fuck You Want To Public License, Version 2,
as published by Sam Hocevar. See the [COPYING](https://raw.githubusercontent.com/last-khajiit/skype2gitter/master/copying.txt) file for more details.

