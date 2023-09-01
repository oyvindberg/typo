---
title: Compatible with your project
---

Much care has been taken while developing typo to support many existing projects.

If you have a Scala 2 project with thousands of lines of database code, you have the option
of integrating typo piece-by-piece into your code base.

## Developed in the Scala 2/3 shared subset

Everything works on 2.12, 2.13, and 3.3.

## Bring your own DB library

Not wanting to invent all the wheels, typo expects you to already use a Scala database library.

For now you have the option of `doobie` or `anorm`, with `skunk` to follow. Maybe also plain `jdbc` mode later.

## Bring your own JSON library

In the same manner, you can generate JSON codecs for the library you already use, see [json](other-features/json.md)