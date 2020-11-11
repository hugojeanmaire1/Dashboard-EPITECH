package App.Model;

import com.google.firebase.auth.FirebaseAuth;

public class Auth {

    private FirebaseAuth mAuth;

    public void onCreate() {
        mAuth = FirebaseAuth.getInstance();
    };

    public void SignIn(String email, String password) {
        return FirebaseAuth.getInstance().sign
    }


}
