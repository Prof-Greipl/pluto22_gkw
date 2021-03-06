
package de.hawlandshut.pluto22_gwk;

import static android.widget.Toast.*;

import android.content.Intent;
import android.net.Uri;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hawlandshut.pluto22_gwk.model.Post;
import de.hawlandshut.pluto22_gwk.testdata.PostTestData;

public class MainActivity extends AppCompatActivity {

    static final String TAG = "xx MainActivity";

    ListView mListView;
    ArrayList<Post> mPostList = new ArrayList<Post>(); // Diese Liste wird später die Posts enthalten, die anzeigen wollen.
    ArrayAdapter<Post> mAdapter;

    ChildEventListener mCEL;
    Query mQuery;
    boolean mListenerIsRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);


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
                text1.setText( post.title+" ("+post.author+")" );
                text2.setText( post.body);
                return view;
            }
        };


        mListView = findViewById( R.id.mainListViewMessages );
        mListView.setAdapter( mAdapter );

        mCEL = getChildEventListener();
        mQuery = FirebaseDatabase.getInstance().getReference("Posts/").limitToLast( 3 );
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "called onStart");
        FirebaseUser user;
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null){
            resetApp();
            Intent intent = new Intent( getApplication(), SignInActivity.class);
            startActivity( intent );
        } else {
            if (!mListenerIsRunning){
                mPostList.clear();
                mQuery.addChildEventListener( mCEL );
                mListenerIsRunning = true;
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    private void resetApp() {
        if (mListenerIsRunning){
            mQuery.removeEventListener( mCEL );
            mListenerIsRunning = false;
        }
        mPostList.clear();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate( R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch ( item.getItemId()) {
            case R.id.menu_main_manage_account:
                intent = new Intent( getApplication(), ManageAccountActivity.class);
                startActivity( intent );
                return true;

            case R.id.menu_main_goto_post:
                intent = new Intent( getApplication(), PostActivity.class);
                startActivity( intent );
                return true;

            default:
                return true;
        }
    }



    private ChildEventListener getChildEventListener(){
        return new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Post p = Post.fromSnapShot( snapshot );
                mPostList.add( p );
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                String key = snapshot.getKey();
                for( int i = 0; i < mPostList.size(); i++){
                    if (key.equals( mPostList.get(i).firebaseKey)){
                        mPostList.remove( i );
                        break;
                    }
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mListenerIsRunning = false;
            }
        };
    }

}