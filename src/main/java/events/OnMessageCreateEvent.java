package events;

import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import enums.Config;
import manager.ResponseManager;
import model.ChatResponse;
import reactor.core.publisher.Mono;

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
                        return commandPing(message);
                    case "ADD":
                        return commandAdd(message);


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

    private static Mono commandPing(Message event) {
        return event.getChannel().flatMap(channel -> channel.createMessage("pong!"));
    }

    private static Mono commandAdd(Message event) {
        String content = event.getContent();
        String trigger = content.split(" ")[2];
        String message = content.substring(content.indexOf(content.split(" ")[3]), content.length()).strip();
        basicResponseManager.add(new ChatResponse(trigger, message));
        return event.getChannel().flatMap(channel -> channel.createMessage("Trigger erfolgreich erstellt!"));
    }

}
