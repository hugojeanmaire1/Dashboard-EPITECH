package App.controller;

import App.Model.About;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

/**
 * Generate about.json file
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@EnableAutoConfiguration
public class AboutController {

    /**
     * Generate the about.json
     * @param request send the infos
     * @return
     */
    @RequestMapping(path = "/about.json")
    public About getAbout(HttpServletRequest request) {
        return new About(request.getRemoteAddr());
    }
}
