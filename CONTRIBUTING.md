### Commands adding:  
 
For example let's implement ping command.

   - Create new branch and checkout
   - Go to `commands` package
   - Create new `ping` package inside
   - Create `class PingCommand`
      ```java
      @Component
      class PingCommand(val messageBuilder: PingMessage) : Command {
        override val caller: String = "ping" //NOTE! val caller is representing how will you call command (e.g. !ping)
        override val description: String = "Ping command description"
        override lateinit var event: GuildMessageReceivedEvent
        
        /*
          args - are everything you pass after your command
          e.g. !ping kek lol
          where 
            args[0] == kek
            args[1] == lol
        */
        override fun invoke(args: List<String>) {
          // Calculating latency.
          messageBuilder.latency(latency).send(event)
        }
      }
      ```
   - Create `object PingMessage`
      ```java
      @Component
      final object PingMessage : MessageBuilder() {

         /*
           Use your property you want to pass to message.
         */
        private var latency = 0L
        fun latency(latency: Long) = apply { this.latency = latency }

        override fun send(event: GuildMessageReceivedEvent) {
          event.channel.sendMessage(createMessage()).queue()
        }

         /*
           Inside this method you can customize your message.
           Example below.
         */
        override fun createMessage(): MessageEmbed {
          return super
            .setTitle("$latency ms")
            .setDescription("Pong-pong")
            .setColor(GREEN)
            .build()
        }
      }
      ```
   - Open pull request
   - You are done!  
     
