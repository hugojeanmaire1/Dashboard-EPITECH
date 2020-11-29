import App.Model.CredentialsTwitch;
import App.Model.Twitch;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.Assert.*;

public class TwitchTest {

    Twitch twitch;

    @BeforeEach
    public void BeforeTest() {
        this.twitch = new Twitch("abcd", "abcde", "google.com");
    }

    @Test
    public void TestTwitch() {
        System.err.println("STARTING TWITCH TEST MODEL");
        assertEquals(this.twitch.getClientID(), "abcd");
        assertEquals(this.twitch.getClientSecret(), "abcde");
        assertEquals(this.twitch.getRedirectUrl(), "google.com");
        assertNull(this.twitch.getCredentials());
        this.twitch.setClientID("abcdef");
        this.twitch.setClientSecret("abcdefgh");
        this.twitch.setRedirectUrl("hjeanmaire.fr");
        CredentialsTwitch credentials = new CredentialsTwitch();
        credentials.setAccess_token("okok");
        credentials.setRefresh_token("okoka");
        credentials.setExpires_in(3600);
        credentials.setToken_type("Bearer");
        this.twitch.setCredentials(credentials);
        assertEquals(this.twitch.getClientID(), "abcdef");
        assertEquals(this.twitch.getClientSecret(), "abcdefgh");
        assertEquals(this.twitch.getRedirectUrl(), "hjeanmaire.fr");
        assertNotNull(this.twitch.getCredentials());
        assertEquals(this.twitch.getCredentials().getAccess_token(), "okok");
        assertEquals(this.twitch.getCredentials().getRefresh_token(), "okoka");
        assertEquals(this.twitch.getCredentials().getToken_type(), "Bearer");
        assertEquals(this.twitch.getCredentials().getExpires_in(), 3600);
        assertEquals(twitch.getCredentials().toString(), "CredentialsTwitch(access_token=okok, expires_in=3600, refresh_token=okoka, token_type=Bearer)");
    }


}
