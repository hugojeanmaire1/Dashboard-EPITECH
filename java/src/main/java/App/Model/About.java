package App.Model;

public class About {

    private Customer customer = null;
    private Server server = null;

    /**
     * About
     * @param remoteAddr
     */

    public About(String remoteAddr) {
        super();
        this.customer = new Customer(remoteAddr);
        this.server = new Server();
    }

    /**
     * Get Customer
     * @return
     */

    public Customer getCustomer() {
        return customer;
    }

    /**
     * Set Customer
     * @param customer
     */

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Get Server
     * @return
     */

    public Server getServer() {
        return server;
    }

    /**
     * Set Server
     * @param server
     */

    public void setServer(Server server) {
        this.server = server;
    }

}
