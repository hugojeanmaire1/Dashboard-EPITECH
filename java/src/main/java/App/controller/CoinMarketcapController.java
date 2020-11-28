package App.controller;

import App.Model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.view.RedirectView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * CoinMarketCap Controller to setup api call
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/services/coinmarketcap")
public class CoinMarketcapController {

    /**
     * Client to setup the http request
     */
    private final OkHttpClient client = new OkHttpClient();

    /**
     * Request Builder function for CMC
     * @param url
     * url of the API to call
     * @param parameters
     * parameters to give to url
     * @param headers
     * headers to set to the API Call
     * @return
     * a request with all of this
     */
    private Request getRequest(String url, Map<String, String> parameters, Map<String, String> headers)
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

    /**
     * Login function for CMC
     * @param uid
     * uid of the user
     * @return
     * a RedirectView to the dashboard
     */
    @GetMapping(path = "/login")
    public RedirectView loginGithub(@RequestParam(value="uid") String uid)
    {
        User user = new User();
        try {
            user.createService(uid, null, null,"coinmarketcap");
        } catch (Exception e) {
            e.printStackTrace();
            return new RedirectView("http://localhost:4200/dashboard");
        }
        return new RedirectView("http://localhost:4200/dashboard");
    }

    /**
     * Get data for one coin given
     * @param coin
     * coin parameters to give to get data
     * @param res
     * response to with all the data
     */
    @GetMapping(path = "/coins")
    public void getCoin(@RequestParam String coin, HttpServletResponse res)
    {
        Map<String, String> params = new HashMap<>();
        params.put("localization", "true");
        params.put("tickers", "true");
        params.put("market_data", "true");
        params.put("community_data", "true");
        params.put("developer_data", "true");
        params.put("sparkline", "false");
        Request r = getRequest("https://api.coingecko.com/api/v3/coins/" + coin, params, null);
        try {
            String s = Objects.requireNonNull(client.newCall(r).execute().body()).string();
            res.setContentType("application/json");
            PrintWriter out = res.getWriter();
            out.print(s);
            out.flush();
            out.close();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "The response is too bad");
        }
    }
}
