
package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("comments")
    @Expose
    private Comments comments;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("created_time")
    @Expose
    private String createdTime;
    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("can_delete_comments")
    @Expose
    private Boolean canDeleteComments;
    @SerializedName("alt_media_url")
    @Expose
    private Object altMediaUrl;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("can_view_comments")
    @Expose
    private Boolean canViewComments;
    @SerializedName("caption")
    @Expose
    private Caption caption;
    @SerializedName("user_has_liked")
    @Expose
    private Boolean userHasLiked;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("images")
    @Expose
    private Images images;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("likes")
    @Expose
    private Likes likes;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Comments getComments() {
        return comments;
    }

    public void setComments(Comments comments) {
        this.comments = comments;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Boolean getCanDeleteComments() {
        return canDeleteComments;
    }

    public void setCanDeleteComments(Boolean canDeleteComments) {
        this.canDeleteComments = canDeleteComments;
    }

    public Object getAltMediaUrl() {
        return altMediaUrl;
    }

    public void setAltMediaUrl(Object altMediaUrl) {
        this.altMediaUrl = altMediaUrl;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getCanViewComments() {
        return canViewComments;
    }

    public void setCanViewComments(Boolean canViewComments) {
        this.canViewComments = canViewComments;
    }

    public Caption getCaption() {
        return caption;
    }

    public void setCaption(Caption caption) {
        this.caption = caption;
    }

    public Boolean getUserHasLiked() {
        return userHasLiked;
    }

    public void setUserHasLiked(Boolean userHasLiked) {
        this.userHasLiked = userHasLiked;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Likes getLikes() {
        return likes;
    }

    public void setLikes(Likes likes) {
        this.likes = likes;
    }

}
