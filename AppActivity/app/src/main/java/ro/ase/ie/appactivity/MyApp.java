package ro.ase.ie.appactivity;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyApp extends Application implements Application.ActivityLifecycleCallbacks{
    private String MyAppTag = "MyAppLevel";

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        Log.d(MyAppTag, "onActivityCreated");
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        Log.d(MyAppTag, "onActivityStarted");
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        Log.d(MyAppTag, "onActivityResumed");
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
        Log.d(MyAppTag, "onActivityPaused");
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
        Log.d(MyAppTag, "onActivityStopped");
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
        Log.d(MyAppTag, "onActivitySaveInstanceState");
    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        Log.d(MyAppTag, "onActivityDestroyed");
    }
}
