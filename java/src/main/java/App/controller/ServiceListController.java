package App.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

@RestController
@EnableAutoConfiguration
@RequestMapping("/services")
public class ServiceListController {

    @RequestMapping("/")
    public String getHome() {
        return "Services";
    }

    @RequestMapping("/list")
    public String getList() {
        return "Services list";
    }

}
