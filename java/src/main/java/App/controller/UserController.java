package App.controller;

import App.Model.User;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.ExecutionException;

@RestController
@EnableAutoConfiguration
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
}
