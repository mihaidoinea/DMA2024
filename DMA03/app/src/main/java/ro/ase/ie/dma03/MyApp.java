package ro.ase.ie.dma03;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyApp extends Application implements Application.ActivityLifecycleCallbacks {
    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        Log.d("MyApp", "onActivityCreated");
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        Log.d("MyApp", "onActivityStarted");
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        Log.d("MyApp", "onActivityResumed");
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
        Log.d("MyApp", "onActivityPaused");
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
        Log.d("MyApp", "onActivityStopped");
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
        Log.d("MyApp", "onActivitySaveInstanceState");
    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        Log.d("MyApp", "onActivityDestroyed");
    }
}
