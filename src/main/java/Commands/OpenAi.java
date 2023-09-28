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

public class OpenAi {
    private static ResponseManager basicResponseManager = ResponseManager.accessMap.get("basic");

    public static Mono command(Message event) {
        String content = event.getContent();
        String prompt = content.substring(content.indexOf(content.split(" ")[2]), content.length()).strip();

        return event.getChannel().flatMap(channel -> channel.createMessage(responseString(prompt)));
    }


    public static void slashCommand(GatewayDiscordClient gatewayClient) {
        String commandName = "ai";
        long applicationId = gatewayClient.getRestClient().getApplicationId().block();

        // Define the Command and its Parameters
        ApplicationCommandRequest pingRequest = ApplicationCommandRequest.builder()
                .name(commandName)
                .description("Let the Ai solve your Problems!")
                .addOption(ApplicationCommandOptionData.builder()
                        .name("prompt")
                        .description("The Question or Task")
                        .type(ApplicationCommandOption.Type.STRING.getValue())
                        .required(true)
                        .build()

                ).build();

        // Define the Logic and Response of the Command
        gatewayClient.on(ChatInputInteractionEvent.class, event -> {
            if (event.getCommandName().equals(commandName)) {
                String prompt = CommandHelper.getStringFromParameter("prompt", event);

                return event
                        .deferReply().then(methodThatTakesALongTime(event,prompt));
            }
            return null;
        }).subscribe();

        // Publish it to Guild/Global
        gatewayClient.getRestClient().getApplicationService()
                  .createGlobalApplicationCommand(applicationId,pingRequest)
              //  .createGuildApplicationCommand(applicationId, 821048022011609109l, pingRequest)
                .subscribe();

    }

    private static Mono<Message> methodThatTakesALongTime(ChatInputInteractionEvent event,String prompt) {
        // Do logic that takes awhile, then return

        return event.createFollowup(responseString(prompt));
    }

    private static String responseString(String prompt) {
        return AiTest.answer(prompt,"1");
    }
}
