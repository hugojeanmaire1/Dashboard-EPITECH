package App.controller;

import App.Model.Services;
import App.Model.User;

import App.Model.Widgets;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
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
     * @param response to send data
     * @throws ExecutionException
     * if the connection is interrupted
     * @throws InterruptedException
     * if the connection is interrupted
     */
    @PostMapping(value = "/create-user")
    public void PostNewUser(@RequestBody User body, HttpServletResponse response) throws ExecutionException, InterruptedException {
        body.createNewUser();
        response.setStatus(200);
    }

    /**
     * check if a user already in the db
     * @param body user infos
     * @param response response to send
     * @return user infos
     * @throws ExecutionException
     * if the connection is interrupted
     * @throws InterruptedException
     * if the connection is interrupted
     */
    @PostMapping(value = "/check-user")
    public User checkUser(@RequestBody User body, HttpServletResponse response) throws ExecutionException, InterruptedException {
        response.setStatus(200);
        return body.userLogIn();
    }

    /**
     * Stock the widget in the widgets list of the user in db
     * @param body widget infos
     * @param uid user id
     * @param serviceName name of service
     * @return modified user infos
     * @throws ExecutionException
     * if the connection is interrupted
     * @throws InterruptedException
     * if the connection is interrupted
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
     * if the connection is interrupted
     * @throws InterruptedException
     * if the connection is interrupted
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
     * if the connection is interrupted
     * @throws InterruptedException
     * if the connection is interrupted
     */
    @PostMapping(value = "/remove-widget")
    public User postWidgets(@RequestBody Widgets body,
                            @RequestParam(value = "uid")String uid) throws ExecutionException, InterruptedException {
        return new User().removeWidget(uid, body);
    }

    /**
     * List all the widget of a user
     * @param uid user id
     * @return list of widgets
     * @throws ExecutionException
     * if the connection is interrupted
     * @throws InterruptedException
     * if the connection is interrupted
     */
    @GetMapping(value = "/get-widgets")
    public ArrayList<Services> getWidgets(@RequestParam(value = "uid")String uid) throws ExecutionException, InterruptedException {
        return new User().getWidgetsList(uid);
    }
}
