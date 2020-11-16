import App.controller.TestController;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class AboutControllerTest {

    @Test
    public void testAboutController() {
        TestController testController = new TestController();
        String result = testController.WelcomePage();
        assertEquals(result, "Welcome to Yawin Tutor");
    }
}
