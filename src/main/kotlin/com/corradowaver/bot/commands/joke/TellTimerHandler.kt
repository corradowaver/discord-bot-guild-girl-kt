package com.corradowaver.bot.commands.joke

import java.util.concurrent.atomic.AtomicLong

object TellTimerHandler {
  val lastJokeSentTimestamp = AtomicLong(0)
}
