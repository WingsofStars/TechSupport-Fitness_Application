package delware.apps.techsupport_scampermobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;

import java.util.ArrayList;
import java.util.Arrays;

public class Statistics extends AppCompatActivity {
     TextView calories;
    TextView distance;
    TextView time;
    AdView mediumAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        calories = findViewById(R.id.calories);
        time = findViewById(R.id.time);
        distance = findViewById(R.id.distance);
        setStatsScreen();


        mediumAd = findViewById(R.id.adView);
        MobileAds.setRequestConfiguration(new RequestConfiguration.Builder().setTestDeviceIds(Arrays.asList("17DC790ACE65C13AC382E329E2098CE0"))
                .build());
        // Gets the ad view defined in layout/ad_fragment.xml with ad unit ID set in
        // values/strings.xml.
        // Create an ad request.
        AdRequest adRequest = new AdRequest.Builder().build();
        // Start loading the ad in the background.
        mediumAd.loadAd(adRequest);
    }

     public void setStatsScreen(){//Show Total Stats
        ArrayList<RunLog> RunLogs = MainActivity.databaseHandler.getAllLogs("All");

        float totaldistance = 0f;
        int totalCalories = 0;
        float totalTime = 0f;

        for( int i = 0; i < MainActivity.databaseHandler.size("All"); i++) {
            totalCalories += RunLogs.get(i).calories;
            totaldistance += RunLogs.get(i).Distance;
            totalTime +=( (RunLogs.get(i).Hours) + (((float)(RunLogs.get(i).Minutes))/60));
        }
        calories.setText(String.valueOf(totalCalories) + " Calories");
        distance.setText(String.format("%.2f", totaldistance) + " Miles");
        time.setText(String.format("%.2f", totalTime) + " Hours"); // float formatting

    }
    //    THIS IS AN IMPORTANT FUNCTION TO EXIT THE CURRENT INTENT AND GO BACK TO THE PREVIOUS ACTIVITY
    public void exitIntent() {
        finish();
    }

    //Clickable Icon Lets the user exit current intent/activity and return to the previous screen
    public void goBack(View v) {
        exitIntent();
    }
}