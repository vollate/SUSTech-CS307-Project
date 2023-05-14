package com.TheEnd.www.db;

public enum QueryType {
    Login("Login"), Query("Query"), Check("Check");
    private final String name;

    QueryType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
