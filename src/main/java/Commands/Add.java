package Commands;

import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.command.ApplicationCommandInteractionOption;
import discord4j.core.object.command.ApplicationCommandInteractionOptionValue;
import discord4j.core.object.command.ApplicationCommandOption;
import discord4j.core.object.entity.Message;
import discord4j.discordjson.json.ApplicationCommandOptionData;
import discord4j.discordjson.json.ApplicationCommandRequest;
import enums.Locations;
import manager.ResponseManager;
import model.ChatResponse;
import reactor.core.publisher.Mono;

public class Add {
    private static ResponseManager basicResponseManager = ResponseManager.accessMap.get("basic");

    public static Mono command(Message event) {
        String content = event.getContent();
        String trigger = content.split(" ")[2];
        String message = content.substring(content.indexOf(content.split(" ")[3]), content.length()).strip();
        addAndSave(trigger, message);
        return event.getChannel().flatMap(channel -> channel.createMessage(responseString(trigger, message)));
    }


    public static void slashCommand(GatewayDiscordClient gatewayClient) {
        String commandName = "add";
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
                .addOption(ApplicationCommandOptionData.builder()
                        .name("response")
                        .description("The Response")
                        .type(ApplicationCommandOption.Type.STRING.getValue())
                        .required(true)
                        .build()
                ).build();

        // Define the Logic and Response of the Command
        gatewayClient.on(ChatInputInteractionEvent.class, event -> {
            if (event.getCommandName().equals(commandName)) {
                String trigger = CommandHelper.getStringFromParameter("trigger", event);
                String response = CommandHelper.getStringFromParameter("response", event);
                addAndSave(trigger, response);
                String userName = event.getInteraction().getMember().orElseThrow().getUsername();
                return event
                        .reply(trigger + " ---> " + response)
                        .withEphemeral(false);
            }
            return null;
        }).subscribe();

        // Publish it to Guild/Global
        gatewayClient.getRestClient().getApplicationService()
                .createGlobalApplicationCommand(applicationId,pingRequest)
                //.createGuildApplicationCommand(applicationId, 821048022011609109l, pingRequest)
                .subscribe();

    }

    private static void addAndSave(String trigger, String response) {
        basicResponseManager.add(new ChatResponse(trigger, response));
        basicResponseManager.save(Locations.CHATRESPONSE.path);
    }

    private static String responseString(String trigger, String response) {
        return "Trigger " + trigger + " erfolgreich erstellt!";
    }
}
