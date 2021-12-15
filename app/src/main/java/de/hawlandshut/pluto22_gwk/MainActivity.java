
package de.hawlandshut.pluto22_gwk;

import static android.widget.Toast.*;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hawlandshut.pluto22_gwk.model.Post;
import de.hawlandshut.pluto22_gwk.testdata.PostTestData;

public class MainActivity extends AppCompatActivity {

    static final String TAG = "xx MainActivity";

    ListView mListView;
    ArrayList<Post> mPostList; // Diese Liste wird später die Posts enthalten, die anzeigen wollen.
    ArrayAdapter<Post> mAdapter;

    // TODO: remove - only for testing
    private final static String TEST_MAIL = "dietergreipl@gmail.com";
    private final static String TEST_PASSWORD = "123456";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: just for testing, remove later
        Log.d(TAG, "called onCreate");

        // TODO: Only for testing
        PostTestData.createTestData();
        mPostList = (ArrayList<Post>) PostTestData.postTestList;

        // Adapter initialiseren und implementieren
        mAdapter = new ArrayAdapter<Post>(
                this,
                android.R.layout.simple_list_item_2,
                android.R.id.text1,
                mPostList){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView text1, text2;
                text1 = view.findViewById( android.R.id.text1);
                text2 = view.findViewById( android.R.id.text2);

                Post post = getItem(position);

                text1.setText( post.author);
                text2.setText( post.body);

                Log.d(TAG, "Called with position : " + position);

                return view;
            }
        };


        mListView = findViewById( R.id.mainListViewMessages );
        mListView.setAdapter( mAdapter );
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "called onStart");
        FirebaseUser user;
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null){
            // Kein User angemeldet
            // Toast.makeText( getApplicationContext(), "Kein User angemeldet. Gehe zu SignIn!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent( getApplication(), SignInActivity.class);
            startActivity( intent );
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate( R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch ( item.getItemId()) {
            case R.id.menu_main_manage_account:
                Intent intent = new Intent( getApplication(), ManageAccountActivity.class);
                startActivity( intent );
                return true;

            case R.id.menu_main_test_write:
                DatabaseReference db = FirebaseDatabase.getInstance().getReference("Posts/");

                Map<String, Object> test = new HashMap<>();
                test.put("uid", "test_uid");
                test.put("name", "Hans Huber");
                test.put("matnr", "123456");
                test.put("timestamp", ServerValue.TIMESTAMP);
                db.push().setValue( test );

            default:
                return true;
        }

    }

    private void doCreateTestUser() {
        String email = TEST_MAIL;
        String password = TEST_PASSWORD;

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                        this,
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    //Erfolgsfall
                                    Toast.makeText( getApplicationContext(), "Created User", Toast.LENGTH_LONG).show();
                                } else {
                                    // Fehlerfall
                                    Toast.makeText( getApplicationContext(), "User Creation Failed", Toast.LENGTH_LONG).show();
                                    Log.d(TAG, "Create Account Error :" +  task.getException().getMessage());
                                }
                            }
                        });
    }


    // TODO: remove, if not needed.
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"called onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"called onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"called onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG,"called onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"called onDestroy");
    }

}