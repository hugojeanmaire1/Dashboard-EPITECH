package App.controller;

import App.Model.About;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

@RestController
@EnableAutoConfiguration
public class AboutController {

    @RequestMapping(path = "/about.json")
    public About getAbout(HttpServletRequest request) {
        return new About(request.getRemoteAddr());
    }
}
