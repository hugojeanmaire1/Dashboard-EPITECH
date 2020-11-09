package App.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class TestController {

    @RequestMapping("/welcome")
    public String WelcomePage() {
        return "Welcome to Yawin Tutor";
    }

    @RequestMapping("/database")
    public String getDatabase() {
        return "Get Databse";
    }

}
