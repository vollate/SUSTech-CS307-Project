package com.TheEnd.www.db.requesttypes;

public enum UserOpType {
    Login("Login"), CreateUser("CreateUser"), DeleteUser("DeleteUser"), ChangePassword("ChangePassword");

    private final String name;

    UserOpType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
