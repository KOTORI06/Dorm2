package com.repairsystem.constant;

public enum UserRoleEnum {
    STUDENT("学生"),
    ADMIN("管理员");

    private final String description;

    UserRoleEnum(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
}
