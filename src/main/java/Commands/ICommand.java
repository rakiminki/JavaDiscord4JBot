package Commands;

import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

public interface ICommand {
    public Mono Command(Message event);

}
