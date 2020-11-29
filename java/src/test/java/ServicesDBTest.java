import App.Model.Services;
import App.Model.ServicesAbout;
import App.Model.WidgetsAbout;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.util.ArrayList;
import static org.junit.Assert.*;


public class ServicesDBTest {

    @Test
    public void TestServicesDB()
    {
        Services services = new Services();
        assertNull(services.getName());
        assertNull(services.getRequestToken());
        assertNull(services.getRequestTokenSecret());
        assertNull(services.getUserName());
        assertNull(services.getAccessToken());
        assertNull(services.getUserId());
        assertEquals(services.getWidgets().size(), 0);
        services = new Services("github");
        assertEquals(services.getName(), "github");
        assertNull(services.getRequestToken());
        assertNull(services.getRequestTokenSecret());
        assertNull(services.getUserName());
        assertNull(services.getAccessToken());
        assertNull(services.getUserId());
        assertEquals(services.getWidgets().size(), 0);
        services.setName("twitch");
        services.setRequestToken("abcde");
        services.setRequestTokenSecret("abcdefg");
        services.setAccessToken("toimontoi");
        services.setUserName("data");
        services.setUserId("zgeuz");
        services.setWidgets(null);
        assertEquals(services.getName(), "twitch");
        assertEquals(services.getRequestToken(), "abcde");
        assertEquals(services.getRequestTokenSecret(), "abcdefg");
        assertEquals(services.getAccessToken(), "toimontoi");
        assertEquals(services.getUserName(), "data");
        assertEquals(services.getUserId(), "zgeuz");
        assertNull(services.getWidgets());
        assertEquals(services.toString(), "Services{name='twitch', RequestToken='abcde', RequestTokenSecret='abcdefg', userName='data', AccessToken='toimontoi', userId='zgeuz', widgets=null}");
    }

}
