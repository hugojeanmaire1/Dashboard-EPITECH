package App.Model;

/**
 * About class to setup about.json
 */
public class About {

    private Customer customer = null;
    private Server server = null;

    /**
     * About
     * @param remoteAddr
     * remoteAddr of the user who's making request
     */
    public About(String remoteAddr) {
        super();
        this.customer = new Customer(remoteAddr);
        this.server = new Server();
    }

    /**
     * Get Customer
     * @return
     * a new Customer with data
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Set Customer
     * @param customer
     * set a new customer
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Get Server
     * @return
     * a Server with all database data
     */
    public Server getServer() {
        return server;
    }

    /**
     * Set Server
     * @param server
     * set a new server
     */
    public void setServer(Server server) {
        this.server = server;
    }

}
