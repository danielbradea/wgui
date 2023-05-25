package com.bid90.wgui.models;

public enum UserRole {
    ADMIN("ADMIN_ROLE"),
    USER("USER_ROLE");

    private final String value;

    UserRole(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}







