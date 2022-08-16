package Commands;

import discord4j.core.event.domain.Event;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.command.ApplicationCommandInteractionOption;
import discord4j.core.object.command.ApplicationCommandInteractionOptionValue;

public class CommandHelper {
    public static String getStringFromParameter(String parameterName, ChatInputInteractionEvent event){
        return event.getOption(parameterName).flatMap(ApplicationCommandInteractionOption::getValue)
                .map(ApplicationCommandInteractionOptionValue::asString)
                .get();

    }
}
