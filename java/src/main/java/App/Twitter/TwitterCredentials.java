package App.Twitter;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TwitterCredentials {

    private String apiKey;
    private String apiSecretKey;
    private String accessToken;
    private String accessTokenSecret;
}
