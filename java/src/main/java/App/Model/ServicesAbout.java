package App.Model;

import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;

@Getter
@Setter
public class ServicesAbout {

    private String name;
    private ArrayList<WidgetsAbout> widgets;

    /**
     * Services About
     */

    public ServicesAbout() {
        this.name = "null";
        this.widgets = new ArrayList<WidgetsAbout>();
    }

    /**
     * Services About
     * @param name
     */

    public ServicesAbout(String name) {
        this.name = name;
        this.widgets = new ArrayList<WidgetsAbout>();
    }

}
