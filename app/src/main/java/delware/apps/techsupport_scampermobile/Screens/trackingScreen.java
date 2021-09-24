package delware.apps.techsupport_scampermobile.Screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import delware.apps.techsupport_scampermobile.CalorieCalculator;
import delware.apps.techsupport_scampermobile.DistanceCalculator;
import delware.apps.techsupport_scampermobile.LocationList;
import delware.apps.techsupport_scampermobile.MainActivity;
import delware.apps.techsupport_scampermobile.NavigationService;
import delware.apps.techsupport_scampermobile.R;
import delware.apps.techsupport_scampermobile.RunLog;
import delware.apps.techsupport_scampermobile.Tracking_Settings;

public class trackingScreen extends AppCompatActivity {

    public static final int DEFAULT_UPDATE_INTERVAL = 10;
    public static final int FAST_UPDATE_INTERVAL = 5;
    private static final int PERMISSIONS_FINE_LOCATION = 69;

    public AdView bannerAd;
    //current location
    public Location currentLocation;
    //prev location
    public Location previousLocation;
    //distance between the newest and previous coordinates
    public double fractionDistance;
    //Total distance
    public double totalDistance = 0;

    public double totalCalories = 0.0;
    //Accelerometer Speed at each interval !not average speed!
    public double currentAcSpeed;

    private List<Location> savedLocations;


    //config file for settings related to FusedLocationProviderClient
    public LocationRequest locationRequest;

    LocationCallback locationCallBack;

    //Google API for location services
    private FusedLocationProviderClient fusedLocationClient;


    public static final String INTENT_START_NAME = "inputStart";

    public Tracking_Settings trackingSettings;
    public DistanceCalculator distanceCalculator;
    public CalorieCalculator calorieCalculator;
    public SharedPreferences prefs;

    public enum State
    {
        running,
        paused,
        stopped
    }
    public Chronometer timetxt;
    public TextView distancetxt;
    public TextView speedtxt;
    public TextView caloriestxt;
    public ImageView pausebtn;
    public ImageView playbtn;
    public ImageView stopbtn;
    public int playBtnPresses = 0;
    private boolean running = false;
    private long pauseOffset;
    public long totalTime; //in seconds
    public static State state;

    private ArrayList<Double> distances;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking_screen);
        bannerAd = findViewById(R.id.adView);

        MobileAds.setRequestConfiguration(new RequestConfiguration.Builder().setTestDeviceIds(Arrays.asList("17DC790ACE65C13AC382E329E2098CE0"))
                        .build());

        // Gets the ad view defined in layout/ad_fragment.xml with ad unit ID set in
        // values/strings.xml.

        // Create an ad request.
        AdRequest adRequest = new AdRequest.Builder().build();

        // Start loading the ad in the background.
        bannerAd.loadAd(adRequest);

        distances = new ArrayList<>();


        prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        timetxt = findViewById(R.id.tvTime);
        distancetxt = findViewById(R.id.tvDistance);
        speedtxt = findViewById(R.id.tvSpeed);
        pausebtn = findViewById(R.id.btnPause);
        playbtn = findViewById(R.id.btnPlay);
        stopbtn = findViewById(R.id.btnStop);
        caloriestxt = findViewById(R.id.tvCalories);

        stopbtn.setEnabled(false);
        pausebtn.setEnabled(false);

        timetxt.setBase(SystemClock.elapsedRealtime());
        trackingSettings = new Tracking_Settings();

        distanceCalculator = new DistanceCalculator();
        calorieCalculator = new CalorieCalculator();

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        //set properties of location request
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(1000 * DEFAULT_UPDATE_INTERVAL);
        locationRequest.setFastestInterval(1000 * FAST_UPDATE_INTERVAL);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);



        //event that is triggered whenever the update interval is met
        locationCallBack = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);

                //get the previous location
                previousLocation = currentLocation;

                //save the location
                currentLocation = locationResult.getLastLocation();
                System.out.println(locationResult.getLastLocation());
                if(currentLocation == null) {
                    System.out.println("1 location is null");
                    speedtxt.setText("Error");
                }else {
                    currentAcSpeed = currentLocation.getSpeed();
                    System.out.println("Speed: " + currentLocation.getSpeed());
                    addLocalToList(currentLocation);
                    System.out.println("Current Location: " + currentLocation + " added to list");
                    speedtxt.setText(String.valueOf(String.format("%.2f",currentLocation.getSpeed())) + " MPH");
                }

                //:)
                //if there are at least 2 locations in the list
                if (savedLocations.size() >= 2){
                    fractionDistance = distanceCalculator.getDistanceM(previousLocation.getLatitude(), previousLocation.getLongitude(),
                            currentLocation.getLatitude(),currentLocation.getLongitude());
                    System.out.println("distance: " + fractionDistance);

                    distances.add(fractionDistance);
//                    if(distances.size() > 10)
//                        distances.remove(11);

                    totalDistance += fractionDistance;
                    System.out.println(totalDistance);
                    distancetxt.setText(String.valueOf(String.format("%.3f",totalDistance/1609)) + " miles");
                }
                System.out.println("Calories Burnt: " + calorieCalculator.caloriesBurned(currentAcSpeed, getTime()));

                if(prefs.getBoolean("LoggedIn", false)){
                    totalCalories += (calorieCalculator.caloriesBurned(currentAcSpeed)/360);
                    caloriestxt.setText("Calories: " + String.format("%.2f", totalCalories));
                }else{
                    caloriestxt.setText("Login For Cal");
                    totalCalories = 100;
                }

                System.out.println("Location Interval Triggered");

                System.out.println("Total Calories: " + totalCalories);
            }
        };

        timetxt.setBase(SystemClock.elapsedRealtime());

    }


    //    THIS IS AN IMPORTANT FUNCTION TO EXIT THE CURRENT INTENT AND GO BACK TO THE PREVIOUS ACTIVITY
    public void exitIntent() {
        ScrapRun();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);//Exits current intent
        intent.putExtra("EXIT", true);
        startActivity(intent);
    }

    //Clickable Icon Lets the user exit current intent/activity and return to the previous screen
    public void goBack(View v) {
        exitIntent();
    }


    public void StartRun(View v){
        playBtnPresses++;
        playbtn.setEnabled(false);
        playbtn.setVisibility(View.INVISIBLE);

        stopbtn.setVisibility(View.VISIBLE);
        pausebtn.setVisibility(View.VISIBLE);
        pausebtn.setEnabled(true);
        stopbtn.setEnabled(true);
        state = State.running;
        getRunIntent(state);


        if(!running) {
            //Start Timer and Tracking for the first time
            totalDistance = 0.0;
            timetxt.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            timetxt.start();
            running = true;
            startLocationUpdates();

        }
        else {
            //Resume
        }


    }

    public void PauseRun(View v){
        playbtn.setVisibility(View.VISIBLE);
        playbtn.setEnabled(true);
        pausebtn.setVisibility(View.INVISIBLE);
        pausebtn.setEnabled(false);

        if (running){
            pauseOffset = SystemClock.elapsedRealtime() - timetxt.getBase();
            timetxt.stop();
            running = false;
            state = State.paused;
        }



    }

    public void StopRun(View v) {
        totalTime = (SystemClock.elapsedRealtime() - timetxt.getBase()) / 1000;

        if (totalTime < 60) {
            pausebtn.setEnabled(false);
            pausebtn.setVisibility(View.INVISIBLE);
            stopbtn.setEnabled(false);
            stopbtn.setVisibility(View.INVISIBLE);

            playbtn.setVisibility(View.VISIBLE);
            playbtn.setEnabled(true);
            timetxt.stop();
            timetxt.setBase(SystemClock.elapsedRealtime());
            pauseOffset = 0;
            running = false;
            state = State.stopped;
            getRunIntent(state);
            distances.clear();
            Toast.makeText(getApplicationContext(), "Run is too Short to save", Toast.LENGTH_LONG).show();
        } else {
            //s
            // TOP EVERYTHING
            pausebtn.setEnabled(false);
            pausebtn.setVisibility(View.INVISIBLE);
            stopbtn.setEnabled(false);
            stopbtn.setVisibility(View.INVISIBLE);

            playbtn.setVisibility(View.VISIBLE);
            playbtn.setEnabled(true);
            timetxt.stop();
            System.out.println(totalTime);
            timetxt.setBase(SystemClock.elapsedRealtime());
            pauseOffset = 0;
            running = false;
            state = State.stopped;
            getRunIntent(state);
            //resets presses so you can restart the run

            float distance = (float) (totalDistance / 1609);
            double calories;
            SimpleDateFormat formater = new SimpleDateFormat("MM/dd/yyyy");
            int hours = (int) totalTime / 3600;
            int leftoverSeconds = (int) (totalTime % 3600);
            System.out.println("leftovers: " + totalTime % 3600);
            System.out.println("Hours: " + hours);
            int minutes = leftoverSeconds / 60;
            float hoursConverted = (float) hours + ((float) minutes / 60);
            System.out.println("seconds: " + totalTime);
            System.out.println("minutes: " + minutes);
            totalTime = SystemClock.elapsedRealtime() - timetxt.getBase();
            calories = calorieCalculator.caloriesBurned(getSpeed(), getTime());
            String Date = formater.format(Calendar.getInstance().getTime());
            RunLog runLog = new RunLog(distance, hours, minutes, (int) calories, Date, "Running/Walking");
            System.out.println(runLog.speed);
            MainActivity.databaseHandler.addLog(runLog);
            MainActivity.setWeeklyStats();
            System.out.println(totalTime);
            stopLocationUpdates();
            distances.clear();
            totalDistance = 0;
            fractionDistance = 0;
            distancetxt.setText("0.00");
            try {
                String calorie = String.format("%.2f", calories);
                caloriestxt.setText("Calories Burned: " + calorie);
            } catch (Exception e) {
                System.out.println("Can't Calculate Calories");
                caloriestxt.setText("Error");
            }
        }
    }

    private void stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallBack);
    }

    private void startLocationUpdates() {
        try {
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallBack, null);
        }catch(Exception e){
            System.out.println("Congrats you broke it");
        }
        updateGPS();


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSIONS_FINE_LOCATION:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    updateGPS();
                } else {
                    Toast.makeText(this, "This app requires the use of location features to successfully operate", Toast.LENGTH_SHORT).show();
                    System.out.println("Location permissions failed or was denied");
                    exitIntent();
                }
                break;
        }

    }
//For first time location
    private void updateGPS() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            //user has given permission
            //gets last known location
            fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {



                    if(location == null) {
                        System.out.println("location is null");
                    }
                    else{
                        currentLocation = location;
                        System.out.println("location: " + location);
                        addLocalToList(currentLocation);
                        System.out.println(currentLocation);
                    }
                }
            });

        }else {
            //permissions not granted yet
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_FINE_LOCATION);
            }
        }
    }
    public void ScrapRun(){
        timetxt.stop();
        totalTime = 0;
        timetxt.setBase(SystemClock.elapsedRealtime());
        pauseOffset=0;
        running = false;
        state = State.stopped;
        getRunIntent(state);
        //Toast.makeText(getApplicationContext(), "Run Cancelled", Toast.LENGTH_LONG).show();
    }

    public void getRunIntent(State state){
        Intent serviceIntent = new Intent(this, NavigationService.class);
        String txtBody = "Run Still In Progress \n" + "Time Elapsed: " + SystemClock.elapsedRealtime();
        switch(state){
            case running:
                System.out.println("running case triggered");
                serviceIntent.putExtra(INTENT_START_NAME, txtBody);

                startService(serviceIntent);
                break;


            case stopped:
                stopService(serviceIntent);
                break;

        }
    }

    private void addLocalToList(Location location){
        LocationList locationList = (LocationList) getApplicationContext();
        savedLocations = locationList.getMyLocations();
        savedLocations.add(location);
        System.out.println("Hello" + location);
    }

    public double getSpeed()
    {
        double sum = 0;
        for (int i = 0; i < distances.size(); i++){
            sum += (distances.get(i)) * 0.00062137;

        }
        //distance over time returns MPH
        return (sum/(getTime()));
    }
//returns in seconds
    public double getTime(){
        double interval;
        double time;
        if(trackingSettings.gpsFastState)
            interval = FAST_UPDATE_INTERVAL;
        else
            interval = DEFAULT_UPDATE_INTERVAL;

        System.out.println(interval);
        time = (interval*distances.size()) * 0.00027778;
        return time;


    }

    @Override
    public void onPause() {
        if (bannerAd != null) {
            bannerAd.pause();
        }
        super.onPause();
    }
    @Override
    public void onResume() {
        super.onResume();
        if (bannerAd != null) {
            bannerAd.resume();
        }
    }

    /** Called before the activity is destroyed */
    @Override
    public void onDestroy() {
        if (bannerAd != null) {
            bannerAd.destroy();
        }
        super.onDestroy();
    }

}