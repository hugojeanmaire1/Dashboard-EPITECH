package App.Model;

public class Customer {

    private String host;

    /**
     * Customer
     * @param ipv4
     */

    public Customer(String ipv4) {
        super();
        this.host = ipv4;
    }

    /**
     * Get Host
     * @return
     */

    public String getHost() {
        return host;
    }

    /**
     * Set Host
     * @param host
     */

    public void setHost(String host) {
        this.host = host;
    }
}
