# skype2gitter

[![Build Status](https://travis-ci.org/last-khajiit/skype2gitter.svg?branch=master)](https://travis-ci.org/last-khajiit/skype2gitter)

A Skype bot that echoes activity to Gitter.

#### How to run
- build with Gradle `gradle dist`
- run Jar-file `java -jar skype2gitter-0.0.1.jar {{skype_login}} {{skype_password}} {{gitter_chat_name}} {{gitter_token}}`

#### ToDo
0. fix new line formating
1. add filtering for Skype chats (listen only mentioned chats, not all)
2. add property file support
3. add arguments validation (process cases with non requared arguments)
4. add statistics displaying
5. remove hardcode & refactor ugly code
6. add errors handling
7. write unit tests
8. write manual

*Feel free to make pull requests!*


---

**Copyright © 2016 Last Khajiit <last.khajiit@gmail.com>**

This work is free. You can redistribute it and/or modify it under the
terms of the Do What The Fuck You Want To Public License, Version 2,
as published by Sam Hocevar. See the [COPYING](https://raw.githubusercontent.com/last-khajiit/skype2gitter/master/copying.txt) file for more details.

