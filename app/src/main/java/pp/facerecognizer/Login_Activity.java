package pp.facerecognizer;



import android.app.ProgressDialog;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import pp.facerecognizer.Shared_Preference.UserSessionManager;


public class Login_Activity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mFirebaseAuth;
    private ProgressDialog progressDialog;
    private UserSessionManager userSessionManager;
    private EditText Username_ET,Password_ET;
    private Button Login_btn;
    private String Username , Password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_login_);
        FindAllViews ();


    }

    @Override
    protected void onStart() {
        progressDialog = new ProgressDialog (this);
        userSessionManager = new UserSessionManager (this);
        mFirebaseAuth = FirebaseAuth.getInstance ();
        if(mFirebaseAuth.getCurrentUser () != null){

        }
        super.onStart ( );
    }

    @Override
    public void onClick(View v) {
        if(v == Login_btn){
            Validations ();
        }
    }

    private void FindAllViews(){
        Username_ET = findViewById (R.id.login_username_txt);
        Password_ET = findViewById (R.id.login_password_txt);
        Login_btn   = findViewById (R.id.login_btn);
    }
    private void Validations(){
        Username = Username_ET.getText ().toString ();
        Password = Password_ET.getText ().toString ();
        if(Username.isEmpty () || Password.isEmpty ()){
            Toast.makeText (getApplicationContext (),"EMPTY FIELDS",Toast.LENGTH_SHORT).show ();
        }
        else {
            progressDialog.setMessage ("Login...");
            progressDialog.show ();
            Firebase_Log (Username,Password);
        }
    }


    private void Firebase_Log(final String Email, final String Password) {
        mFirebaseAuth.signInWithEmailAndPassword (Email,Password).addOnCompleteListener (this, new OnCompleteListener <AuthResult> ( ) {
            @Override
            public void onComplete(@NonNull Task <AuthResult> task) {
                if(task.isSuccessful ()){
                    startActivity (new Intent (Login_Activity.this, MainActivity.class));
                    userSessionManager.createUserLoginSession (Email,Password);
                    progressDialog.dismiss ();
                }
                else {
                    progressDialog.dismiss ();
                    Toast.makeText (getApplicationContext (), "Invalid User", Toast.LENGTH_SHORT).show ( );
                }
            }
        });
    }

}
