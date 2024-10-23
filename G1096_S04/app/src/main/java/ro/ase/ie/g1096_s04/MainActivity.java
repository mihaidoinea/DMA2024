package ro.ase.ie.g1096_s04;

import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    ViewStub viewStub;
    View inflatedView;

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

        initializeControls();
    }

    private void initializeControls() {
        viewStub = findViewById(R.id.viewStub);
        inflatedView = viewStub.inflate();
    }

    public void showLayout(View view)
    {
        viewStub.setVisibility(View.VISIBLE);
    }

    public void hideLayout(View view)
    {
        viewStub.setVisibility(View.GONE);
    }

}