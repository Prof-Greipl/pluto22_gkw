package de.hawlandshut.pluto22_gwk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener{

    static final String TAG = "xx CreateAccountAct.";

    EditText mEditTextEmail;
    EditText mEditTextPassword;
    EditText mEditTextPassword1;
    Button mButtonCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        mEditTextEmail = findViewById( R.id.createAccountEditTextEmail );
        mEditTextPassword = findViewById( R.id.createAccountEditTextPassword);
        mEditTextPassword1 = findViewById( R.id.createAccountEditTextPassword1);
        mButtonCreateAccount = findViewById( R.id.createAccountButtonCreateAccount );

        mButtonCreateAccount.setOnClickListener( this );

        // TODO: Presets for testing. Remove later.
        mEditTextEmail.setText("dietergreipl@gmail.com");
        mEditTextPassword.setText("123456");
        mEditTextPassword1.setText("123456");
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        switch(i){
            case R.id.createAccountButtonCreateAccount:
                doCreateAccount();
                return;
        }
    }

    private void doCreateAccount() {
        String email = mEditTextEmail.getText().toString();
        String password = mEditTextPassword.getText().toString();

        // TODO: Verify equality of password and password1

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                        this,
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    //Erfolgsfall
                                    Toast.makeText( getApplicationContext(), "Created User", Toast.LENGTH_LONG).show();
                                    finish();
                                } else {
                                    // Fehlerfall
                                    Toast.makeText( getApplicationContext(), "User Creation Failed", Toast.LENGTH_LONG).show();
                                    Log.d(TAG, "Create Account Error :" +  task.getException().getMessage());
                                }
                            }
                        });
    }
}