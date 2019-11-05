package ua.training.model.entities;

public enum UserRole {
    ADMIN("ADMIN"), USER("USER"), GUEST("GUEST");

    private String name;

    UserRole(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
