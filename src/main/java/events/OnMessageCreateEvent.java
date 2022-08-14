package events;

import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

public class OnMessageCreateEvent {
    public static Mono call(GatewayDiscordClient gateway) {
        Mono commandPing = gateway.on(MessageCreateEvent.class, event -> {
            Message message = event.getMessage();
            if (message.getContent().equalsIgnoreCase("!ping")) {
                return commandPing(message);
            }

            return Mono.empty();
        }).then();
        return commandPing;
    }

    private static Mono commandPing(Message event) {
        return event.getChannel().flatMap(channel -> channel.createMessage("pong!"));
    }
}
