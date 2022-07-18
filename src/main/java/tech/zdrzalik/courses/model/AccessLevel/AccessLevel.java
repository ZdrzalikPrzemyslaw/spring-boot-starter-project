package tech.zdrzalik.courses.model.AccessLevel;

public enum AccessLevel {
    USER("user"), ADMIN("admin");
    private final String level;

    private AccessLevel(String level) {
        this.level = level;
    }

    public String getLevel() {
        return level;
    }
}