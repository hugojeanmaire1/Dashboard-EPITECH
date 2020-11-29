package App.Model;

import java.util.ArrayList;

/**
 * Servcies class to handle services Model
 */
public class Services {

    /**
     * Name of the service
     */
    private String name;

    /**
     * Request token associated for this service
     */
    private String RequestToken;

    /**
     * Request token secret associated for this service
     */
    private String RequestTokenSecret;

    /**
     * username of the user
     */
    private String userName;

    /**
     * Access token for this one
     */
    private String AccessToken;

    /**
     * user ID of the user
     */
    private String userId;

    /**
     * List of all widgets associated by
     */
    private ArrayList<Widgets> widgets;

    /**
     * Services
     */
    public Services() {
        this.name = null;
        this.RequestToken = null;
        this.RequestTokenSecret = null;
        this.userName = null;
        this.AccessToken = null;
        this.userId = null;
        this.widgets = new ArrayList<Widgets>();
    }

    /**
     * Services
     * @param name
     * name of the services
     */
    public Services(String name) {
        this.name = name;
        this.RequestToken = null;
        this.RequestTokenSecret = null;
        this.userName = null;
        this.AccessToken = null;
        this.userId = null;
        this.widgets = new ArrayList<Widgets>();
    }

    /**
     * Get Name
     * @return
     * the name of the services
     */
    public String getName() {
        return name;
    }

    /**
     * Set Name
     * @param name
     * set a new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get Widget
     * @return
     * the list of the widgets
     */
    public ArrayList<Widgets> getWidgets() {
        return widgets;
    }

    /**
     * Set new widgets for this services
     * @param widgets
     * new widgets to set
     */
    public void setWidgets(ArrayList<Widgets> widgets) {
        this.widgets = widgets;
    }

    /**
     * Get RequestToken
     * @return
     * the request token
     */
    public String getRequestToken() {
        return RequestToken;
    }

    /**
     * Get Access Token
     * @return
     * the access token
     */

    public String getAccessToken() {
        return AccessToken;
    }

    /**
     * Get Request Token Secret
     * @return
     * the request token secret
     */
    public String getRequestTokenSecret() { return RequestTokenSecret; }

    /**
     * Set Request Token Secret
     * @param requestTokenSecret
     * set a new request token secret
     */
    public void setRequestTokenSecret(String requestTokenSecret) { RequestTokenSecret = requestTokenSecret; }

    /**
     * Set Request Token
     * @param requestToken
     * set a new request token
     */
    public void setRequestToken(String requestToken) { RequestToken = requestToken; }

    /**
     * Set Access Token
     * @param accessToken
     * set a new access Token
     */
    public void setAccessToken(String accessToken) { AccessToken = accessToken; }

    /**
     * Get User Name
     * @return
     * the user name
     */
    public String getUserName() { return userName; }

    /**
     * Set User Name
     * @param userName
     * set a new username
     */
    public void setUserName(String userName) { this.userName = userName; }

    /**
     * Get User ID
     * @return
     * the user id
     */
    public String getUserId() { return userId; }

    /**
     * Set User ID
     * @param userId
     * set a new user id
     */
    public void setUserId(String userId) { this.userId = userId; }

    /**
     * To String Function
     * @return
     * a string with all the data for this class
     */
    @Override
    public String toString() {
        return "Services{" +
                "name='" + name + '\'' +
                ", RequestToken='" + RequestToken + '\'' +
                ", RequestTokenSecret='" + RequestTokenSecret + '\'' +
                ", userName='" + userName + '\'' +
                ", AccessToken='" + AccessToken + '\'' +
                ", userId='" + userId + '\'' +
                ", widgets=" + widgets +
                '}';
    }

}
