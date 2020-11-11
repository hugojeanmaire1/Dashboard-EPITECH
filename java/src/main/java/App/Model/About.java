package App.Model;

public class About {

    private Customer customer = null;
    private Server server = null;

    public About(String remoteAddr) {
        super();
        this.customer = new Customer(remoteAddr);
        this.server = new Server();
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

}
