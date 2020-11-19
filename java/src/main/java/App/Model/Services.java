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

    public Services() {
        this.name = "null";
        this.widgets = new ArrayList<Widgets>();
    }

    public Services(String name) {
        this.name = name;
        this.widgets = new ArrayList<Widgets>();
        this.widgets.add(new Widgets("city_temperature", "Display temperature for a city"));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Widgets> getWidgets() {
        return widgets;
    }

    public void setWidgets(ArrayList<Widgets> widgets) {
        this.widgets = widgets;
    }

    public String getRequestToken() {
        return RequestToken;
    }

    public String getAccessToken() {
        return AccessToken;
    }

    public String getRequestTokenSecret() { return RequestTokenSecret; }

    public void setRequestTokenSecret(String requestTokenSecret) { RequestTokenSecret = requestTokenSecret; }

    public void setRequestToken(String requestToken) { RequestToken = requestToken; }

    public void setAccessToken(String accessToken) { AccessToken = accessToken; }

    public String getUserName() { return userName; }

    public void setUserName(String userName) { this.userName = userName; }

    public String getUserId() { return userId; }

    public void setUserId(String userId) { this.userId = userId; }

    @Override
    public String toString() {
        return "Services{" +
                "name='" + name + '\'' +
                '}';
    }
}
