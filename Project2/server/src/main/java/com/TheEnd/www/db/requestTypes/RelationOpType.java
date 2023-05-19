package com.TheEnd.www.db.requestTypes;

public enum RelationOpType {
    Like("Like"),
    Fav("Fav"),
    Share("Share"),
    Follow("Follow"),
    DeleteFollow("DeleteFollow");

    private final String name;

    RelationOpType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
