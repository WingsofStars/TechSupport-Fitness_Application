package delware.apps.techsupport_scampermobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    public SharedPreferences prefs; // uses small save files know as "Shared Prefrences"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);


        prefs = getSharedPreferences("prefs", MODE_PRIVATE);

//        boolean firstStart = prefs.getBoolean("isThisFirstStart", true);// Searches for a Shared  Preferences Value, if one doesn't exists, it is created with the default value of true
//
//        if (firstStart){
//
//            SharedPreferences.Editor editor = prefs.edit(); // Makes an editor to change the values of a shared Preferences
//            editor.putBoolean("isThisFirstStart", false);//Changes Value to False
//            editor.apply();
//        }


        boolean doesUserExist = prefs.getBoolean("Exists", false); //Checks for user account if it doesn't exists, it creates a SP(Shared Preference) saying it Doesn't
        if(!doesUserExist){
            Intent goToCreateUser = new Intent(getApplicationContext(), newUserScreen.class);
            startActivity(goToCreateUser); // Got to the create user Screen
        }
    }


    public void goToSettings(View v){
        Intent goToSettings = new Intent(MainActivity.this, settings.class);
        startActivity(goToSettings);
    }

    public void goToCardioDisplay(View v){
        Intent goToSettings = new Intent(MainActivity.this, cardioDisplayScreen.class);
        startActivity(goToSettings);
    }

    public void goToTrackingScreen(View v){
        Intent goToSettings = new Intent(MainActivity.this, trackingScreen.class);
        startActivity(goToSettings);
    }
}