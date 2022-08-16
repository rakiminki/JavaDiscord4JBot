package Commands;

import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.command.ApplicationCommandOption;
import discord4j.core.object.entity.Message;
import discord4j.discordjson.json.ApplicationCommandOptionData;
import discord4j.discordjson.json.ApplicationCommandRequest;
import enums.Locations;
import manager.ResponseManager;
import model.ChatResponse;
import reactor.core.publisher.Mono;

public class Remove {
    private static ResponseManager basicResponseManager = ResponseManager.accessMap.get("basic");

    public static Mono command(Message event) {
        String content = event.getContent();
        String trigger = content.split(" ")[2];
        removeAndSave(trigger);

        return event.getChannel().flatMap(channel -> channel.createMessage(responseString(trigger)));
    }


    public static void slashCommand(GatewayDiscordClient gatewayClient) {
        String commandName = "remove";
        long applicationId = gatewayClient.getRestClient().getApplicationId().block();

        // Define the Command and its Parameters
        ApplicationCommandRequest pingRequest = ApplicationCommandRequest.builder()
                .name(commandName)
                .description("Add a Reaction")
                .addOption(ApplicationCommandOptionData.builder()
                        .name("trigger")
                        .description("The Trigger")
                        .type(ApplicationCommandOption.Type.STRING.getValue())
                        .required(true)
                        .build()
                )
               .build();

        // Define the Logic and Response of the Command
        gatewayClient.on(ChatInputInteractionEvent.class, event -> {
            if (event.getCommandName().equals(commandName)) {
                String trigger = CommandHelper.getStringFromParameter("trigger", event);
                removeAndSave(trigger);
                return event
                        .reply(responseString(trigger))
                        .withEphemeral(false);
            }
            return null;
        }).subscribe();

        // Publish it to Guild/Global
        gatewayClient.getRestClient().getApplicationService()
                //  .createGlobalApplicationCommand(applicationId,pingRequest)
                .createGuildApplicationCommand(applicationId, 821048022011609109l, pingRequest)
                .subscribe();

    }

    private static void removeAndSave(String trigger) {
        basicResponseManager.remove(trigger);
        basicResponseManager.save(Locations.CHATRESPONSE.path);
    }

    private static String responseString(String trigger) {
        return "Trigger " + trigger + " erfolgreich entfernt!";
    }
}
