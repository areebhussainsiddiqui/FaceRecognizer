package pp.facerecognizer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

import pp.facerecognizer.Shared_Preference.UserSessionManager;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button add_attendance_btn, door_btn, show_attendance_btn, logout_btn;
    private UserSessionManager sessionManager;
    private static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    String lattitude, longitude;
    private Location currentLocation;
    private LocationRequest mLocationRequest;
    private long UPDATE_INTERVAL = 10 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FindAllViews();
        getLocationPermission();

        startLocationUpdates();

    }

    @Override
    protected void onStart() {

        sessionManager = new UserSessionManager(this);
        super.onStart();

        /*add_attendance_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentLocation != null){
                    double distance = calculteDistence(currentLocation);
                    if (distance < 30){
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("north campus");
                        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {


                            } });

                        AlertDialog dialog = builder.create();
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.show();
                    }
                }
            }
        });
*/

    }

    @Override
    public void onClick(View v) {
        if (v == add_attendance_btn) {
         startActivity (new Intent (MainActivity.this,FaceRecognition_Activity.class));
            //   CheckPermission();

        }
        if (v == door_btn) {
        }
        if (v == show_attendance_btn) {
            startActivity(new Intent (MainActivity.this, Show_Attendance_Activity.class));
        }
        if (v == logout_btn) {
            FirebaseAuth.getInstance().signOut();
            sessionManager.logoutUser();
            startActivity(new Intent(MainActivity.this, Login_Activity.class));
        }
    }

    private void FindAllViews() {
        add_attendance_btn = findViewById(R.id.main_add);
        door_btn = findViewById(R.id.main_door);
        show_attendance_btn = findViewById(R.id.main_show);
        logout_btn = findViewById(R.id.main_logout);
    }


    private void CheckPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            } else {
                LocationChecher();
                // startActivity (new Intent ("android.media.action.IMAGE_CAPTURE"));
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (( ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
                        //     startActivity (new Intent ("android.media.action.IMAGE_CAPTURE"));
                        //Request location updates:
                        //locationManager.requestLocationUpdates(provider, 400, 1, this);
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();


                }
                return;
            }


        }
    }

    private void LocationChecher() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 20, new Listener());
        // Have another for GPS provider just in case.
        // Try to request the location immediately
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (location == null) {
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
        if (location != null) {
            if ((location.getLatitude() == 24.90291268) && (location.getLongitude() == 67.19973475)) {
                handleLatLng(location.getLatitude(), location.getLongitude());
                startActivity(new Intent("android.media.action.IMAGE_CAPTURE"));
            } else {
                Toast.makeText(getApplicationContext(), "YOU ARE FAR AWAY FROM DESTIONATION", Toast.LENGTH_SHORT).show();
            }

        }
        Toast.makeText(getApplicationContext(),
                "Trying to obtain GPS coordinates. Make sure you have location services on.",
                Toast.LENGTH_SHORT).show();
    }

    private void handleLatLng(double latitude, double longitude) {
        Log.d("BLACK", "(" + latitude + "," + longitude + ")");
    }

    private class Listener implements LocationListener {
        public void onLocationChanged(Location location) {
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
            handleLatLng(latitude, longitude);
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        }
    }


    public void onLocationChanged(Location location) {
        // New location has now been determined
        String msg = "Updated Location: " +
                Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    @SuppressLint("MissingPermission")
    protected void startLocationUpdates() {

        // Create the location request to start receiving updates
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        // mLocationRequest.setInterval(UPDATE_INTERVAL);
        // mLocationRequest.setFastestInterval(FASTEST_INTERVAL);

        // Create LocationSettingsRequest object using location request
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();

        // Check whether location settings are satisfied
        // https://developers.google.com/android/reference/com/google/android/gms/location/SettingsClient
        SettingsClient settingsClient = LocationServices.getSettingsClient(this);
        settingsClient.checkLocationSettings(locationSettingsRequest);

        // new Google API SDK v11 uses getFusedLocationProviderClient(this)
        getFusedLocationProviderClient(this).getLastLocation().addOnSuccessListener(new OnSuccessListener <Location> () {
            @Override
            public void onSuccess(Location location) {
                currentLocation = location;
            }
        })
                .addOnFailureListener(new OnFailureListener () {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this,"Something went wrong",Toast.LENGTH_LONG);
                    }
                });
    }

    public double calculteDistence(Location location){

        //North campus
        Location locationB = new Location("point B");
        locationB.setLatitude(24.974011);
        locationB.setLongitude( 67.046799);

        double distance = location.distanceTo(location);
        return (distance/1000);// distence in km
    }

}