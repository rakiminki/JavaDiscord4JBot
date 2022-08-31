package model.huntbot;

import java.util.Objects;

public class Collectible {
    private long id;
    private String name;
    private int amount;

    public Collectible(long id, String name, int amount) {
        this.id = id;
        this.name = name;
        this.amount = amount;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Collectible that = (Collectible) o;
        return id == that.id && amount == that.amount && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, amount);
    }

    @Override
    public String toString() {
        return "Collectible{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                '}';
    }
}
