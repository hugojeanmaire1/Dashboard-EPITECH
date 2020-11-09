package App.Model;

import java.util.ArrayList;

public class Services {

    private String name;
    private ArrayList<Widgets> widgets;

    public Services() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Widgets> getWidgets() {
        return widgets;
    }

    public void setWidgets(ArrayList<Widgets> widgets) {
        this.widgets = widgets;
    }

    @Override
    public String toString() {
        return "Services{" +
                "name='" + name + '\'' +
                ", widgets=" + widgets +
                '}';
    }
}
