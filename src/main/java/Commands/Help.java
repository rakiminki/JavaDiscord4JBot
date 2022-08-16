package Commands;

import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.entity.Message;
import discord4j.discordjson.json.ApplicationCommandRequest;
import reactor.core.publisher.Mono;

public class Help {

    public static Mono command(Message event) {
        return event.getChannel().flatMap(channel -> channel.createMessage(help()));

    }

    public static void slashCommand(GatewayDiscordClient gatewayClient) {
        long applicationId = gatewayClient.getRestClient().getApplicationId().block();

        ApplicationCommandRequest pingRequest = ApplicationCommandRequest.builder()
                .name("help")
                .description("Need help with any commands?")
                .build();

        gatewayClient.on(ChatInputInteractionEvent.class, event -> {
            if (event.getCommandName().equals("help")) {
                return event
                        .reply(help())
                        .withEphemeral(true);
            }
            return null;
        }).subscribe();

        gatewayClient.getRestClient().getApplicationService()
                //  .createGlobalApplicationCommand(applicationId,pingRequest)
                .createGuildApplicationCommand(applicationId, 821048022011609109l, pingRequest)
                .subscribe();
    }
    private static String help(){
        return "Für eine komplette Dokumentation aller Befehle gehe auf folgende Seite: https://github.com/rakiminki/TollerBotv2";
    }
}
