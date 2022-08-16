package Commands;

import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.entity.Message;
import discord4j.discordjson.json.ApplicationCommandRequest;
import manager.ResponseManager;
import reactor.core.publisher.Mono;

public class List {
    private static ResponseManager basicResponseManager = ResponseManager.accessMap.get("basic");

    public static Mono command(Message event) {
        return event.getChannel().flatMap(channel -> channel.createMessage(list()));
    }

    public static void slashCommand(GatewayDiscordClient gatewayClient) {
        long applicationId = gatewayClient.getRestClient().getApplicationId().block();

        ApplicationCommandRequest pingRequest = ApplicationCommandRequest.builder()
                .name("list")
                .description("Lists all Triggers")
                .build();

        gatewayClient.on(ChatInputInteractionEvent.class, event -> {
            if (event.getCommandName().equals("list")) {
                return event
                        .reply(list())
                        .withEphemeral(true);
            }
            return null;
        }).subscribe();

        gatewayClient.getRestClient().getApplicationService()
                //  .createGlobalApplicationCommand(applicationId,pingRequest)
                .createGuildApplicationCommand(applicationId, 821048022011609109l, pingRequest)
                .subscribe();
    }

    private static String list(){
        StringBuilder message = new StringBuilder("Liste aller Trigger: \n");
        for (String trigger : basicResponseManager.getTrigger()) {
            message.append("- " + trigger + "\n");
        }
        return message.toString();
    }
}
