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

/**
 * User call to firestore database
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/users")
public class UserController {

    /**
     * Create a user in a db
     * @param body infos to send to the db
     * @param request for session
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @PostMapping(value = "/create-user")
    public void PostNewUser(@RequestBody User body, HttpServletRequest request) throws ExecutionException, InterruptedException {
        request.getSession().setAttribute("Create", body);
        body.createNewUser();
    }

    /**
     * check if a user already in the db
     * @param body user infos
     * @param request request
     * @return user infos
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @PostMapping(value = "/check-user")
    public User checkUser(@RequestBody User body, HttpServletRequest request) throws ExecutionException, InterruptedException {
        request.getSession().setAttribute("Login", body);

        return body.userLogIn();
    }

    /**
     * Stock the widet in the widgets list of the user in db
     * @param body widget infos
     * @param uid user id
     * @param serviceName name of service
     * @return modified user infos
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @PostMapping(value = "/add-widget")
    public User postWidget(@RequestBody Widgets body,
                            @RequestParam(value = "uid")String uid,
                            @RequestParam(value = "serviceName")String serviceName) throws ExecutionException, InterruptedException {
        return new User().addWidget(uid, serviceName, body);
    }

    /**
     * Update the widget position
     * @param body widget infos
     * @param uid user id
     * @return modified user infos
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @PostMapping(value = "/update-widget")
    public User updateWidget(@RequestBody Widgets body,
                            @RequestParam(value = "uid")String uid) throws ExecutionException, InterruptedException {
        return new User().updateWidget(uid, body);
    }

    /**
     * delete widget in the user widgets list
     * @param body widget infos
     * @param uid user id
     * @return user modified infos
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @PostMapping(value = "/remove-widget")
    public User postWidgets(@RequestBody Widgets body,
                            @RequestParam(value = "uid")String uid) throws ExecutionException, InterruptedException {
        return new User().removeWidget(uid, body);
    }

    /**
     * List all the wdgets of a user
     * @param uid user id
     * @return list of widgets
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @GetMapping(value = "/get-widgets")
    public ArrayList<Services> getWidgets(@RequestParam(value = "uid")String uid) throws ExecutionException, InterruptedException {
        return new User().getWidgetsList(uid);
    }
}
