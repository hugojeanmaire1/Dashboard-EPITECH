package App.controller;

import com.github.scribejava.apis.LinkedInApi20;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import java.util.Random;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/services/linkedin")
public class LinkedinController {

    private static final Firestore db = FirestoreClient.getFirestore();

    @GetMapping("/login/callback")
    public void loginLinkedinCallback(@RequestParam(required = false) String code, @RequestParam(required = false) String error)
    {
        System.out.println(code);
        System.out.println(error);
    }

    @GetMapping(path = "/login")
    public RedirectView loginLinkedin(HttpServletResponse response, @RequestParam(value="uid") String uid)
    {
        OAuth20Service service = new ServiceBuilder("77kxfy40sa6aeh")
                .apiSecret("gQZWGjKkS7AIiOhP")
                .defaultScope("r_liteprofile r_emailaddress w_member_social")
                .callback("http://localhost:8080/services/linkedin/login/callback")
                .build(LinkedInApi20.instance());
        String secretState = "secret" + new Random().nextInt(999_999);
        String authorizationUrl = service.getAuthorizationUrl(secretState);
        System.out.println(authorizationUrl);
        return new RedirectView(authorizationUrl);
    }
}
