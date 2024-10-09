package ro.ase.csie.ie.testapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    final private String MainActivityTag = MainActivity.class.getSimpleName();

    final private String MyString = "Message saved from saveInstanceState!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Log.d(MainActivityTag, "Hello from onCreate()!");
        Log.w(MainActivityTag, "Hello from onCreate()!");
        Log.e(MainActivityTag, "Hello from onCreate()!");
        Log.i(MainActivityTag, "Hello from onCreate()!");
        Log.v(MainActivityTag, "Hello from onCreate()!");
        Log.wtf(MainActivityTag, "Hello from onCreate()!");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.w(MainActivityTag, "Hello from onPause()!");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.w(MainActivityTag, "Hello from onResume()!");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.w(MainActivityTag, "Hello from onStart()!");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.w(MainActivityTag, "Hello from onStop()!");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.w(MainActivityTag, "Hello from onDestroy()!");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.w(MainActivityTag, "Hello from onSaveInstanceState()!");
        outState.putString("key", MyString.concat(" plus something new"));
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.w(MainActivityTag, "Hello from onRestoreInstanceState()!");
        Log.d(MainActivityTag, savedInstanceState.getString("key"));
    }

    public void apasaButon(View view)
    {
        Log.d(MainActivityTag, "Button was clicked!");
    }

}