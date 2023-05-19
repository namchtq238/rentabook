package com.rentabook.constant;

public enum Role {

    ADMIN("ADMIN", 0), CUSTOMER("CUSTOMER", 1);
    private final String type;
    private final Integer value;

    Role(String type, int value) {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public Integer getValue() {
        return value;
    }
}
