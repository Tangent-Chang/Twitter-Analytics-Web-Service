
package twitter;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The Class Tweet.
 */
@Generated("org.jsonschema2pojo")
public class Tweet {

    /** The created at. */
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    
    /** The id. */
    @SerializedName("id")
    @Expose
    private Long id;
    
    /** The id str. */
    @SerializedName("id_str")
    @Expose
    private String idStr;
    
    /** The text. */
    @SerializedName("text")
    @Expose
    private String text;
    
    /** The source. */
    @SerializedName("source")
    @Expose
    private String source;
    
    /** The truncated. */
    @SerializedName("truncated")
    @Expose
    private Boolean truncated;
    
    /** The in reply to status id. */
    @SerializedName("in_reply_to_status_id")
    @Expose
    private Long inReplyToStatusId;
    
    /** The in reply to status id str. */
    @SerializedName("in_reply_to_status_id_str")
    @Expose
    private Object inReplyToStatusIdStr;
    
    /** The in reply to user id. */
    @SerializedName("in_reply_to_user_id")
    @Expose
    private Object inReplyToUserId;
    
    /** The in reply to user id str. */
    @SerializedName("in_reply_to_user_id_str")
    @Expose
    private Object inReplyToUserIdStr;
    
    /** The in reply to screen name. */
    @SerializedName("in_reply_to_screen_name")
    @Expose
    private Object inReplyToScreenName;
    
    /** The user. */
    @SerializedName("user")
    @Expose
    private User user;
    
    /** The geo. */
    @SerializedName("geo")
    @Expose
    private Object geo;
    
    /** The coordinates. */
    @SerializedName("coordinates")
    @Expose
    private Object coordinates;
    
    /** The place. */
    @SerializedName("place")
    @Expose
    private Object place;
    
    /** The possibly sensitive. */
    @SerializedName("possibly_sensitive")
    @Expose
    private Boolean possiblySensitive;
    
    /** The quoted status id. */
    @SerializedName("quoted_status_id")
    @Expose
    private Long quotedStatusId;
    
    /** The quoted status id str. */
    @SerializedName("quoted_status_id_str")
    @Expose
    private Long quotedStatusIdStr;
    
    /** The quoted status. */
    @SerializedName("quoted_status")
    @Expose
    private Tweet quotedStatus;
    
    /** The contributors. */
    @SerializedName("contributors")
    @Expose
    private Object contributors;
    
    /** The retweeted status. */
    @SerializedName("retweeted_status")
    @Expose
    private Tweet retweetedStatus;
    
    /** The retweet count. */
    @SerializedName("retweet_count")
    @Expose
    private Integer retweetCount;
    
    /** The favorite count. */
    @SerializedName("favorite_count")
    @Expose
    private Integer favoriteCount;
    
    /** The entities. */
    @SerializedName("entities")
    @Expose
    private Entities entities;
    
    /** The favorited. */
    @SerializedName("favorited")
    @Expose
    private Boolean favorited;
    
    /** The retweeted. */
    @SerializedName("retweeted")
    @Expose
    private Boolean retweeted;
    
    /** The lang. */
    @SerializedName("lang")
    @Expose
    private String lang;

    /**
     * Gets the created at.
     *
     * @return     The createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the created at.
     *
     * @param createdAt     The created_at
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Gets the id.
     *
     * @return     The id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id     The id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the id str.
     *
     * @return     The idStr
     */
    public String getIdStr() {
        return idStr;
    }

    /**
     * Sets the id str.
     *
     * @param idStr     The id_str
     */
    public void setIdStr(String idStr) {
        this.idStr = idStr;
    }

    /**
     * Gets the text.
     *
     * @return     The text
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the text.
     *
     * @param text     The text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Gets the source.
     *
     * @return     The source
     */
    public String getSource() {
        return source;
    }

    /**
     * Sets the source.
     *
     * @param source     The source
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * Gets the truncated.
     *
     * @return     The truncated
     */
    public Boolean getTruncated() {
        return truncated;
    }

    /**
     * Sets the truncated.
     *
     * @param truncated     The truncated
     */
    public void setTruncated(Boolean truncated) {
        this.truncated = truncated;
    }

    /**
     * Gets the in reply to status id.
     *
     * @return     The inReplyToStatusId
     */
    public Long getInReplyToStatusId() {
        return inReplyToStatusId;
    }

    /**
     * Sets the in reply to status id.
     *
     * @param inReplyToStatusId     The in_reply_to_status_id
     */
    public void setInReplyToStatusId(Long inReplyToStatusId) {
        this.inReplyToStatusId = inReplyToStatusId;
    }

    /**
     * Gets the in reply to status id str.
     *
     * @return     The inReplyToStatusIdStr
     */
    public Object getInReplyToStatusIdStr() {
        return inReplyToStatusIdStr;
    }

    /**
     * Sets the in reply to status id str.
     *
     * @param inReplyToStatusIdStr     The in_reply_to_status_id_str
     */
    public void setInReplyToStatusIdStr(Object inReplyToStatusIdStr) {
        this.inReplyToStatusIdStr = inReplyToStatusIdStr;
    }

    /**
     * Gets the in reply to user id.
     *
     * @return     The inReplyToUserId
     */
    public Object getInReplyToUserId() {
        return inReplyToUserId;
    }

    /**
     * Sets the in reply to user id.
     *
     * @param inReplyToUserId     The in_reply_to_user_id
     */
    public void setInReplyToUserId(Object inReplyToUserId) {
        this.inReplyToUserId = inReplyToUserId;
    }

    /**
     * Gets the in reply to user id str.
     *
     * @return     The inReplyToUserIdStr
     */
    public Object getInReplyToUserIdStr() {
        return inReplyToUserIdStr;
    }

    /**
     * Sets the in reply to user id str.
     *
     * @param inReplyToUserIdStr     The in_reply_to_user_id_str
     */
    public void setInReplyToUserIdStr(Object inReplyToUserIdStr) {
        this.inReplyToUserIdStr = inReplyToUserIdStr;
    }

    /**
     * Gets the in reply to screen name.
     *
     * @return     The inReplyToScreenName
     */
    public Object getInReplyToScreenName() {
        return inReplyToScreenName;
    }

    /**
     * Sets the in reply to screen name.
     *
     * @param inReplyToScreenName     The in_reply_to_screen_name
     */
    public void setInReplyToScreenName(Object inReplyToScreenName) {
        this.inReplyToScreenName = inReplyToScreenName;
    }

    /**
     * Gets the user.
     *
     * @return     The user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user.
     *
     * @param user     The user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets the geo.
     *
     * @return     The geo
     */
    public Object getGeo() {
        return geo;
    }

    /**
     * Sets the geo.
     *
     * @param geo     The geo
     */
    public void setGeo(Object geo) {
        this.geo = geo;
    }

    /**
     * Gets the coordinates.
     *
     * @return     The coordinates
     */
    public Object getCoordinates() {
        return coordinates;
    }

    /**
     * Sets the coordinates.
     *
     * @param coordinates     The coordinates
     */
    public void setCoordinates(Object coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * Gets the place.
     *
     * @return     The place
     */
    public Object getPlace() {
        return place;
    }

    /**
     * Sets the place.
     *
     * @param place     The place
     */
    public void setPlace(Object place) {
        this.place = place;
    }

    /**
     * Gets the contributors.
     *
     * @return     The contributors
     */
    public Object getContributors() {
        return contributors;
    }

    /**
     * Sets the contributors.
     *
     * @param contributors     The contributors
     */
    public void setContributors(Object contributors) {
        this.contributors = contributors;
    }

    /**
     * Gets the retweeted status.
     *
     * @return     The retweetedStatus
     */
    public Tweet getRetweetedStatus() {
        return retweetedStatus;
    }

    /**
     * Sets the retweeted status.
     *
     * @param retweetedStatus     The retweeted_status
     */
    public void setRetweetedStatus(Tweet retweetedStatus) {
        this.retweetedStatus = retweetedStatus;
    }

    /**
     * Gets the retweet count.
     *
     * @return     The retweetCount
     */
    public Integer getRetweetCount() {
        return retweetCount;
    }

    /**
     * Sets the retweet count.
     *
     * @param retweetCount     The retweet_count
     */
    public void setRetweetCount(Integer retweetCount) {
        this.retweetCount = retweetCount;
    }

    /**
     * Gets the favorite count.
     *
     * @return     The favoriteCount
     */
    public Integer getFavoriteCount() {
        return favoriteCount;
    }

    /**
     * Sets the favorite count.
     *
     * @param favoriteCount     The favorite_count
     */
    public void setFavoriteCount(Integer favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    /**
     * Gets the entities.
     *
     * @return     The entities
     */
    public Entities getEntities() {
        return entities;
    }

    /**
     * Sets the entities.
     *
     * @param entities     The entities
     */
    public void setEntities(Entities entities) {
        this.entities = entities;
    }

    /**
     * Gets the favorited.
     *
     * @return     The favorited
     */
    public Boolean getFavorited() {
        return favorited;
    }

    /**
     * Sets the favorited.
     *
     * @param favorited     The favorited
     */
    public void setFavorited(Boolean favorited) {
        this.favorited = favorited;
    }

    /**
     * Gets the retweeted.
     *
     * @return     The retweeted
     */
    public Boolean getRetweeted() {
        return retweeted;
    }

    /**
     * Sets the retweeted.
     *
     * @param retweeted     The retweeted
     */
    public void setRetweeted(Boolean retweeted) {
        this.retweeted = retweeted;
    }

    /**
     * Gets the lang.
     *
     * @return     The lang
     */
    public String getLang() {
        return lang;
    }

    /**
     * Sets the lang.
     *
     * @param lang     The lang
     */
    public void setLang(String lang) {
        this.lang = lang;
    }

}
