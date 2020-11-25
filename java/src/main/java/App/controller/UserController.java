package App.controller;

import App.Model.Services;
import App.Model.User;

import App.Model.Widgets;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/users")
public class UserController {

    @PostMapping(value = "/create-user")
    public void PostNewUser(@RequestBody User body, HttpServletRequest request) throws ExecutionException, InterruptedException {
        request.getSession().setAttribute("Create", body);
        body.createNewUser();
    }

    @PostMapping(value = "/check-user")
    public User checkUser(@RequestBody User body, HttpServletRequest request) throws ExecutionException, InterruptedException {
        request.getSession().setAttribute("Login", body);

        return body.userLogIn();
    }


    @PostMapping(value = "/add-widget")
    public User postWidget(@RequestBody Widgets body,
                            @RequestParam(value = "uid")String uid,
                            @RequestParam(value = "serviceName")String serviceName) throws ExecutionException, InterruptedException {
        return new User().addWidget(uid, serviceName, body);
    }

    @PostMapping(value = "/update-widget")
    public User updateWidget(@RequestBody Widgets body,
                            @RequestParam(value = "uid")String uid) throws ExecutionException, InterruptedException {
        return new User().updateWidget(uid, body);
    }

    @PostMapping(value = "/remove-widget")
    public User postWidgets(@RequestBody Widgets body,
                            @RequestParam(value = "uid")String uid) throws ExecutionException, InterruptedException {
        return new User().removeWidget(uid, body);
    }

    @GetMapping(value = "/get-widgets")
    public ArrayList<Services> getWidgets(@RequestParam(value = "uid")String uid) throws ExecutionException, InterruptedException {
        return new User().getWidgetsList(uid);
    }
}
