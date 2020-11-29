import App.Model.Customer;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

public class CustomerTest {

    @Test
    public void TestCustomer()
    {
        System.err.println("STARTING CUSTOMER TEST MODEL");
        Customer customer = new Customer("127.0.0.1");
        assertEquals(customer.getHost(), "127.0.0.1");
        customer.setHost("127.0.0.2");
        assertEquals(customer.getHost(), "127.0.0.2");
    }

}
