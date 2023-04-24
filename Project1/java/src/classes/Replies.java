package classes;

import com.alibaba.fastjson.annotation.JSONField;

public class Replies {
    private int reply_id;
    @JSONField(name = "Post ID")
    private int postID;
    @JSONField(name = "Reply Content")
    private String replyContent;
    @JSONField(name = "Reply Stars")
    private int replyStars;
    @JSONField(name = "Reply Author")
    private String replyAuthor;
    @JSONField(name = "Secondary Reply Content")
    private String secondaryReplyContent;
    @JSONField(name = "Secondary Reply Stars")
    private int secondaryReplyStars;
    @JSONField(name = "Secondary Reply Author")
    private String secondaryReplyAuthor;

    public Replies() {
    }

    public Replies(int postID, String replyContent, int replyStars, String replyAuthor, String secondaryReplyContent, int secondaryReplyStars, String secondaryReplyAuthor) {
        this.postID = postID;
        this.replyContent = replyContent;
        this.replyStars = replyStars;
        this.replyAuthor = replyAuthor;
        this.secondaryReplyContent = secondaryReplyContent;
        this.secondaryReplyStars = secondaryReplyStars;
        this.secondaryReplyAuthor = secondaryReplyAuthor;
    }
    public void setReply_id(int id){
        reply_id = id;
    }
    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public int getReplyStars() {
        return replyStars;
    }

    public void setReplyStars(int replyStars) {
        this.replyStars = replyStars;
    }

    public String getReplyAuthor() {
        return replyAuthor;
    }

    public void setReplyAuthor(String replyAuthor) {
        this.replyAuthor = replyAuthor;
    }

    public String getSecondaryReplyContent() {
        return secondaryReplyContent;
    }

    public void setSecondaryReplyContent(String secondaryReplyContent) {
        this.secondaryReplyContent = secondaryReplyContent;
    }

    public int getSecondaryReplyStars() {
        return secondaryReplyStars;
    }

    public void setSecondaryReplyStars(int secondaryReplyStars) {
        this.secondaryReplyStars = secondaryReplyStars;
    }

    public String getSecondaryReplyAuthor() {
        return secondaryReplyAuthor;
    }

    public void setSecondaryReplyAuthor(String secondaryReplyAuthor) {
        this.secondaryReplyAuthor = secondaryReplyAuthor;
    }

    @Override
    public String toString() {
        return "Replies{" +
                "postID=" + postID +
                ", replyContent='" + replyContent + '\'' +
                ", replyStars=" + replyStars +
                ", replyAuthor='" + replyAuthor + '\'' +
                ", secondaryReplyContent='" + secondaryReplyContent + '\'' +
                ", secondaryReplyStars=" + secondaryReplyStars +
                ", secondaryReplyAuthor='" + secondaryReplyAuthor + '\'' +
                '}';
    }
    public String toInsertReplySql(){
        String reCon = replyContent.replace("'", "''");
        return "insert into data.replies(reply_id, post_id, stars, content, author_name)\n" +
                "values (nextval('id_sequence.reply_seq')::numeric, "
                + postID+ ", "
                + replyStars+ ", '"
                + reCon+"', '"
                + replyAuthor+"')";

    }
    public String toInsertSecReplySql(){
        String secReCon = secondaryReplyContent.replace("'", "''");
        return "insert into data.secondary_replies(secondary_reply_id, reply_id, stars, content, author_name)\n" +
                "values (nextval('id_sequence.secondary_reply_seq')::numeric, "
                + reply_id + ", "
                + secondaryReplyStars + ", '"
                + secReCon + "', '"
                + secondaryReplyAuthor + "');";

    }
}
