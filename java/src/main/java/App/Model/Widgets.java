package App.Model;

import java.util.HashMap;

public class Widgets {

    private String name;
    private String description;
    private HashMap<String, String> params;

    public Widgets() {

    }

    public Widgets(String name, String description) {
        this.name = name;
        this.description = description;
        this.params = new HashMap<String, String>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public HashMap<String, String> getParams() {
        return params;
    }

    public void setParams(HashMap<String, String> params) {
        this.params = params;
    }

}
