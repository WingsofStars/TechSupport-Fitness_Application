package delware.apps.techsupport_scampermobile;

//import android.Manifest;
import android.content.Intent;
//import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
//import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
//import android.widget.Toast;

//import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;

//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
//import com.google.android.gms.location.LocationResult;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

import delware.apps.techsupport_scampermobile.Screens.TrackingScreen;

public class Tracking_Settings extends AppCompatActivity {
    public TrackingScreen trackingScreen = new TrackingScreen();
//    public static final int DEFAULT_UPDATE_INTERVAL = 10;
//    public static final int FAST_UPDATE_INTERVAL = 5;
//    private static final int PERMISSIONS_FINE_LOCATION = 69;
    //For calculating Distance Between Cords
    static final double _eQuatorialEarthRadius = 6378.1370D;
    static final double _d2r = (Math.PI / 180D);
    TextView tv_latitude, tv_longitude, tv_altitude, tv_accuracy, tv_speed, tv_sensor, tv_updates, tv_address, tv_wayPointCounts;
    ImageView iv_return;
    Button btn_newWaypoint, btn_showWayPointList, btn_showMap;
    Switch sw_locationupdates, sw_gps;
    public double currentSpeed;

//    //current location
//    Location currentLocation;
//
   //list of saved locations
   public List<Location> savedLocations;
//
//    //config file for settings related to FusedLocationProviderClient
//    LocationRequest locationRequest;
//
//    LocationCallback locationCallBack;
//
//    //Google API for location services
//    private FusedLocationProviderClient fusedLocationClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking_settings);

        iv_return = findViewById(R.id.iv_return);
        tv_latitude = findViewById(R.id.tv_latitude);
        tv_longitude = findViewById(R.id.tv_longitude);
        tv_altitude = findViewById(R.id.tv_accuracy);
        tv_accuracy = findViewById(R.id.tv_accuracy);
        tv_speed = findViewById(R.id.tv_speed);
        tv_sensor = findViewById(R.id.tv_sensor);
        tv_updates = findViewById(R.id.tv_updates);
        tv_address = findViewById(R.id.tv_address);
        sw_locationupdates = findViewById(R.id.sw_locationupdates);
        sw_gps = findViewById(R.id.sw_gps);
        btn_newWaypoint = findViewById(R.id.btn_newWaypoint);
        btn_showWayPointList = findViewById(R.id.btn_showWaypointList);
        tv_wayPointCounts = findViewById(R.id.tv_CountOfCrumbs);
        btn_showMap = findViewById(R.id.btn_showMap);

//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
//        //set properties of location request
//        locationRequest = LocationRequest.create();
//        locationRequest.setInterval(1000 * DEFAULT_UPDATE_INTERVAL);
//        locationRequest.setFastestInterval(1000 * FAST_UPDATE_INTERVAL);
//        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        //event that is triggered whenever the update interval is met
//        locationCallBack = new LocationCallback() {
//            @Override
//            public void onLocationResult(@NonNull LocationResult locationResult) {
//                super.onLocationResult(locationResult);
//
//                //save the location
//                updateUIValues(locationResult.getLastLocation());
//                currentLocation = locationResult.getLastLocation();
//
//                System.out.println("Location Interval Triggered");
//            }
//        };

        btn_newWaypoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get gps location

                //add items to list
                addLocalToList(trackingScreen.currentLocation);
            }
        });

        btn_showWayPointList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Tracking_Settings.this, ShowSavedLocationsList.class);
                startActivity(i);
            }
        });

        btn_showMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Tracking_Settings.this, MapsActivity.class);
                startActivity(i);
            }
        });

        sw_gps.setOnClickListener(v -> {
            if (sw_gps.isChecked()) {
                //uses GPS - most accurate
                trackingScreen.locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                tv_sensor.setText("Using GPS sensors");
            } else {
                trackingScreen.locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
                tv_sensor.setText("Using Towers + WIFI");
            }
        });

        sw_locationupdates.setOnClickListener(v -> {
            if (sw_locationupdates.isChecked()) {
                //turn on location tracking
                trackingScreen.startLocationUpdates();

            } else {
                //turn off location tracking
                trackingScreen.stopLocationUpdates();

            }
        });

        trackingScreen.updateGPS();

    }


//    public void stopLocationUpdates() {
//        tv_updates.setText("Location is NOT being tracked");
//        tv_latitude.setText("Not tracking location");
//        tv_longitude.setText("Not tracking location");
//        tv_speed.setText("Not tracking location");
//        tv_address.setText("Not tracking location");
//        tv_accuracy.setText("Not tracking location");
//        tv_altitude.setText("Not tracking location");
//
//        fusedLocationClient.removeLocationUpdates(locationCallBack);
//    }

//    public void startLocationUpdates() {
//        tv_updates.setText("Location is being tracked");
//        try {
//            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallBack, null);
//        }catch(Exception e){
//            System.out.println();
//        }
//        updateGPS();
//
//
//    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        switch (requestCode) {
//            case PERMISSIONS_FINE_LOCATION:
//                if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    updateGPS();
//                } else {
//                    Toast.makeText(this, "This app requires the use of location features to successfully operate", Toast.LENGTH_SHORT).show();
//                    System.out.println("Location permissions failed or was denied");
//                    exitIntent();
//                }
//                break;
//        }
//
//    }

    public void addLocalToList(Location location){
        LocationList locationList = (LocationList) getApplicationContext();
        savedLocations = locationList.getMyLocations();
        savedLocations.add(location);
    }

//    private void updateGPS() {
//        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
//            //user has given permission
//            //gets last known location
//            fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
//                @Override
//                public void onSuccess(Location location) {
//
//                    updateUIValues(location);
//                    currentLocation = location;
//                    LocationList locationList = (LocationList) getApplicationContext();
//                    savedLocations = locationList.getMyLocations();
//                    savedLocations.add(currentLocation);
//                    System.out.println(currentLocation);
//                    if(location != null) {
//                        System.out.println("location is null");
//                    }
//                }
//            });
//
//        }else {
//            //permissions not granted yet
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_FINE_LOCATION);
//            }
//        }
//    }

    public void updateUIValues(Location location) {

        // update all of the text view objects with a new location
        tv_latitude.setText(String.valueOf(location.getLatitude()));
        tv_longitude.setText(String.valueOf(location.getLongitude()));
        tv_accuracy.setText(String.valueOf(location.getAccuracy()));

        if (location.hasAltitude()) {
            tv_altitude.setText(String.valueOf(location.getAltitude()));

        } else {
            tv_altitude.setText("Not available");
        }

        if (location.hasSpeed()) {
            tv_speed.setText(String.valueOf(location.getSpeed()));
            currentSpeed = Double.parseDouble(String.valueOf(location.getSpeed()));

        } else {
            tv_speed.setText("Not available");
        }

        getGeocode(location);
//        Geocoder geocoder = new Geocoder(Tracking_Settings.this);
//        try{
//            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1 );
//            tv_address.setText(addresses.get(0).getAddressLine(0));
//
//        }catch (Exception e) {
//            tv_address.setText("Unable to get street address");
//
//        }

        //show number of items in list
        tv_wayPointCounts.setText(Integer.toString(savedLocations.size()));
    }

    public List<Address> getGeocode(Location location){
        Geocoder geocoder = new Geocoder(Tracking_Settings.this);
        List<Address> addresses = null;
        try{
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1 );
            tv_address.setText(addresses.get(0).getAddressLine(0));

        }catch (Exception e) {
            tv_address.setText("Unable to get street address");

        }
        return addresses;
    }

    //uses the Haversine Formula
    public int getDistanceMi(double lat1, double long1, double lat2, double long2){
        double dlong = (long2 - long1) * _d2r;
        double dlat = (lat2 - lat1) * _d2r;
        double a = Math.pow(Math.sin(dlat / 2D), 2D) + Math.cos(lat1 * _d2r) * Math.cos(lat2 * _d2r)
                * Math.pow(Math.sin(dlong / 2D), 2D);
        double c = 2D * Math.atan2(Math.sqrt(a), Math.sqrt(1D - a));
        double d = _eQuatorialEarthRadius * c;
        //double d is in km then we convert to mi
        return (int) (0.62137119 * d);
    }

    public void exitIntent(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);//Exits current intent
        intent.putExtra("EXIT", true);
        startActivity(intent);
    }

    public void goBack(View v){
        exitIntent();
    }
}
