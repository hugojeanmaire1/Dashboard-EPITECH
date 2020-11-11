package App.controller;

import App.Model.Services;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

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
