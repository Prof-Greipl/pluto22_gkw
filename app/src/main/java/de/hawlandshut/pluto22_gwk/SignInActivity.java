package de.hawlandshut.pluto22_gwk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener{

    static final String TAG = "xx SignInActivity";

    // 3.1 Declare "UI-Variables"
    EditText mEditTextEmail;
    EditText mEditTextPassword;
    Button mButtonSignIn;
    Button mButtonResetPassword;
    Button mButtonCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        Log.d(TAG, "onCreate called");

        // 3.2 Init UI Variables
        mEditTextEmail = findViewById( R.id.signInEditTextEmail);
        mEditTextPassword = findViewById( R.id.signInEditTextPassword);
        mButtonSignIn = findViewById( R.id.signInButtonSignIn);
        mButtonResetPassword = findViewById( R.id.signInButtonResetPassword);
        mButtonCreateAccount = findViewById( R.id.signInButtonCreateAccount);

        // 3.3 Implement Listeners (Verbindung UI-Element - Listerner herstellen)
        mButtonSignIn.setOnClickListener( this );
        mButtonCreateAccount.setOnClickListener( this );
        mButtonResetPassword.setOnClickListener( this );

        // TODO: Presets only for testing - remove later
        mEditTextEmail.setText("dietergreipl@gmail.com");
        mEditTextPassword.setText("123456");
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null)
            finish(); // In diesem Fall kommen wir von CreateAccount!
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "ID = " + v.getId());
        int i = v.getId();
        switch(i){
            case R.id.signInButtonCreateAccount:
                doGotoCreateAccount();
                return;
                
            case R.id.signInButtonSignIn:
                doSignIn();
                return;

            case R.id.signInButtonResetPassword:
                doResetPassword();
                return;

        }
    }

    // Business Logic
    private void doSignIn() {
        String email = mEditTextEmail.getText().toString();
        String password = mEditTextPassword.getText().toString();

        // TODO (S5-HW): Check Email, Check Password

        // Check, if any user is signed in. If yes, go home...
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            Toast.makeText( getApplicationContext(), "Please sign out first.", Toast.LENGTH_LONG).show();
            return;
        }

        FirebaseAuth.getInstance().signInWithEmailAndPassword( email, password)
                .addOnCompleteListener(
                        this,
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    //Erfolgsfall
                                    Toast.makeText( getApplicationContext(), "You are signed in.", Toast.LENGTH_LONG).show();
                                    finish();
                                } else {
                                    // Fehlerfall
                                    Toast.makeText( getApplicationContext(), "Sign in failed", Toast.LENGTH_LONG).show();
                                    Log.d(TAG, "Create Account Error :" +  task.getException().getMessage());
                                }
                            }
                        }
                );
    }

    private void doResetPassword() {
        String email = mEditTextEmail.getText().toString();

        FirebaseAuth.getInstance().sendPasswordResetEmail( email )
                .addOnCompleteListener(
                        this,
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    //Erfolgsfall
                                    Toast.makeText( getApplicationContext(), "PW-Reset Mail sent.", Toast.LENGTH_LONG).show();
                                } else {
                                    // Fehlerfall
                                    Toast.makeText( getApplicationContext(), "Sending PW-Reset Mail Failed", Toast.LENGTH_LONG).show();
                                    Log.d(TAG, "Sending password reset mail failed : " +  task.getException().getMessage());
                                }
                            }
                        }
                );
    }

    private void doGotoCreateAccount() {
        Intent intent = new Intent( getApplication(), CreateAccountActivity.class);
        startActivity( intent );
    }
}