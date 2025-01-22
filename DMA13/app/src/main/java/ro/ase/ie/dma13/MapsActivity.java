package ro.ase.ie.dma13;

import androidx.fragment.app.FragmentActivity;

import android.os.Build;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import ro.ase.ie.dma13.databinding.ActivityMapsBinding;

import static android.Manifest.permission.ACCESS_BACKGROUND_LOCATION;
import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Looper;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private static final int REQUEST_CODE = 1001;
    private static final int REQUEST_BCK_CODE = 1002;

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private SupportMapFragment mapFragment;

    private GeofencingClient geofencingClient;
    List<Geofence> geofenceList = new ArrayList<>(Arrays.asList(new Geofence.Builder()
            // Set the request ID of the geofence. This is a string to identify this
            // geofence.
            .setRequestId("homeId")
            .setCircularRegion(44.420881824757586, 26.14974061408763, 200)
            .setExpirationDuration(Geofence.NEVER_EXPIRE)
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT)
            .build()));
    private PendingIntent geofencePendingIntent;


    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

//        SharedPreferences sharedPreferences = null;
//        SharedPreferences.Editor edit = sharedPreferences.edit();

        checkLocationPermissions();

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        LocationProvider locationProvider = locationManager.getProvider(LocationManager.FUSED_PROVIDER);
        Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.FUSED_PROVIDER);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {

            }
        };
        locationManager.requestLocationUpdates(locationProvider.getName(), 0, 0, locationListener);



        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);



        //1. getLastLocation
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            LatLng newLoc = new LatLng(location.getLatitude(), location.getLongitude());
                            mMap.addMarker(new MarkerOptions().position(newLoc).title("New Update"));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(newLoc));
                        }
                    }
                });

        //2. getUpdates for location
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    LatLng newLoc = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(newLoc).title("New Update"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(newLoc));
                }
            }
        };
    }

    private PendingIntent getGeofencePendingIntent() {
        // Reuse the PendingIntent if we already have it.
        if (geofencePendingIntent != null) {
            return geofencePendingIntent;
        }
        Intent intent = new Intent(this, GeofenceBroadcastReceiver.class);
        // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when
        // calling addGeofences() and removeGeofences().
        geofencePendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_MUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
        return geofencePendingIntent;
    }


    private GeofencingRequest getGeofencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofences(geofenceList);
        return builder.build();
    }

    @Override
    protected void onPause() {
        fusedLocationClient.removeLocationUpdates(locationCallback);
        super.onPause();
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onResume() {
        super.onResume();
        checkLocationPermissions();
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }

    private void checkLocationPermissions() {
        if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION, ACCESS_BACKGROUND_LOCATION}, REQUEST_CODE);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                if (ActivityCompat.checkSelfPermission(this, ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{ACCESS_BACKGROUND_LOCATION}, REQUEST_BCK_CODE);
                }
            }
            geofencingClient = LocationServices.getGeofencingClient(this);
            PendingIntent pendingIntent = getGeofencePendingIntent();
            GeofencingRequest geofencingRequest = getGeofencingRequest();

            geofencingClient.addGeofences(geofencingRequest, pendingIntent)
                    .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Snackbar.make(mapFragment.getView(), "Geofences added.", Snackbar.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(this, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("Geofence", e.getMessage(), e.fillInStackTrace());

                            Snackbar.make(mapFragment.getView(), "Geofences not added.", Snackbar.LENGTH_LONG).show();
                        }
                    });
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {



        switch (requestCode) {
            case REQUEST_CODE:
                Log.d("MapsActivity", "REQUEST_CODE");
                if (grantResults.length > 0) {
                    boolean coarseAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean fineAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if(coarseAccepted && fineAccepted)
                    {
                        Snackbar.make(mapFragment.getView(), "Permission Granted, Now you can access coarse or fine location.", Snackbar.LENGTH_LONG).show();
                        Log.d("MapsActivity", "Permission Granted, Now you can access coarse or fine location.");
                    }
                } else {
                    Snackbar.make(mapFragment.getView(), "No Permissions Granted!", Snackbar.LENGTH_LONG).show();

                    Log.d("MapsActivity", "No Permissions Granted!");
                }
                break;
            case REQUEST_BCK_CODE:
                Log.d("MapsActivity", "REQUEST_BCK_CODE");
                if (grantResults.length > 0) {
                    boolean backAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if(backAccepted)
                    {
                        Snackbar.make(mapFragment.getView(), "Permission Granted, Now you can access background location.", Snackbar.LENGTH_LONG).show();
                        Log.d("MapsActivity", "Permission Granted, Now you can access background location.");
                    }
                } else {
                    Snackbar.make(mapFragment.getView(), "No Permissions Granted!", Snackbar.LENGTH_LONG).show();

                    Log.d("MapsActivity", "No Permissions Granted!");
                }
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION}, REQUEST_CODE);
        }
        else {

            mMap.setMyLocationEnabled(true);
        }
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 17f));
    }
}