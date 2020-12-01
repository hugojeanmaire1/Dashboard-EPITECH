package App.controller;

import App.Model.Services;
import App.Model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import com.wrapper.spotify.model_objects.specification.*;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import com.wrapper.spotify.requests.data.search.simplified.SearchAlbumsRequest;
import com.wrapper.spotify.requests.data.search.simplified.SearchArtistsRequest;
import com.wrapper.spotify.requests.data.search.simplified.SearchPlaylistsRequest;
import io.netty.util.concurrent.PromiseAggregator;
import org.apache.hc.core5.http.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.SpotifyHttpManager;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.ExecutionException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Spotify call
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/services/spotify")
public class SpotifyController {

    /**
     * Static variables
     */
    @JsonIgnore
    private static final Firestore db = FirestoreClient.getFirestore();
    private static final URI redirectUri = SpotifyHttpManager.makeUri(System.getenv("SPOTIFY_CALLBACK_URL"));
    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(System.getenv("SPOTIFY_CLIENTID"))
            .setClientSecret(System.getenv("SPOTIFY_CLIENT_SECRET"))
            .setRedirectUri(redirectUri)
            .build();

    /**
     * login callback
     * @param body infos to have
     * @param code id
     * @return user infos
     * @throws ExecutionException
     * If the connection is interrupted
     * @throws InterruptedException
     * If the connection is interrupted
     */
    @PostMapping(path = "/login/callback")
    public User CallbackLogin(@RequestBody User body,
                              @RequestParam(value = "code") String code) throws ExecutionException, InterruptedException {
        spotifyApi.authorizationCodeUri().show_dialog(true).build();

        DocumentReference docRef = db.collection("users").document(body.getUid());
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        if (document.exists()) {
            User user = document.toObject(User.class);
            for (Services service : user.getServices()) {
                if (service.getName().equals("spotify")) {
                    try {
                        AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(code).build();
                        AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();
                        spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
                        spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());
                        service.setAccessToken(authorizationCodeCredentials.getAccessToken());
                        service.setRequestToken(authorizationCodeCredentials.getRefreshToken());
                        user.createService(user.getUid(), service.getAccessToken(), service.getRequestToken(), "spotify");
                    } catch (ParseException | IOException | SpotifyWebApiException e) {
                        e.printStackTrace();
                    }
                }
            }
            return user;
        }
        return null;
    }

    /**
     * Login to spotify
     * @param response redirect link
     * @param uid user id
     * @throws ExecutionException
     * If the connection is interrupted
     * @throws InterruptedException
     * If the connection is interrupted
     * @throws IOException
     * If the connection is interrupted
     */
    @GetMapping(path = "/login")
    public void loginSpotify(HttpServletResponse response, @RequestParam(value = "uid") String uid) throws ExecutionException, InterruptedException, IOException {
        AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodeUri()
                .show_dialog(true)
                .build();
        User user = new User();
        user.createService(uid, null, null, "spotify");
        response.sendRedirect(String.valueOf(authorizationCodeUriRequest.execute()));
    }

    /**
     * Search for an artist
     * @param artist name
     * @return list of artists
     * @throws ExecutionException
     * If the connection is interrupted
     * @throws IOException
     * If the connection is interrupted
     */
    @GetMapping(path = "search/artist")
    public Artist[] getArtist(@RequestParam(value = "artist") String artist) throws ExecutionException, IOException {
        try {
            final SearchArtistsRequest searchArtistsRequest = spotifyApi.searchArtists(artist)
                    .build();
            final Paging<Artist> artistPaging = searchArtistsRequest.execute();
            return (artistPaging.getItems());
        } catch (ParseException | SpotifyWebApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Search for a given albums
     * @param albums name
     * @return list of datas
     * @throws IOException
     * If the connection is interrupted
     */
    @GetMapping(path="search/albums")
    public AlbumSimplified[] getAlbums(@RequestParam(value = "albums") String albums) throws IOException {
        try {
            final SearchAlbumsRequest searchAlbumsRequest  = spotifyApi.searchAlbums(albums)
                    .build();
            final Paging<AlbumSimplified> albumSimplifiedPaging = searchAlbumsRequest.execute();
            return (albumSimplifiedPaging.getItems());
        } catch (ParseException | SpotifyWebApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Search for pa playlist
     * @param playlist name
     * @return list of data
     * @throws IOException
     * If the connection is interrupted
     */
    @GetMapping(path="search/playlist")
    public PlaylistSimplified[] getPlaylist(@RequestParam(value = "playlist") String playlist) throws IOException {
        try {
            final SearchPlaylistsRequest searchPlaylistsRequest = spotifyApi.searchPlaylists(playlist)
                    .build();
            final Paging<PlaylistSimplified> playlistSimplifiedPaging = searchPlaylistsRequest.execute();
            return (playlistSimplifiedPaging.getItems());
        } catch (ParseException | SpotifyWebApiException e) {
            e.printStackTrace();
            return null;
        }
    }
}
