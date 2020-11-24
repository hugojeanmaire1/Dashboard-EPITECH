package App.Model;

import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;

@Getter
@Setter
public class ServicesAbout {

    private String name;
    private ArrayList<Widgets> widgets;

    public ServicesAbout() {
        this.name = "null";
        this.widgets = new ArrayList<Widgets>();
    }

    public ServicesAbout(String name) {
        this.name = name;
        this.widgets = new ArrayList<Widgets>();
    }

}
