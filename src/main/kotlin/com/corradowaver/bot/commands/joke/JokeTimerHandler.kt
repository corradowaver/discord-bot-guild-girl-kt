package com.corradowaver.bot.commands.joke

import java.util.concurrent.atomic.AtomicLong

object JokeTimerHandler {
  val lastJokeSentTimestamp = AtomicLong(0)
}
