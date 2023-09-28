package Commands;

import discord4j.core.object.entity.Message;
import helper.MessageHelper;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public abstract class AbstractAnswerCommand implements ICommand {
    Function<MessageHelper, String> commandAction;

    public Mono command(Message event) {
        return event.getChannel().flatMap(channel -> channel.createMessage(commandAction.apply(new MessageHelper(event))));
    }
}
