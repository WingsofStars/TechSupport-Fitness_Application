package delware.apps.techsupport_scampermobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;

import java.util.ArrayList;
import java.util.Arrays;

import delware.apps.techsupport_scampermobile.Dev_Settings;
import delware.apps.techsupport_scampermobile.Game_Settings;
import delware.apps.techsupport_scampermobile.MainActivity;
import delware.apps.techsupport_scampermobile.Profile_Settings;
import delware.apps.techsupport_scampermobile.R;
import delware.apps.techsupport_scampermobile.SimpleListAdapter;
import delware.apps.techsupport_scampermobile.Statistics;

public class settings extends AppCompatActivity {

    public static final String PROFILE = "Profile Settings";
    public static final String GAME = "GAME Settings";
    public static final String STATISTICS = "Statistics";
    public static final String DEV = "Dev Settings";
    public static final String GPS = "Tracking Settings";
    public ListView subMenu;
    public ArrayList<String> submenuItems = new ArrayList<>();
    AdView bannerAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        subMenu = findViewById(R.id.subMenu);
        submenuItems.add(PROFILE);
//        submenuItems.add(GAME);
        submenuItems.add(STATISTICS);
//        submenuItems.add(DEV);
//        submenuItems.add(GPS);
        SimpleListAdapter adapter = new SimpleListAdapter(this, R.layout.row , submenuItems);
        subMenu.setAdapter(adapter);

        subMenu.setOnItemClickListener( new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (id == submenuItems.indexOf(PROFILE)){
                    Intent goToProfileSettings = new Intent(getApplicationContext(), Profile_Settings.class);
                    startActivity(goToProfileSettings);
                }
                else if(id == submenuItems.indexOf(GAME)){
                    Intent goToGame = new Intent(getApplicationContext(), Game_Settings.class);
                    startActivity(goToGame);
                }
                else if(id == submenuItems.indexOf(STATISTICS)){
                    Intent goToStats = new Intent(getApplicationContext(), Statistics.class);
                    startActivity(goToStats);
                }
                else if(id == submenuItems.indexOf(DEV)){
                    Intent goToDev = new Intent(getApplicationContext(), Dev_Settings.class);
                    startActivity(goToDev);
                }
                else if(id == submenuItems.indexOf(GPS)){
                    Intent goToTracking = new Intent(getApplicationContext(), Tracking_Settings.class);
                    startActivity(goToTracking);

                }

//                switch (adapter.getItem(position)){
//                    case 1:
//
//                        break;
//                    case 2:
//                        break;
//                }
            }
        });

        bannerAd = findViewById(R.id.adView);

        MobileAds.setRequestConfiguration(new RequestConfiguration.Builder().setTestDeviceIds(Arrays.asList("17DC790ACE65C13AC382E329E2098CE0"))
                .build());

        // Gets the ad view defined in layout/ad_fragment.xml with ad unit ID set in
        // values/strings.xml.

        // Create an ad request.
        AdRequest adRequest = new AdRequest.Builder().build();

        // Start loading the ad in the background.
        bannerAd.loadAd(adRequest);

    }

//    public void SaveAll(){
//        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
//        SharedPreferences.Editor editor = prefs.edit();
//
//        //editor.put("name", value);
//
//        editor.apply();
//    }


//    THIS IS AN IMPORTANT FUNCTION TO EXIT THE CURRENT INTENT AND GO BACK TO THE PREVIOUS ACTIVITY
    public void exitIntent(){
//        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);//Exits current intent
//        intent.putExtra("EXIT", true);
//        startActivity(intent);
        finish();
        if(Integer.parseInt(MainActivity.currentID) != 0) {
            MainActivity.xpSystem.xpCheck(0, MainActivity.databaseHandler.getUserByID(Integer.parseInt(MainActivity.currentID)));
        } else {
            MainActivity.TVXP.setText("0 / 0 | Level 0");
        }
    }

    //Clickable Icon Lets the user exit current intent/activity and return to the previous screen
    public void goBack(View v){
        exitIntent();
    }
}