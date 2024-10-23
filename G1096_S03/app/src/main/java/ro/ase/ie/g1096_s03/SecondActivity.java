package ro.ase.ie.g1096_s03;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SecondActivity extends AppCompatActivity {

    private String ActivityTag = SecondActivity.class.getName();
    String key1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        key1 = extras.getString("key1");
        Log.d(ActivityTag, "Value received: " + key1);
    }

    public void sendResponse(View view)
    {
        Intent intent = new Intent();
        intent.putExtra("param1", key1.concat(", echoed back!"));
        setResult(RESULT_OK, intent);
        finish();
    }
}