
package twitter;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The Class UserMention.
 */
@Generated("org.jsonschema2pojo")
public class UserMention {

    /** The screen name. */
    @SerializedName("screen_name")
    @Expose
    private String screenName;
    
    /** The name. */
    @SerializedName("name")
    @Expose
    private String name;
    
    /** The id. */
    @SerializedName("id")
    @Expose
    private Long id;
    
    /** The id str. */
    @SerializedName("id_str")
    @Expose
    private String idStr;
    
    /** The indices. */
    @SerializedName("indices")
    @Expose
    private List<Integer> indices = new ArrayList<Integer>();

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
     * Gets the indices.
     *
     * @return     The indices
     */
    public List<Integer> getIndices() {
        return indices;
    }

    /**
     * Sets the indices.
     *
     * @param indices     The indices
     */
    public void setIndices(List<Integer> indices) {
        this.indices = indices;
    }

}
