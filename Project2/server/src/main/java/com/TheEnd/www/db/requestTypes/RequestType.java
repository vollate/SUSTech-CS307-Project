package com.TheEnd.www.db.requestTypes;

public enum RequestType {
    UserOp("UserOp"), PostOp("PostOp"), SearchOp("SearchOp");
    private final String name;

    RequestType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
