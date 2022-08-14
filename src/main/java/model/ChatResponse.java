package model;

import java.io.Serializable;
import java.util.Objects;

public class ChatResponse implements Serializable {
    private String trigger;
    private String message;

    public ChatResponse(String trigger, String message) {
        setTrigger(trigger);
        setMessage(message);
    }

    public String getTrigger() {
        return trigger;
    }

    public void setTrigger(String trigger) {
        this.trigger = trigger;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatResponse that = (ChatResponse) o;
        return getTrigger().equals(that.getTrigger()) && getMessage().equals(that.getMessage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTrigger(), getMessage());
    }

    @Override
    public String toString() {
        return "ChatResponse{" +
                "trigger='" + trigger + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
