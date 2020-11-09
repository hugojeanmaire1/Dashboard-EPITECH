package App.Model;

public class Customer {

    private String host;

    public Customer(String ipv4) {
        super();
        this.host = ipv4;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "host='" + host + '\'' +
                '}';
    }
}
