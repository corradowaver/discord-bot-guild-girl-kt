package com.corradowaver.bot.commands.joke

enum class JokeType(val id: Int) {
  CLASSIC(1),
  STORY(2),
  POEM(3),
  CLASSIC_18(11),
  STORY_18(12),
  POEM_18(13)
}

fun JokeType.adultVersion(): JokeType =
  JokeType.values().find { it.id == this.id + 10 }!!

