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

    /**
     * WidgetsAbout
     */

    public WidgetsAbout() {
        this.name = null;
        this.description = null;
        this.params = new ArrayList<>();
    }

    /**
     * WidgetsAbout
     * @param name
     * @param description
     */

    public WidgetsAbout(String name, String description) {
        this.name = name;
        this.description = description;
        this.params = new ArrayList<>();
    }

    /**
     * Get Name
     * @return
     */

    public String getName() {
        return name;
    }

    /**
     * Set Name
     * @param name
     */

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get Description
     * @return
     */

    public String getDescription() {
        return description;
    }

    /**
     * Set Description
     * @param description
     */

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get Params
     * @return
     */

    public ArrayList<HashMap<String, String>> getParams() {
        return params;
    }

    /**
     * Set Params
     * @param params
     */

    public void setParams(ArrayList<HashMap<String, String>> params) {
        this.params = params;
    }
}
