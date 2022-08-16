package Commands;

import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.entity.Message;
import discord4j.discordjson.json.ApplicationCommandRequest;
import reactor.core.publisher.Mono;

public class Ping {

    public static Mono command(Message event) {
        return event.getChannel().flatMap(channel -> channel.createMessage("Pong!"));
    }

    public static void slashCommand(GatewayDiscordClient gatewayClient) {
        long applicationId = gatewayClient.getRestClient().getApplicationId().block();

        ApplicationCommandRequest pingRequest = ApplicationCommandRequest.builder()
                .name("ping")
                .description("Wanna play a Round?")
                .build();

        gatewayClient.on(ChatInputInteractionEvent.class, event -> {
            if (event.getCommandName().equals("ping")) {
                return event
                        .reply("Pong!")
                        .withEphemeral(true);
            }
            return null;
        }).subscribe();

        gatewayClient.getRestClient().getApplicationService()
                //  .createGlobalApplicationCommand(applicationId,pingRequest)
                .createGuildApplicationCommand(applicationId, 821048022011609109l, pingRequest)
                .subscribe();

    }
}
