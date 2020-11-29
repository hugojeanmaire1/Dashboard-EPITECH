import App.Model.WidgetsAbout;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class WidgetsTest {

    WidgetsAbout widgets;

    @BeforeEach
    public void BeforeTest() {
        this.widgets = new WidgetsAbout();
    }

    @Test
    public void TestWidgets()
    {
        System.err.println("STARTING WIDGETS TEST MODEL");
        assertNull(this.widgets.getName());
        assertNull(this.widgets.getDescription());
        assertEquals(this.widgets.getParams().size(), 0);
        ArrayList<HashMap<String, String>> params = new ArrayList<>();
        HashMap<String, String> map = new HashMap<>();
        map.put("data", "ok");
        map.put("data2", "ok2");
        params.add(map);
        this.widgets.setName("github");
        this.widgets.setDescription("github widgets");
        this.widgets.setParams(params);
        assertEquals(this.widgets.getName(), "github");
        assertEquals(this.widgets.getDescription(), "github widgets");
        assertEquals(this.widgets.getParams().size(), 1);
        this.widgets = new WidgetsAbout("githubb", "github widgetss");
        assertEquals(this.widgets.getName(), "githubb");
        assertEquals(this.widgets.getDescription(), "github widgetss");
        assertEquals(this.widgets.getParams().size(), 0);
    }
}
