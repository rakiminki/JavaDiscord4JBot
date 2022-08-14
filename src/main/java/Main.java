import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;

import events.OnMessageCreateEvent;
import events.OnReady;
import reactor.core.publisher.Mono;

public class Main {
    public static void main(String[] args) {
        DiscordClient offlineClient = DiscordClient.create(Secret.DISCORD_TOKEN.value);
        Mono<Void> client = registerActionHandlers(offlineClient);
        client.block();
    }

    public static Mono<Void> registerActionHandlers(DiscordClient offlineClient) {
        Mono client = offlineClient.withGateway((GatewayDiscordClient gateway) -> {
            // onReady
            Mono onReady = OnReady.call(gateway);

            // onMessage
            Mono commandPing = OnMessageCreateEvent.call(gateway);

            return onReady.and(commandPing);
        });
        return client;
    }



}
