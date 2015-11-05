package twitter;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The Class Entities.
 */
@Generated("org.jsonschema2pojo")
public class Entities {

    /** The hashtags. */
    @SerializedName("hashtags")
    @Expose
    private List<Object> hashtags = new ArrayList<Object>();
    
    /** The symbols. */
    @SerializedName("symbols")
    @Expose
    private List<Object> symbols = new ArrayList<Object>();
    
    /** The urls. */
    @SerializedName("urls")
    @Expose
    private List<Object> urls = new ArrayList<Object>();
    
    /** The user mentions. */
    @SerializedName("user_mentions")
    @Expose
    private List<UserMention> userMentions = new ArrayList<UserMention>();

    /**
     * Gets the hashtags.
     *
     * @return     The hashtags
     */
    public List<Object> getHashtags() {
        return hashtags;
    }

    /**
     * Sets the hashtags.
     *
     * @param hashtags     The hashtags
     */
    public void setHashtags(List<Object> hashtags) {
        this.hashtags = hashtags;
    }

    /**
     * Gets the symbols.
     *
     * @return     The symbols
     */
    public List<Object> getSymbols() {
        return symbols;
    }

    /**
     * Sets the symbols.
     *
     * @param symbols     The symbols
     */
    public void setSymbols(List<Object> symbols) {
        this.symbols = symbols;
    }

    /**
     * Gets the urls.
     *
     * @return     The urls
     */
    public List<Object> getUrls() {
        return urls;
    }

    /**
     * Sets the urls.
     *
     * @param urls     The urls
     */
    public void setUrls(List<Object> urls) {
        this.urls = urls;
    }

    /**
     * Gets the user mentions.
     *
     * @return     The userMentions
     */
    public List<UserMention> getUserMentions() {
        return userMentions;
    }

    /**
     * Sets the user mentions.
     *
     * @param userMentions     The user_mentions
     */
    public void setUserMentions(List<UserMention> userMentions) {
        this.userMentions = userMentions;
    }

}
