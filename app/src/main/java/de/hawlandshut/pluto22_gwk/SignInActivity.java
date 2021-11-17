package de.hawlandshut.pluto22_gwk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "ID = " + v.getId());
        int i = v.getId();
        switch(i){
            case R.id.signInButtonCreateAccount:
                doCreateAccount();
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
        Toast.makeText( getApplicationContext(), "Clicked SignIn", Toast.LENGTH_LONG).show();
    }

    private void doResetPassword() {
        Toast.makeText( getApplicationContext(), "Clicked ResetPassword", Toast.LENGTH_LONG).show();
    }

    private void doCreateAccount() {
        Toast.makeText( getApplicationContext(), "Clicked CreateAccount", Toast.LENGTH_LONG).show();
    }
}