import App.Model.About;
import App.Model.Customer;
import App.Model.Server;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

public class AboutTest {

    About about;

    @BeforeEach
    public void BeforeTest()
    {
        this.about = new About("127.0.0.1");
    }

    @Test
    public void TestAbout()
    {
        System.err.println("STARTING ABOUT TEST MODEL");
        assertNotNull(this.about.getCustomer());
        assertEquals(this.about.getCustomer().getHost(), "127.0.0.1");
        assertNotNull(this.about.getServer());
        this.about.setCustomer(new Customer("127.0.0.2"));
        this.about.setServer(new Server());
        assertEquals(this.about.getCustomer().getHost(), "127.0.0.2");
        assertNotNull(this.about.getServer());
    }
}
