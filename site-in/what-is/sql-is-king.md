---
title: SQL is king!
---

Let's acknowledge that SQL is king in the data domain.
The primary workflow in typo is to write SQL in SQL files!

This has many advantages:
- queries exist in a fully exploded form, there are no conversion steps to/from source code
- tooling support is superb, for IDEs and LLMs alike
- it accepts parameters
- centralization: you can see all the queries used by (part of) a system in one place. Your DBA can script analyses of them, for instance.

Let's see an example right away! In the video below you can see the generated code change as the sql file is edited.

<video
width="100%"
controls
autoplay="autoplay"
src="https://github.com/oyvindberg/typo/assets/247937/df7c4f2d-b118-4081-81c6-dd03dfe62ee2"
/>


## Parameters

This video shows how you can use parameters. 

It also shows the syntax to override inferred nullability and types.
While postgres is pretty good at both, it's not always exactly what you want.

<video
width="100%"
controls
autoplay="autoplay"
src="https://github.com/oyvindberg/typo/assets/247937/b2965b74-8ee5-4475-8e40-5938ff44d385"
/>
