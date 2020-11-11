package App.controller;
import App.Twitter.TwitterClient;
import App.Twitter.TwitterCredentials;
import org.springframework.stereotype.Service;

@Service
public class DAO {

    private static DAO instance = new DAO();
    private static TwitterClient twitterClient = null;

    private DAO()
    {
        System.out.println("Construction du Singleton au premier appel");
    }

    public static DAO getInstance()
    {
        if (instance == null)
            instance = new DAO();
        return instance;
    }

    public static TwitterClient getTwitterClientInstance() {
        if (twitterClient == null)
            twitterClient = new TwitterClient(TwitterCredentials.builder()
                    .accessToken("892450431816871937-BQexzSGrVxlgVWahoSzlL2l5Oq6VlFO")
                    .accessTokenSecret("xCBYfI03dYCF2LwowByAakhW1aqxPuLeoWghPIrptLtuZ")
                    .apiKey("S2O303TWOCfZHx3e8Jt16AgCr")
                    .apiSecretKey("VEquyDohQ2YaXueBqmL0akbAJR16v0GxxTXpxRGDCjuJ9F0QRk")
                    .build());
        return twitterClient;
    }
}
