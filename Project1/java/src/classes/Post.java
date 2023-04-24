package classes;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class Post {
    @JSONField(name = "Post ID")
    private int postID;
    private String title;
    private List<String> category;
    private String content;
    @JSONField(name = "Posting Time")
    private String postingTime;
    @JSONField(name = "Posting City")
    private String postingCity;
    private String Author;
    @JSONField(name = "Author Registration Time")
    private String authorRegistrationTime;
    @JSONField(name = "Author's ID")
    private String authorID;
    @JSONField(name = "Author's Phone")
    private String authoPhone;
    @JSONField(name = "Authors Followed By")
    private List<String> authorFollowedBy;
    @JSONField(name = "Authors Who Favorited the Post")
    private List<String> authorFavorite;
    @JSONField(name = "Authors Who Shared the Post")
    private List<String> authorShared;
    @JSONField(name = "Authors Who Liked the Post")
    private List<String> authorLiked;

    @Override
    public String toString() {
        return "Post{" +
                "postID=" + postID +
                ", title='" + title + '\'' +
                ", category=" + category +
                ", content='" + content + '\'' +
                ", postingTime='" + postingTime + '\'' +
                ", postingCity='" + postingCity + '\'' +
                ", Author='" + Author + '\'' +
                ", authorRegistrationTime='" + authorRegistrationTime + '\'' +
                ", authorID='" + authorID + '\'' +
                ", authoPhone='" + authoPhone + '\'' +
                ", authorFollowedBy=" + authorFollowedBy +
                ", authorFavorite=" + authorFavorite +
                ", authorShared=" + authorShared +
                ", authorLiked=" + authorLiked +
                '}';
    }

    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPostingTime() {
        return postingTime;
    }

    public void setPostingTime(String postingTime) {
        this.postingTime = postingTime;
    }

    public String getPostingCity() {
        return postingCity;
    }

    public void setPostingCity(String postingCity) {
        this.postingCity = postingCity;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getAuthorRegistrationTime() {
        return authorRegistrationTime;
    }

    public void setAuthorRegistrationTime(String authorRegistrationTime) {
        this.authorRegistrationTime = authorRegistrationTime;
    }

    public String getAuthorID() {
        return authorID;
    }

    public void setAuthorID(String authorID) {
        this.authorID = authorID;
    }

    public String getAuthoPhone() {
        return authoPhone;
    }

    public void setAuthoPhone(String authoPhone) {
        this.authoPhone = authoPhone;
    }

    public List<String> getAuthorFollowedBy() {
        return authorFollowedBy;
    }

    public void setAuthorFollowedBy(List<String> authorFollowedBy) {
        this.authorFollowedBy = authorFollowedBy;
    }

    public List<String> getAuthorFavorite() {
        return authorFavorite;
    }

    public void setAuthorFavorite(List<String> authorFavorite) {
        this.authorFavorite = authorFavorite;
    }

    public List<String> getAuthorShared() {
        return authorShared;
    }

    public void setAuthorShared(List<String> authorShared) {
        this.authorShared = authorShared;
    }

    public List<String> getAuthorLiked() {
        return authorLiked;
    }

    public void setAuthorLiked(List<String> authorLiked) {
        this.authorLiked = authorLiked;
    }
}
