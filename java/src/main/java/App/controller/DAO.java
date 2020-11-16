package App.controller;
import org.springframework.stereotype.Service;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

@Service
public class DAO {

    private static DAO instance = new DAO();
    private static Twitter twitter = null;

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

    public static Twitter getTwitter()
    {
        if (twitter == null) {
            String consumerKey = "S2O303TWOCfZHx3e8Jt16AgCr";
            String consumerSecret = "VEquyDohQ2YaXueBqmL0akbAJR16v0GxxTXpxRGDCjuJ9F0QRk";

            ConfigurationBuilder builder = new ConfigurationBuilder();
            builder.setOAuthConsumerKey(consumerKey);
            builder.setOAuthConsumerSecret(consumerSecret);
            Configuration configuration = builder.build();

            TwitterFactory factory = new TwitterFactory(configuration);
            twitter = factory.getInstance();
        }
        return twitter;
    }
}
