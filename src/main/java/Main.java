import enums.Locations;
import enums.Secret;
import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;

import events.OnMessageCreateEvent;
import events.OnReady;
import manager.ResponseManager;
import reactor.core.publisher.Mono;

public class Main {
    public static void main(String[] args) {
        new ResponseManager("basic", Locations.CHATRESPONSE.path);
        DiscordClient offlineClient = DiscordClient.create(Secret.DISCORD_TOKEN.value);
        Mono<Void> client = registerActionHandlers(offlineClient);
        client.block();
    }

    public static Mono<Void> registerActionHandlers(DiscordClient offlineClient) {
        Mono client = offlineClient.withGateway((GatewayDiscordClient gateway) -> {
            // onReady
            Mono onReady = OnReady.call(gateway);

            // onMessageCreate
            Mono onMessageCreate = OnMessageCreateEvent.call(gateway);

            return onReady.and(onMessageCreate);
        });
        return client;
    }



}
