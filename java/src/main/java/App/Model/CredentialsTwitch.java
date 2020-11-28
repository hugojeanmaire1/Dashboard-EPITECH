package App.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Class CredentialsTwitch
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class CredentialsTwitch {

    /**
     * Access token given by the API
     */
    public String access_token;

    /**
     * Expiration variable given
     */
    public int expires_in;

    /**
     * Refresh token given by the API
     */
    public String refresh_token;

    /**
     * Token type given by the API (Bearer)
     */
    public String token_type;
}
