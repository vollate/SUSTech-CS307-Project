package com.TheEnd.www.db.requestTypes;

public enum ShowOpType {

    ShowLikePosts("ShowLikePosts"),
    ShowFavPosts("ShowFavPosts"),
    ShowSharePosts("ShowSharePosts"),
    ShowFollowers("ShowFollowers"),
    ShowFollowees("ShowShowFollowees"),
    ShowUserPost("ShowUserPost"),
    ShowUserReplyPost("ShowUserReplyPost"),
    ShowUserInfo("ShowUserInfo");

    private final String name;

    ShowOpType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
