package enums;

public enum Locations {
    DATA("data/"),
    CHATRESPONSE("chatresponse.data");
    public String path;

    private Locations(String path) {
        this.path = path;
    }
}
