package com.TheEnd.www.db.requesttypes;

public
enum PostOpType {
  GetPost("GetPost"), GetReply("GetReply"), GetSecondReply("GetSecondReply"),
  AddPost("AddPost"), AddReply("AddReply"), AddSecondReply("AddSecondReply"),
  DeletePost("DeletePost"), DeleteReply("DeleteReply"),
  DeleteSecondaryReply("DeleteSecondaryReply"), GetCategories("GetCategories");

  private final String name;

  PostOpType(String name){this.name = name;}

public String getName() { return name; }
}
