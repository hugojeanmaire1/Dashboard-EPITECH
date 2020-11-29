package App.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.w3c.dom.stylesheets.DocumentStyle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TransferQueue;

/**
 * Widgets class Model to handle it in DB
 */
public class Widgets {

    /**
     * Title of the widget
     */
    private String title;

    /**
     * Name of the widget
     */
    private String name;

    /**
     * Description of the widgets
     */
    private String description;

    /**
     * Position in the grid
     */
    private HashMap<String, Object> position;

    /**
     * Parameters of the widgets
     */
    private ArrayList<HashMap<String, String>> params;

    /**
     * Connection to DB
     */
    @JsonIgnore
    private static final Firestore db = FirestoreClient.getFirestore();

    /**
     * Widgets constructor
     */
    public Widgets() {
        this.name = null;
        this.title = null;
        this.description = null;
        this.params = null;
        this.position = null;
    }

    /**
     * Widgets constructor
     * @param name
     * name of the widgets
     * @param description
     * description of the widgets
     */
    public Widgets(String name, String description) {
        this.name = name;
        this.title = null;
        this.description = description;
        this.params = new ArrayList<>();
        this.position = new HashMap<>();
    }

    /**
     * Get Name
     * @return
     * the name of the widget
     */
    public String getName() {
        return name;
    }

    /**
     * Get Position
     * @return
     * the position of the widget
     */
    public HashMap<String, Object> getPosition() { return position; }

    /**
     * Set Position
     * @param position
     * a new position
     */
    public void setPosition(HashMap<String, Object> position) { this.position = position; }

    /**
     * Set Name
     * @param name
     * set a new name
     */
    public void setName(String name) { this.name = name; }

    /**
     * Get Description
     * @return
     * the description of the user
     */
    public String getDescription() { return description; }

    /**
     * Set Description
     * @param description
     * set a new description
     */
    public void setDescription(String description) { this.description = description; }

    /**
     * Get Params
     * @return
     * parameters of the widgets
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

    /**
     * GetT Title
     * @return
     * get the title
     */
    public String getTitle() { return title; }

    /**
     * Set Title
     * @param title
     * set a new title
     */
    public void setTitle(String title) { this.title = title; }

    /**
     * toString Function
     * @return
     * string with all data
     */
    @Override
    public String toString() {
        return "Widgets{" +
                "title='" + title + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", position=" + position +
                ", params=" + params +
                '}';
    }
}
