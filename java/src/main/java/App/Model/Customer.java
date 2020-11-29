package App.Model;

/**
 * Customer class to save data like Ipv4
 */
public class Customer {

    /**
     * Host is the ipv4 of the user
     */
    private String host;

    /**
     * Customer
     * @param ipv4
     * ipv4 of the user given by the remoteAddr in the AboutClass
     */
    public Customer(String ipv4) {
        super();
        this.host = ipv4;
    }

    /**
     * Get Host
     * @return
     * the IPV4 of the user
     */
    public String getHost() {
        return host;
    }

    /**
     * Set Host
     * @param host
     * Set a new ipv4
     */
    public void setHost(String host) {
        this.host = host;
    }
}
