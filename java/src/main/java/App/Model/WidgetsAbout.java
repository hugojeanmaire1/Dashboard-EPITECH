package App.Model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Widgets class to handle about.json
 */
public class WidgetsAbout {

    /**
     * name of the widget
     */
    private String name;

    /**
     * Description of the widgets
     */
    private String description;

    /**
     * params of the widgets
     */
    private ArrayList<HashMap<String, String>> params;

    /**
     * WidgetsAbout constructor
     */
    public WidgetsAbout() {
        this.name = null;
        this.description = null;
        this.params = new ArrayList<>();
    }

    /**
     * WidgetsAbout constructor
     * @param name
     * name of the widget
     * @param description
     * description of this one
     */
    public WidgetsAbout(String name, String description) {
        this.name = name;
        this.description = description;
        this.params = new ArrayList<>();
    }

    /**
     * Get Name
     * @return
     * the name
     */
    public String getName() {
        return name;
    }

    /**
     * Set Name
     * @param name
     * set a new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get Description
     * @return
     * the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set Description
     * @param description
     * set a new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get Params
     * @return
     * the couple of params
     */
    public ArrayList<HashMap<String, String>> getParams() {
        return params;
    }

    /**
     * Set Params
     * @param params
     * set a new couple of params
     */
    public void setParams(ArrayList<HashMap<String, String>> params) {
        this.params = params;
    }
}
