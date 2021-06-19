package pp.facerecognizer;

import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import pp.facerecognizer.R;
import pp.facerecognizer.Shared_Preference.UserSessionManager;


public class Splash_Screen extends AppCompatActivity {
    private final int SPLASH_TIMEOUT = 2000;
    private UserSessionManager userSessionManager;

    @Override
    protected void onStart() {

        new Handler ( ).postDelayed (new Runnable ( ) {
            @Override
            public void run() {

                LoginStatus ( );
                // startActivity (new Intent (Splash_Activity.this, Login_Activity.class));
                //   finish ( );
            }
        }, SPLASH_TIMEOUT);
        super.onStart ( );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_splash__screen);
        userSessionManager = new UserSessionManager (this);

    }

    private void LoginStatus() {
        if (( userSessionManager.isUserLoggedIn ( ) ) == true) {
            Intent intent = new Intent (Splash_Screen.this, MainActivity.class);

            startActivity (intent);

        } else {
            Intent intent = new Intent (Splash_Screen.this, Login_Activity.class);
            startActivity (intent);

        }
    }
}