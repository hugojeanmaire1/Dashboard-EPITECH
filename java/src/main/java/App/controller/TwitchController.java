package App.controller;


import App.Model.CredentialsTwitch;
import App.Model.Twitch;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import okhttp3.*;
import okhttp3.RequestBody;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/services/twitch")
public class TwitchController {

    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();


    @SneakyThrows
    private Request PostRequestBuilder(String url, Map<String, String> parameters, Map<String, String> headers)
    {
        RequestBody formBody = new FormBody.Builder().build();
        Headers h = new Headers.Builder().build();
        if (headers != null) {
            Headers.Builder builder = new Headers.Builder();
            for(Map.Entry<String, String> header: headers.entrySet())
                builder.add(header.getKey(), header.getValue());
            h = builder.build();
        }
        HttpUrl final_url = null;
        if (parameters != null) {
            HttpUrl.Builder builder = HttpUrl.parse(url).newBuilder();
            for(Map.Entry<String, String> param: parameters.entrySet())
                builder.addQueryParameter(param.getKey(),param.getValue());
            final_url = builder.build();
        } else
            final_url = HttpUrl.parse(url);
        return new Request.Builder()
                .headers(h)
                .url(final_url)
                .post(formBody)
                .build();
    }

    @SneakyThrows
    private Request GetRequestBuilder(String url, Map<String, String> parameters, Map<String, String> headers)
    {
        Headers h = new Headers.Builder().build();
        if (headers != null) {
            Headers.Builder builder = new Headers.Builder();
            for(Map.Entry<String, String> header: headers.entrySet())
                builder.add(header.getKey(), header.getValue());
            h = builder.build();
        }
        HttpUrl final_url = null;
        if (parameters != null) {
            HttpUrl.Builder builder = HttpUrl.parse(url).newBuilder();
            for(Map.Entry<String, String> param: parameters.entrySet())
                builder.addQueryParameter(param.getKey(),param.getValue());
            final_url = builder.build();
        } else
            final_url = HttpUrl.parse(url);

        return new Request.Builder()
                .headers(h)
                .url(final_url)
                .get()
                .build();
    }

    @SneakyThrows
    @GetMapping("/login/callback")
    public Object callbackTwitch(@RequestParam String code, HttpServletRequest request)
    {
        Twitch twitch = (Twitch)request.getSession().getAttribute("twitch");
        Map<String, String> params = new HashMap<>();
        params.put("client_id", twitch.getClientID());
        params.put("client_secret", twitch.getClientSecret());
        params.put("code", code);
        params.put("grant_type", "authorization_code");
        params.put("redirect_uri", twitch.getRedirectUrl());
        Request r = PostRequestBuilder("https://id.twitch.tv/oauth2/token", params, null);
        String s = Objects.requireNonNull(client.newCall(r).execute().body()).string();
        CredentialsTwitch credentials = objectMapper.readValue(s, CredentialsTwitch.class);
        twitch.setCredentials(credentials);
        request.getSession().setAttribute("twitch", twitch);
        return s;
    }

    @SneakyThrows
    @GetMapping(path="/login")
    public RedirectView loginTwitch(HttpServletRequest request)
    {
        Twitch twitch = new Twitch("kwhp5wznikfygc3faiad50fqep61w2", "emulvozjrzr3stv5fl5dzcvx42479r", "http://localhost:8080/services/twitch/login/callback");
        Map<String, String> params = new HashMap<>();
        params.put("client_id", twitch.getClientID());
        params.put("redirect_uri", twitch.getRedirectUrl());
        params.put("response_type", "code");
        request.getSession().setAttribute("twitch", twitch);
        Request r = GetRequestBuilder("https://id.twitch.tv/oauth2/authorize", params, null);
        return new RedirectView(r.url().toString());
    }

    @SneakyThrows
    @GetMapping(path="/trends")
    public Object getTrends(HttpServletRequest request)
    {
        Map<String, String> headers = new HashMap<>();
        headers.put("Client-ID", "kwhp5wznikfygc3faiad50fqep61w2");
        headers.put("Accept", "application/vnd.twitchtv.v5+json");

        Request r = GetRequestBuilder("https://api.twitch.tv/kraken/games/top", null, headers);
        try {
            return Objects.requireNonNull(client.newCall(r).execute().body()).string();
        } catch (IOException e) {
            return Objects.requireNonNull(new Response.Builder().build().body()).string();
        }
    }
}
