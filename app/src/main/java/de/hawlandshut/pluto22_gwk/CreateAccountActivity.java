package de.hawlandshut.pluto22_gwk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener{

    static final String TAG = "xx MainActivity";

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
        Toast.makeText( getApplicationContext(), "clicked Create Account", Toast.LENGTH_LONG).show();
    }
}