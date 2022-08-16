package Commands;

import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.entity.Message;
import discord4j.discordjson.json.ApplicationCommandRequest;
import manager.ResponseManager;
import model.ChatResponse;
import reactor.core.publisher.Mono;

import java.util.Map;

public class ListPlus {
    private static ResponseManager basicResponseManager = ResponseManager.accessMap.get("basic");

    public static Mono command(Message event) {
        return event.getChannel().flatMap(channel -> channel.createMessage(list()));
    }

    public static void slashCommand(GatewayDiscordClient gatewayClient) {
        long applicationId = gatewayClient.getRestClient().getApplicationId().block();

        ApplicationCommandRequest pingRequest = ApplicationCommandRequest.builder()
                .name("liste")
                .description("Lists all Triggers with Response")
                .build();

        gatewayClient.on(ChatInputInteractionEvent.class, event -> {
            if (event.getCommandName().equals("liste")) {
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
        StringBuilder message = new StringBuilder("Liste aller Trigger mit Antwort: \n");
        for (Map.Entry<String, ChatResponse> entry : basicResponseManager.getTriggersWithResponse()) {
            message.append("- " + entry.getKey() + " --> " + entry.getValue().getMessage() + "\n");
        }
        return message.toString();
    }
}
