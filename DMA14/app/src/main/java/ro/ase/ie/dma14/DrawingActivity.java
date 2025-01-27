package ro.ase.ie.dma14;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class DrawingActivity extends AppCompatActivity {

    GraphView dv ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dv = new GraphView(this);
        setContentView(dv);
    }
}
