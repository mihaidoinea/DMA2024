package ro.ase.ie.g1105_s03;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText etInput;
    ActivityResultLauncher<Intent> activityLauncher;


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
        Log.d("MainActivity", "onCreate()");
        etInput = findViewById(R.id.etInput);

        activityLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                int resultCode = result.getResultCode();
                if(resultCode == RESULT_OK)
                {
                    Intent data = result.getData();
                    Bundle extras = data.getExtras();
                    String rp1 = extras.getString("rp1");
                    Log.d("MainActivity", "Received param is: "+ rp1);
                }
            }
        });

    }

    public void openActivity(View view)
    {
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("p1", etInput.getText().toString());
        intent.putExtras(bundle);
        //startActivity(intent);
//        activityLauncher.launch(intent);
//        Intent callIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
//        Intent callIntent = new Intent(Intent.ACTION_SEND);
//        callIntent.putExtra(Intent.EXTRA_TEXT,"Hello from an Android app");
//        callIntent.setType("text/plain");
//        Intent callIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

//        startActivity(callIntent);

        LinearLayout layout = findViewById(R.id.main);
        layout.setOrientation(LinearLayout.VERTICAL);
        //static element
        TextView tv = new TextView(this);
        tv.setText("Title:");

        //pixel conversion
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int hPixeli = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, displayMetrics);
        LinearLayout.LayoutParams lpt = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, hPixeli);
        tv.setLayoutParams(lpt);

        layout.addView(tv);
        setContentView(layout);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("MainActivity", "onSaveInstanceState()");

        outState.putString("key1", etInput.getText().toString().concat(" extra"));
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d("MainActivity", "onRestoreInstanceState()");

        String restoredValue = savedInstanceState.getString("key1");
        etInput.setText(restoredValue);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("MainActivity", "onStart()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("MainActivity", "onStop()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("MainActivity", "onPause()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("MainActivity", "onResume()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("MainActivity", "onDestroy()");
    }
}