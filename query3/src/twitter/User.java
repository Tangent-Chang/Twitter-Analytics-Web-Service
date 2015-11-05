
package twitter;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The Class User.
 */
@Generated("org.jsonschema2pojo")
public class User {

    /** The id. */
    @SerializedName("id")
    @Expose
    private Long id;
    
    /** The id str. */
    @SerializedName("id_str")
    @Expose
    private String idStr;
    
    /** The name. */
    @SerializedName("name")
    @Expose
    private String name;
    
    /** The screen name. */
    @SerializedName("screen_name")
    @Expose
    private String screenName;
    
    /** The location. */
    @SerializedName("location")
    @Expose
    private String location;
    
    /** The url. */
    @SerializedName("url")
    @Expose
    private Object url;
    
    /** The description. */
    @SerializedName("description")
    @Expose
    private String description;
    
    /** The _protected. */
    @SerializedName("protected")
    @Expose
    private Boolean _protected;
    
    /** The followers count. */
    @SerializedName("followers_count")
    @Expose
    private Integer followersCount;
    
    /** The friends count. */
    @SerializedName("friends_count")
    @Expose
    private Integer friendsCount;
    
    /** The listed count. */
    @SerializedName("listed_count")
    @Expose
    private Integer listedCount;
    
    /** The created at. */
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    
    /** The favourites count. */
    @SerializedName("favourites_count")
    @Expose
    private Integer favouritesCount;
    
    /** The utc offset. */
    @SerializedName("utc_offset")
    @Expose
    private Integer utcOffset;
    
    /** The time zone. */
    @SerializedName("time_zone")
    @Expose
    private String timeZone;
    
    /** The geo enabled. */
    @SerializedName("geo_enabled")
    @Expose
    private Boolean geoEnabled;
    
    /** The verified. */
    @SerializedName("verified")
    @Expose
    private Boolean verified;
    
    /** The statuses count. */
    @SerializedName("statuses_count")
    @Expose
    private Integer statusesCount;
    
    /** The lang. */
    @SerializedName("lang")
    @Expose
    private String lang;
    
    /** The contributors enabled. */
    @SerializedName("contributors_enabled")
    @Expose
    private Boolean contributorsEnabled;
    
    /** The is translator. */
    @SerializedName("is_translator")
    @Expose
    private Boolean isTranslator;
    
    /** The is translation enabled. */
    @SerializedName("is_translation_enabled")
    @Expose
    private Boolean isTranslationEnabled;
    
    /** The profile background color. */
    @SerializedName("profile_background_color")
    @Expose
    private String profileBackgroundColor;
    
    /** The profile background image url. */
    @SerializedName("profile_background_image_url")
    @Expose
    private String profileBackgroundImageUrl;
    
    /** The profile background image url https. */
    @SerializedName("profile_background_image_url_https")
    @Expose
    private String profileBackgroundImageUrlHttps;
    
    /** The profile background tile. */
    @SerializedName("profile_background_tile")
    @Expose
    private Boolean profileBackgroundTile;
    
    /** The profile image url. */
    @SerializedName("profile_image_url")
    @Expose
    private String profileImageUrl;
    
    /** The profile image url https. */
    @SerializedName("profile_image_url_https")
    @Expose
    private String profileImageUrlHttps;
    
    /** The profile banner url. */
    @SerializedName("profile_banner_url")
    @Expose
    private String profileBannerUrl;
    
    /** The profile link color. */
    @SerializedName("profile_link_color")
    @Expose
    private String profileLinkColor;
    
    /** The profile sidebar border color. */
    @SerializedName("profile_sidebar_border_color")
    @Expose
    private String profileSidebarBorderColor;
    
    /** The profile sidebar fill color. */
    @SerializedName("profile_sidebar_fill_color")
    @Expose
    private String profileSidebarFillColor;
    
    /** The profile text color. */
    @SerializedName("profile_text_color")
    @Expose
    private String profileTextColor;
    
    /** The profile use background image. */
    @SerializedName("profile_use_background_image")
    @Expose
    private Boolean profileUseBackgroundImage;
    
    /** The default profile. */
    @SerializedName("default_profile")
    @Expose
    private Boolean defaultProfile;
    
    /** The default profile image. */
    @SerializedName("default_profile_image")
    @Expose
    private Boolean defaultProfileImage;
    
    /** The following. */
    @SerializedName("following")
    @Expose
    private Object following;
    
    /** The follow request sent. */
    @SerializedName("follow_request_sent")
    @Expose
    private Object followRequestSent;
    
    /** The notifications. */
    @SerializedName("notifications")
    @Expose
    private Object notifications;

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
     * Gets the name.
     *
     * @return     The name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the screen name.
     *
     * @return     The screenName
     */
    public String getScreenName() {
        return screenName;
    }

    /**
     * Sets the screen name.
     *
     * @param screenName     The screen_name
     */
    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    /**
     * Gets the location.
     *
     * @return     The location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location.
     *
     * @param location     The location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets the url.
     *
     * @return     The url
     */
    public Object getUrl() {
        return url;
    }

    /**
     * Sets the url.
     *
     * @param url     The url
     */
    public void setUrl(Object url) {
        this.url = url;
    }

    /**
     * Gets the description.
     *
     * @return     The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     *
     * @param description     The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the protected.
     *
     * @return     The _protected
     */
    public Boolean getProtected() {
        return _protected;
    }

    /**
     * Sets the protected.
     *
     * @param _protected     The protected
     */
    public void setProtected(Boolean _protected) {
        this._protected = _protected;
    }

    /**
     * Gets the followers count.
     *
     * @return     The followersCount
     */
    public Integer getFollowersCount() {
        return followersCount;
    }

    /**
     * Sets the followers count.
     *
     * @param followersCount     The followers_count
     */
    public void setFollowersCount(Integer followersCount) {
        this.followersCount = followersCount;
    }

    /**
     * Gets the friends count.
     *
     * @return     The friendsCount
     */
    public Integer getFriendsCount() {
        return friendsCount;
    }

    /**
     * Sets the friends count.
     *
     * @param friendsCount     The friends_count
     */
    public void setFriendsCount(Integer friendsCount) {
        this.friendsCount = friendsCount;
    }

    /**
     * Gets the listed count.
     *
     * @return     The listedCount
     */
    public Integer getListedCount() {
        return listedCount;
    }

    /**
     * Sets the listed count.
     *
     * @param listedCount     The listed_count
     */
    public void setListedCount(Integer listedCount) {
        this.listedCount = listedCount;
    }

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
     * Gets the favourites count.
     *
     * @return     The favouritesCount
     */
    public Integer getFavouritesCount() {
        return favouritesCount;
    }

    /**
     * Sets the favourites count.
     *
     * @param favouritesCount     The favourites_count
     */
    public void setFavouritesCount(Integer favouritesCount) {
        this.favouritesCount = favouritesCount;
    }

    /**
     * Gets the utc offset.
     *
     * @return     The utcOffset
     */
    public Integer getUtcOffset() {
        return utcOffset;
    }

    /**
     * Sets the utc offset.
     *
     * @param utcOffset     The utc_offset
     */
    public void setUtcOffset(Integer utcOffset) {
        this.utcOffset = utcOffset;
    }

    /**
     * Gets the time zone.
     *
     * @return     The timeZone
     */
    public String getTimeZone() {
        return timeZone;
    }

    /**
     * Sets the time zone.
     *
     * @param timeZone     The time_zone
     */
    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    /**
     * Gets the geo enabled.
     *
     * @return     The geoEnabled
     */
    public Boolean getGeoEnabled() {
        return geoEnabled;
    }

    /**
     * Sets the geo enabled.
     *
     * @param geoEnabled     The geo_enabled
     */
    public void setGeoEnabled(Boolean geoEnabled) {
        this.geoEnabled = geoEnabled;
    }

    /**
     * Gets the verified.
     *
     * @return     The verified
     */
    public Boolean getVerified() {
        return verified;
    }

    /**
     * Sets the verified.
     *
     * @param verified     The verified
     */
    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    /**
     * Gets the statuses count.
     *
     * @return     The statusesCount
     */
    public Integer getStatusesCount() {
        return statusesCount;
    }

    /**
     * Sets the statuses count.
     *
     * @param statusesCount     The statuses_count
     */
    public void setStatusesCount(Integer statusesCount) {
        this.statusesCount = statusesCount;
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

    /**
     * Gets the contributors enabled.
     *
     * @return     The contributorsEnabled
     */
    public Boolean getContributorsEnabled() {
        return contributorsEnabled;
    }

    /**
     * Sets the contributors enabled.
     *
     * @param contributorsEnabled     The contributors_enabled
     */
    public void setContributorsEnabled(Boolean contributorsEnabled) {
        this.contributorsEnabled = contributorsEnabled;
    }

    /**
     * Gets the checks if is translator.
     *
     * @return     The isTranslator
     */
    public Boolean getIsTranslator() {
        return isTranslator;
    }

    /**
     * Sets the checks if is translator.
     *
     * @param isTranslator     The is_translator
     */
    public void setIsTranslator(Boolean isTranslator) {
        this.isTranslator = isTranslator;
    }

    /**
     * Gets the checks if is translation enabled.
     *
     * @return     The isTranslationEnabled
     */
    public Boolean getIsTranslationEnabled() {
        return isTranslationEnabled;
    }

    /**
     * Sets the checks if is translation enabled.
     *
     * @param isTranslationEnabled     The is_translation_enabled
     */
    public void setIsTranslationEnabled(Boolean isTranslationEnabled) {
        this.isTranslationEnabled = isTranslationEnabled;
    }

    /**
     * Gets the profile background color.
     *
     * @return     The profileBackgroundColor
     */
    public String getProfileBackgroundColor() {
        return profileBackgroundColor;
    }

    /**
     * Sets the profile background color.
     *
     * @param profileBackgroundColor     The profile_background_color
     */
    public void setProfileBackgroundColor(String profileBackgroundColor) {
        this.profileBackgroundColor = profileBackgroundColor;
    }

    /**
     * Gets the profile background image url.
     *
     * @return     The profileBackgroundImageUrl
     */
    public String getProfileBackgroundImageUrl() {
        return profileBackgroundImageUrl;
    }

    /**
     * Sets the profile background image url.
     *
     * @param profileBackgroundImageUrl     The profile_background_image_url
     */
    public void setProfileBackgroundImageUrl(String profileBackgroundImageUrl) {
        this.profileBackgroundImageUrl = profileBackgroundImageUrl;
    }

    /**
     * Gets the profile background image url https.
     *
     * @return     The profileBackgroundImageUrlHttps
     */
    public String getProfileBackgroundImageUrlHttps() {
        return profileBackgroundImageUrlHttps;
    }

    /**
     * Sets the profile background image url https.
     *
     * @param profileBackgroundImageUrlHttps     The profile_background_image_url_https
     */
    public void setProfileBackgroundImageUrlHttps(String profileBackgroundImageUrlHttps) {
        this.profileBackgroundImageUrlHttps = profileBackgroundImageUrlHttps;
    }

    /**
     * Gets the profile background tile.
     *
     * @return     The profileBackgroundTile
     */
    public Boolean getProfileBackgroundTile() {
        return profileBackgroundTile;
    }

    /**
     * Sets the profile background tile.
     *
     * @param profileBackgroundTile     The profile_background_tile
     */
    public void setProfileBackgroundTile(Boolean profileBackgroundTile) {
        this.profileBackgroundTile = profileBackgroundTile;
    }

    /**
     * Gets the profile image url.
     *
     * @return     The profileImageUrl
     */
    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    /**
     * Sets the profile image url.
     *
     * @param profileImageUrl     The profile_image_url
     */
    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    /**
     * Gets the profile image url https.
     *
     * @return     The profileImageUrlHttps
     */
    public String getProfileImageUrlHttps() {
        return profileImageUrlHttps;
    }

    /**
     * Sets the profile image url https.
     *
     * @param profileImageUrlHttps     The profile_image_url_https
     */
    public void setProfileImageUrlHttps(String profileImageUrlHttps) {
        this.profileImageUrlHttps = profileImageUrlHttps;
    }

    /**
     * Gets the profile banner url.
     *
     * @return     The profileBannerUrl
     */
    public String getProfileBannerUrl() {
        return profileBannerUrl;
    }

    /**
     * Sets the profile banner url.
     *
     * @param profileBannerUrl     The profile_banner_url
     */
    public void setProfileBannerUrl(String profileBannerUrl) {
        this.profileBannerUrl = profileBannerUrl;
    }

    /**
     * Gets the profile link color.
     *
     * @return     The profileLinkColor
     */
    public String getProfileLinkColor() {
        return profileLinkColor;
    }

    /**
     * Sets the profile link color.
     *
     * @param profileLinkColor     The profile_link_color
     */
    public void setProfileLinkColor(String profileLinkColor) {
        this.profileLinkColor = profileLinkColor;
    }

    /**
     * Gets the profile sidebar border color.
     *
     * @return     The profileSidebarBorderColor
     */
    public String getProfileSidebarBorderColor() {
        return profileSidebarBorderColor;
    }

    /**
     * Sets the profile sidebar border color.
     *
     * @param profileSidebarBorderColor     The profile_sidebar_border_color
     */
    public void setProfileSidebarBorderColor(String profileSidebarBorderColor) {
        this.profileSidebarBorderColor = profileSidebarBorderColor;
    }

    /**
     * Gets the profile sidebar fill color.
     *
     * @return     The profileSidebarFillColor
     */
    public String getProfileSidebarFillColor() {
        return profileSidebarFillColor;
    }

    /**
     * Sets the profile sidebar fill color.
     *
     * @param profileSidebarFillColor     The profile_sidebar_fill_color
     */
    public void setProfileSidebarFillColor(String profileSidebarFillColor) {
        this.profileSidebarFillColor = profileSidebarFillColor;
    }

    /**
     * Gets the profile text color.
     *
     * @return     The profileTextColor
     */
    public String getProfileTextColor() {
        return profileTextColor;
    }

    /**
     * Sets the profile text color.
     *
     * @param profileTextColor     The profile_text_color
     */
    public void setProfileTextColor(String profileTextColor) {
        this.profileTextColor = profileTextColor;
    }

    /**
     * Gets the profile use background image.
     *
     * @return     The profileUseBackgroundImage
     */
    public Boolean getProfileUseBackgroundImage() {
        return profileUseBackgroundImage;
    }

    /**
     * Sets the profile use background image.
     *
     * @param profileUseBackgroundImage     The profile_use_background_image
     */
    public void setProfileUseBackgroundImage(Boolean profileUseBackgroundImage) {
        this.profileUseBackgroundImage = profileUseBackgroundImage;
    }

    /**
     * Gets the default profile.
     *
     * @return     The defaultProfile
     */
    public Boolean getDefaultProfile() {
        return defaultProfile;
    }

    /**
     * Sets the default profile.
     *
     * @param defaultProfile     The default_profile
     */
    public void setDefaultProfile(Boolean defaultProfile) {
        this.defaultProfile = defaultProfile;
    }

    /**
     * Gets the default profile image.
     *
     * @return     The defaultProfileImage
     */
    public Boolean getDefaultProfileImage() {
        return defaultProfileImage;
    }

    /**
     * Sets the default profile image.
     *
     * @param defaultProfileImage     The default_profile_image
     */
    public void setDefaultProfileImage(Boolean defaultProfileImage) {
        this.defaultProfileImage = defaultProfileImage;
    }

    /**
     * Gets the following.
     *
     * @return     The following
     */
    public Object getFollowing() {
        return following;
    }

    /**
     * Sets the following.
     *
     * @param following     The following
     */
    public void setFollowing(Object following) {
        this.following = following;
    }

    /**
     * Gets the follow request sent.
     *
     * @return     The followRequestSent
     */
    public Object getFollowRequestSent() {
        return followRequestSent;
    }

    /**
     * Sets the follow request sent.
     *
     * @param followRequestSent     The follow_request_sent
     */
    public void setFollowRequestSent(Object followRequestSent) {
        this.followRequestSent = followRequestSent;
    }

    /**
     * Gets the notifications.
     *
     * @return     The notifications
     */
    public Object getNotifications() {
        return notifications;
    }

    /**
     * Sets the notifications.
     *
     * @param notifications     The notifications
     */
    public void setNotifications(Object notifications) {
        this.notifications = notifications;
    }

}
