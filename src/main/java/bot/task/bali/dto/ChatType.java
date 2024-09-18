package bot.task.bali.dto;

public enum ChatType {

    WHATSAPP("whatsapp"),
    INSTAGRAM("instagram");

    private final String name;

    ChatType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
