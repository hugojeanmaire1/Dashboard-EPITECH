package App.Model;

import java.util.ArrayList;

public class Services {

    private String name;
    private String RequestToken;
    private String RequestTokenSecret;
    private String userName;
    private String AccessToken;
    private String userId;
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
     */

    public String getName() {
        return name;
    }

    /**
     * Set Name
     * @param name
     */

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get Widget
     * @return
     */

    public ArrayList<Widgets> getWidgets() {
        return widgets;
    }

    /**
     * Set The Widget
     */

    public void setWidgets(ArrayList<Widgets> widgets) {
        this.widgets = widgets;
    }

    /**
     * Get RequestToken
     * @return
     */

    public String getRequestToken() {
        return RequestToken;
    }

    /**
     * Get Access Token
     * @return
     */

    public String getAccessToken() {
        return AccessToken;
    }

    /**
     * Get Requestion Token Secret
     * @return
     */

    public String getRequestTokenSecret() { return RequestTokenSecret; }

    /**
     * Set Request Token Secret
     * @param requestTokenSecret
     */

    public void setRequestTokenSecret(String requestTokenSecret) { RequestTokenSecret = requestTokenSecret; }

    /**
     * Set Request Token
     * @param requestToken
     */

    public void setRequestToken(String requestToken) { RequestToken = requestToken; }

    /**
     * Set Access Token
     * @param accessToken
     */

    public void setAccessToken(String accessToken) { AccessToken = accessToken; }

    /**
     * Get User Name
     * @return
     */

    public String getUserName() { return userName; }

    /**
     * Set User Name
     * @param userName
     */

    public void setUserName(String userName) { this.userName = userName; }

    /**
     * Get User ID
     * @return
     */

    public String getUserId() { return userId; }

    /**
     * Set User ID
     * @param userId
     */

    public void setUserId(String userId) { this.userId = userId; }

    /**
     * To String Function
     * @return
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
