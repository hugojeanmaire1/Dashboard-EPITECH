import App.Model.Widgets;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;


public class WidgetsTest {

    private Widgets widgets;

    @Before
    public void BeforeTest() {
        this.widgets = new Widgets("Facebook", "Facebook widgets");
    }


    @Test
    public void TestWidgets() {
        assertEquals(this.widgets.getName(), "Facebook");
        assertEquals(this.widgets.getDescription(), "Facebook widgets");
        assertEquals(this.widgets.getParams().size(), 0);
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("value", "node");
        hashMap.put("name", "zzz");
        this.widgets.setParams(hashMap);
        this.widgets.setName("Google");
        this.widgets.setDescription("Google Widgets");
        assertEquals(this.widgets.getName(), "Google");
        assertEquals(this.widgets.getDescription(), "Google Widgets");
        assertEquals(this.widgets.getParams().size(), 2);
    }
}
