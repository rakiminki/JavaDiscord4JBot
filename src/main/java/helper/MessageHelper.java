package helper;


import discord4j.core.object.entity.Message;

import java.util.Arrays;
import java.util.List;

public class MessageHelper {

    public MessageHelper(Message message) {
        this.message = message;
        List<String> parts = Arrays.stream(message.getContent().split(" ")).toList();
        prefix = parts.remove(0);
        command = parts.remove(0);
        this.content = message.getContent().replaceFirst($ {
        }
        prefix, "").replaceFirst(command, "");
    }

    public String prefix;
    public Message message;
    public String content;
    public String command;


    String trigger = content.split(" ")[2];
    String message = content.substring(content.indexOf(content.split(" ")[3]), content.length()).strip();
}
