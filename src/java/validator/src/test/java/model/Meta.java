package model;

public class Meta {
    private String author;
    private String description;
    private boolean enabled;
    private String name;

    public Meta() {

    }

    public Meta(String author, String description, boolean enabled, String name) {
        this.author = author;
        this.description = description;
        this.enabled = enabled;
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public String getName() {
        return name;
    }
}
