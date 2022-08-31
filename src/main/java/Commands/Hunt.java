package Commands;

import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.entity.Message;
import discord4j.discordjson.json.ApplicationCommandRequest;
import reactor.core.publisher.Mono;

public class Hunt {

    public static Mono command(Message event) {
        return event.getChannel().flatMap(channel -> channel.createMessage(hunt()));

    }

    public static void slashCommand(GatewayDiscordClient gatewayClient) {
        long applicationId = gatewayClient.getRestClient().getApplicationId().block();

        ApplicationCommandRequest pingRequest = ApplicationCommandRequest.builder()
                .name("hunt")
                .description("Wanna hunt some animals?")
                .build();

        gatewayClient.on(ChatInputInteractionEvent.class, event -> {
            if (event.getCommandName().equals("hunt")) {
                return event
                        .reply(hunt())
                        .withEphemeral(true);
            }
            return null;
        }).subscribe();

        gatewayClient.getRestClient().getApplicationService()
                //  .createGlobalApplicationCommand(applicationId,pingRequest)
                .createGuildApplicationCommand(applicationId, 821048022011609109l, pingRequest)
                .subscribe();
    }

    private static String hunt() {
        return "Jäger";
        //https://the-trivia-api.com/api/questions
    }
}
