package com.corradowaver.kreed.bot.commads

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent

interface Command {
  val caller: String
  var event: GuildMessageReceivedEvent
  fun invoke(args: List<String>)
}
