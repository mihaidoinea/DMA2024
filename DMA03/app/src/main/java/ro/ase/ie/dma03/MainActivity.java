package ro.ase.ie.dma03;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private String ActivityTag = MainActivity.class.getName();

    public MainActivity()
    {
//        getApplication().registerActivityLifecycleCallbacks(new MyApp());
    }

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

        Log.d(ActivityTag, "onCreate");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(ActivityTag, "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(ActivityTag, "onResume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(ActivityTag, "onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(ActivityTag, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(ActivityTag, "onDestroy");
    }
}