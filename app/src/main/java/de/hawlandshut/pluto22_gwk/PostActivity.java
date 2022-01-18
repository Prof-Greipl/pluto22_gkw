package de.hawlandshut.pluto22_gwk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;
import java.util.Map;

public class PostActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String TAG = "xxPostActivity";

    EditText mEditTextTitle;
    EditText mEditTextText;
    Button mButtonPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        mButtonPost = findViewById( R.id.postButtonPost);
        mEditTextText = findViewById( R.id.postEditTextText);
        mEditTextTitle = findViewById( R.id.postEditTextTitle);
        mButtonPost.setOnClickListener( this );
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        switch (i) {
            case R.id.postButtonPost:
                doPost();
                return;
        }
    }

    private void doPost() {
        Map<String, Object> postMap = new HashMap<>();
        postMap.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
        postMap.put("author", FirebaseAuth.getInstance().getCurrentUser().getEmail());
        postMap.put("title", mEditTextTitle.getText().toString());
        postMap.put("body", mEditTextText.getText().toString());
        postMap.put("timestamp", ServerValue.TIMESTAMP);
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Posts/");
        mDatabase.push().setValue(postMap);
    }
}