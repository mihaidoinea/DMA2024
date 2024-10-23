package ro.ase.ie.g1096_s04;

import android.app.ActionBar;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.LinearLayout;

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
        Button button = inflatedView.findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addControl();
            }
        });
    }
    int counter =0;

    private void addControl() {
        Button button = new Button(MainActivity.this);
        button.setText("Button_" + (++counter));
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int hPixels = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, displayMetrics);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, hPixels);
        button.setLayoutParams(layoutParams);
        LinearLayout linearLayout = inflatedView.findViewById(R.id.extraLayout);
        linearLayout.addView(button);
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