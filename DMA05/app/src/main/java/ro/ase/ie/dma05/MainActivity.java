package ro.ase.ie.dma05;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements FirstFragment.IFragmentAction {

    FragmentManager fragmentManager;

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
        fragmentManager = getSupportFragmentManager();
        if(savedInstanceState == null)
        {
            Bundle bundle = new Bundle();
            bundle.putString("key", "Hello from MainActivity!");
            fragmentManager
                    .beginTransaction()
                    .add(R.id.fragmentContainerView, FirstFragment.class, bundle)
                    .commit();
        }
    }

    public void switchFragment(View view)
    {
        if(view.getId() == R.id.button)
        {
            replaceFragment(new FirstFragment());
        }
        else if(view.getId() == R.id.button2)
        {
            replaceFragment(new SecondFragment());
        }
    }

    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerView, fragment);
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.commit();
    }

    @Override
    public void clickEvent(String message) {
        Log.d("MainActivity", "Message from fragment: " + message);
    }
}