# skype2gitter

[![release](https://img.shields.io/badge/release-v0.0.1-brightgreen.png?style=default)](https://github.com/last-khajiit/skype2gitter/releases/latest) [![Build Status](https://travis-ci.org/last-khajiit/skype2gitter.svg?branch=master)](https://travis-ci.org/last-khajiit/skype2gitter)

A Skype bot that echoes activity to Gitter.

#### How to run
- build with Gradle `gradle dist`
- run Jar-file `java -jar skype2gitter-0.0.1.jar {{skype_login}} {{skype_password}} {{gitter_chat_name}} {{gitter_token}}`

You can deploy it to [Heroku](heroku.com) with Heroku Toolbelt, there are few steps:
- clone skype2gitter
- login to heroku - `heroku login`
- create Heroku app  - `heroku create %your_app_name%`
- set up config vars (SKYPE_LOGIN, SKYPE_PASSWORD, GITTER_CHAT_NAME, GITTER_TOKEN) with comand - `heroku config:set %ENV_VAR_NAME%=%var_value%`
- deploy app - `git push heroku master`
- for avoiding your app go to sleep use any ping services, for instance [Kaffeine](http://kaffeine.herokuapp.com/)


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

