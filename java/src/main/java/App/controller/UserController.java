package App.controller;

import App.Model.About;
import App.Model.User;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@EnableAutoConfiguration
public class UserController {

    @RequestMapping(path = "/create-user/{uid}", method = RequestMethod.POST)
    public void PostNewUser(@PathVariable(value = "uid")String uid) {
        User test = new User();

        test.createUser(uid);
    };

}
