package App.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;

public class WidgetsAbout {

    private String name;
    private String description;
    private ArrayList<HashMap<String, String>> params;

    public WidgetsAbout() {
        this.name = null;
        this.description = null;
        this.params = new ArrayList<>();
    }

    public WidgetsAbout(String name, String description) {
        this.name = name;
        this.description = description;
        this.params = new ArrayList<>();
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

    public ArrayList<HashMap<String, String>> getParams() {
        return params;
    }

    public void setParams(ArrayList<HashMap<String, String>> params) {
        this.params = params;
    }
}
