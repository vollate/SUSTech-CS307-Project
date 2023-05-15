package com.TheEnd.www.db.requestTypes;

public enum PostOpType {
    GetPost("GetPost"), GetReply("GetReply"), GetSecondReply("GetSecondReply"), AddPost("AddPost"),
    AddReply("AddReply"), AddSecondReply("AddSecondReply"), DeletePost("DeletePost"),
    DeleteReply("DeleteReply"), DeleteSecondaryReply("DeleteSecondaryReply");

    private final String name;

    PostOpType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
