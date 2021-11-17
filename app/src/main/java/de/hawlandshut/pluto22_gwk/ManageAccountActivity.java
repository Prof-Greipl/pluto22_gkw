package de.hawlandshut.pluto22_gwk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ManageAccountActivity extends AppCompatActivity implements View.OnClickListener{

    static final String TAG = "xx ManageAccountActivity";

    // 3.1 Declare UI-Variables
    TextView mTextViewEmail;
    TextView mTextViewAccountVerified;
    TextView mTextViewId;
    Button mButtonSignOut;
    Button mButtonSendActivationMail;
    Button mButtonDeleteAccount;
    EditText mEditTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_account);

        // 3.2. Initialize UI Variables
        mTextViewEmail = findViewById( R.id.manageAccountTextViewEmail);
        mTextViewAccountVerified = findViewById( R.id.manageAccountTextViewAccountVerified);
        mTextViewId = findViewById( R.id.manageAccountTextViewId);
        mButtonSignOut =findViewById( R.id.manageAccountButtonSignOut);
        mButtonSendActivationMail =findViewById( R.id.manageAccountButtonSendActivationMail);
        mButtonDeleteAccount =findViewById( R.id.manageAccountButtonDeleteAccount);
        mEditTextPassword = findViewById( R.id.manageAccountEditTextPassword);

        // 3.3. Listener mit Button verknüpfen
        mButtonDeleteAccount.setOnClickListener( this );
        mButtonSendActivationMail.setOnClickListener( this );
        mButtonSignOut.setOnClickListener( this );
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        switch(i){
            case R.id.manageAccountButtonSignOut:
                doSignOut();
                return;

            case R.id.manageAccountButtonSendActivationMail:
                doSendActivationMail();
                return;

            case R.id.manageAccountButtonDeleteAccount:
                doDeleteAccount();
                return;
        }
    }

    private void doDeleteAccount() {
        Toast.makeText( getApplicationContext(), "clicked Delete Account", Toast.LENGTH_LONG).show();
    }

    private void doSendActivationMail() {
        Toast.makeText( getApplicationContext(), "clicked Send Act. Mail", Toast.LENGTH_LONG).show();
    }

    private void doSignOut() {
        Toast.makeText( getApplicationContext(), "clicked Sign Out", Toast.LENGTH_LONG).show();
    }
}