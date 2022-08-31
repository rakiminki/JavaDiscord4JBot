import Commands.*;
import enums.Locations;
import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;

import enums.Secret;
import events.OnMessageCreateEvent;
import events.OnReady;
import manager.ResponseManager;
import reactor.core.publisher.Mono;

public class Main {
    public static void main(String[] args) {
        new ResponseManager("basic", Locations.CHATRESPONSE.path);
        DiscordClient offlineClient = DiscordClient.create(Secret.DISCORD_TOKEN.value);
        Mono<Void> client = registerEvents(offlineClient);
        client.block();
    }

    public static Mono<Void> registerEvents(DiscordClient offlineClient) {
        Mono client = offlineClient.withGateway((GatewayDiscordClient gatewayClient) -> {
            // onReady
            Mono onReady = OnReady.call(gatewayClient);

            // onMessageCreate
            Mono onMessageCreate = OnMessageCreateEvent.call(gatewayClient);

            // Slash Commands
            registerSlashCommands(gatewayClient);

            return onReady.and(onMessageCreate);
        });
        return client;
    }

    private static void registerSlashCommands(GatewayDiscordClient gatewayClient) {
        Ping.slashCommand(gatewayClient);
        Add.slashCommand(gatewayClient);
        Remove.slashCommand(gatewayClient);
        List.slashCommand(gatewayClient);
        ListPlus.slashCommand(gatewayClient);
        Help.slashCommand(gatewayClient);
        Hunt.slashCommand(gatewayClient);
        OpenAi.slashCommand(gatewayClient);

    }


}
