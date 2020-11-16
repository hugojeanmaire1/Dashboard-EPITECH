import App.Model.Services;
import App.Model.Widgets;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;


public class ServicesTest {

    private Services services;

    @Before
    public void BeforeTest() {
        this.services = new Services("Facebook");
    }


    @Test
    public void TestWidgets() {
        assertEquals(this.services.getName(), "Facebook");
        assertEquals(this.services.getWidgets().size(), 0);
        ArrayList<Widgets> hashMap = new ArrayList<Widgets>();
        hashMap.add(new Widgets("Facebook", "Facebook desc"));
        this.services.setWidgets(hashMap);
        this.services.setName("Google");
        assertEquals(this.services.getName(), "Google");
        assertEquals(this.services.getWidgets().size(), 1);

    }
}
