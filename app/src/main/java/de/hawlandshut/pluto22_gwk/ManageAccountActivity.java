package de.hawlandshut.pluto22_gwk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ManageAccountActivity extends AppCompatActivity implements View.OnClickListener{

    static final String TAG = "xx ManageAccountActiv.";

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

        // TODO: Presets for testing. Remove later.
        mEditTextPassword.setText("123456");


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            mTextViewEmail.setText("E-Mail : " + user.getEmail());
            mTextViewId.setText("Technical ID : " + user.getUid() );
            mTextViewAccountVerified.setText(user.isEmailVerified() ? "Konto ist verifiziert" : "Konto ist nicht verifizert.");
        }
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
        FirebaseUser user;
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null){
            Toast.makeText( getApplicationContext(), "Deletion not possible (No user signed in).", Toast.LENGTH_LONG).show();
        }
        else {
            // Reauthenticate
            String email = user.getEmail();
            String password = mEditTextPassword.getText().toString();

            AuthCredential credential = EmailAuthProvider.getCredential( email, password);
            user.reauthenticate( credential )
                    .addOnCompleteListener(
                            this,
                            new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        //Erfolgsfall
                                        Toast.makeText( getApplicationContext(), "Reauth is fine.", Toast.LENGTH_LONG).show();
                                        finalDeletion();
                                    } else {
                                        // Fehlerfall
                                        Toast.makeText( getApplicationContext(), "Reauth failed", Toast.LENGTH_LONG).show();
                                        Log.d(TAG, "Reauth failed : " +  task.getException().getMessage());
                                    }
                                }
                            }
                    );
        }

    }

    private void finalDeletion(){

        FirebaseUser user;
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null){
            Log.e(TAG, "Serious error: Null user in final Deletion");
        } else {
            user.delete()
                    .addOnCompleteListener(
                            this,
                            new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        //Erfolgsfall
                                        Toast.makeText(getApplicationContext(), "Account deleted.", Toast.LENGTH_LONG).show();
                                        finish();
                                    } else {
                                        // Fehlerfall
                                        Toast.makeText(getApplicationContext(), "Deletion failed", Toast.LENGTH_LONG).show();
                                        Log.d(TAG, "Delete Account Error :" + task.getException().getMessage());
                                    }
                                }
                            }
                    );
        }

    }


    private void doSendActivationMail() {
        FirebaseUser user;
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null){
            Toast.makeText( getApplicationContext(), "Sending not possible (No user signed in).", Toast.LENGTH_LONG).show();
        }
        else {
            user.sendEmailVerification()
                    .addOnCompleteListener(
                            this,
                            new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        //Erfolgsfall
                                        Toast.makeText( getApplicationContext(), "Ver. Mail sent.", Toast.LENGTH_LONG).show();
                                    } else {
                                        // Fehlerfall
                                        Toast.makeText( getApplicationContext(), "Sending Verif. Mail Failed", Toast.LENGTH_LONG).show();
                                        Log.d(TAG, "Sending verification mail failed : " +  task.getException().getMessage());
                                    }
                                }
                            }
                    );
        }
    }

    private void doSignOut() {
        FirebaseUser user;
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null){
            Toast.makeText( getApplicationContext(), "Sorry, no user was signed in.", Toast.LENGTH_LONG).show();
        } else {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText( getApplicationContext(), user.getEmail() + " signed out!.", Toast.LENGTH_LONG).show();
            finish();
        }
    }
}