package App.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString

/**
 * Class CredentialsTwitch
 */

public class CredentialsTwitch {

    public String access_token;
    public int expires_in;
    public String refresh_token;
    public String token_type;
}
