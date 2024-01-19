package com.kyura.message.common;

public enum MenuType {
    ADMIN(0),
    USER(1);

    private final int value;

    MenuType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
