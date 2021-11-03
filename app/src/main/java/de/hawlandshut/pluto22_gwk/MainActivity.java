
package de.hawlandshut.pluto22_gwk;

import static android.widget.Toast.*;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    static final String TAG = "xx MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: just for testing, remove later
        Toast.makeText( getApplicationContext(), "Ein erster Toast", Toast.LENGTH_LONG).show();
        Log.d(TAG, "called onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "called onStart");
        // TODO: nur zum Testen von SignIn - muss wieder raus
        // gehe direkt zu signIn
        Intent intent = new Intent( getApplication(), SignInActivity.class);
        startActivity( intent );
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
            case R.id.menu_main_post1:
                Toast.makeText( getApplicationContext(), "Pressed Post1", Toast.LENGTH_LONG).show();
                return true;

            case R.id.menu_main_post2:
                Toast.makeText( getApplicationContext(), "Pressed Post2", Toast.LENGTH_LONG).show();
                return true;

            case R.id.menu_main_test:
                Toast.makeText( getApplicationContext(), "Pressed Test", Toast.LENGTH_LONG).show();
                return true;

            default:
                return true;
        }

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