package events;

import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.object.entity.User;
import reactor.core.publisher.Mono;

public class OnReady {
    public static Mono call(GatewayDiscordClient gateway) {
        Mono<Void> onReady = gateway.on(ReadyEvent.class, event ->
                Mono.fromRunnable(() -> {
                    final User self = event.getClient().getSelf().block();
                    System.out.printf("Logged in as %s#%s%n", self.getUsername(), self.getDiscriminator());
                })).then();
        return onReady;
    }

}
