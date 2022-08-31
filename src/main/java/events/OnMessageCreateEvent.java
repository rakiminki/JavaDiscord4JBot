package events;

import Commands.*;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import enums.Config;
import enums.Locations;
import manager.ResponseManager;
import model.ChatResponse;
import reactor.core.publisher.Mono;

import java.util.Map;

public class OnMessageCreateEvent {
    private static ResponseManager basicResponseManager = ResponseManager.accessMap.get("basic");

    public static Mono call(GatewayDiscordClient gateway) {
        Mono action = gateway.on(MessageCreateEvent.class, event -> {
            Message message = event.getMessage();
            String content = message.getContent();
            // Check if Message is written by a bot
            if (message.getAuthor().get().isBot()) return Mono.empty();
            // Check if Message starts with command prefix
            String[] splittedContent = content.split(" ");
            String prefix = splittedContent[0];
            if (prefix.strip().equalsIgnoreCase(Config.PREFIX.value)) {
                String command = splittedContent[1].toUpperCase();
                switch (command) {
                    case "PING":
                        return Ping.command(message);
                    case "ADD":
                        return Add.command(message);
                    case "REMOVE":
                        return Remove.command(message);
                    case "LIST":
                        return List.command(message);
                    case "LIST+":
                        return ListPlus.command(message);
                    case "HELP":
                        return Help.command(message);
                    case "HUNT":
                        return Hunt.command(message);
                    case "AI":
                        return OpenAi.command(message);


                }

            } else {
                for (String trigger : ResponseManager.accessMap.get("basic").getTrigger()) {
                    if (content.contains(trigger)) {
                        return message.getChannel().flatMap(channel -> channel.createMessage(basicResponseManager.getMessage(trigger)));
                    }

                }
            }


            return Mono.empty();
        }).then();
        return action;
    }
}
