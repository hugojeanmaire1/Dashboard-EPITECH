package App.Model;

import java.util.ArrayList;

public class Services {

    private String name;
    private ArrayList<Widgets> widgets;

    public Services() {
        this.name = "null";
        this.widgets = new ArrayList<Widgets>();
    }

    public Services(String name) {
        this.name = name;
        this.widgets = new ArrayList<Widgets>();
        this.widgets.add(new Widgets("city_temperature", "Display temperature for a city"));
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
                '}';
    }
}
