import App.Model.ServicesAbout;
import App.Model.WidgetsAbout;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.util.ArrayList;
import static org.junit.Assert.*;


public class ServicesTest {

    ServicesAbout services;

    @BeforeEach
    public void BeforeTest()
    {
        this.services = new ServicesAbout("Facebook");
    }

    @Test
    public void testServices()
    {
        System.err.println("STARTING SERVICES TEST MODEL");
        assertEquals(this.services.getName(), "Facebook");
        assertEquals(this.services.getWidgets().size(), 0);
        this.services.setName("Github");
        ArrayList<WidgetsAbout> widgetsAbouts = new ArrayList<>();
        widgetsAbouts.add(new WidgetsAbout("widgets", "widgets1"));
        this.services.setWidgets(widgetsAbouts);
        assertEquals(this.services.getName(), "Github");
        assertEquals(this.services.getWidgets().size(), 1);
        assertEquals(this.services.getWidgets().get(0).getName(), "widgets");
        this.services = new ServicesAbout();
        assertNull(this.services.getName());
        assertEquals(this.services.getWidgets().size(), 0);
    }

}
