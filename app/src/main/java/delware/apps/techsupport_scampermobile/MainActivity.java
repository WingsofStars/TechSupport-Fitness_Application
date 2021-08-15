package delware.apps.techsupport_scampermobile;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.EditText;

import delware.apps.techsupport_scampermobile.Screens.newUserScreen;
import delware.apps.techsupport_scampermobile.Screens.settings;
import delware.apps.techsupport_scampermobile.Screens.trackingScreen;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    static SharedPreferences prefs; // uses small save files know as "Shared Preferences"
    public AlertDialog.Builder dBuilder;
    public AlertDialog dialogue;
    static TextView TV;
    static String currerntID;
    public static DBHandler databaseHandler;
    static ArrayList<RunLog> RunLogs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        databaseHandler = new DBHandler(MainActivity.this);
        TV = findViewById(R.id.xpBar);


        prefs = getSharedPreferences("prefs", MODE_PRIVATE);

//        boolean firstStart = prefs.getBoolean("isThisFirstStart", true);// Searches for a Shared  Preferences Value, if one doesn't exists, it is created with the default value of true
//
//        if (firstStart){
//
//            SharedPreferences.Editor editor = prefs.edit(); // Makes an editor to change the values of a shared Preferences
//            editor.putBoolean("isThisFirstStart", false);//Changes Value to False
//            editor.apply();
//        }


        boolean isUserLoggedIn = prefs.getBoolean("LoggedIn", false); //checks for user account if it doesn't exists, it creates a SP(Shared Preference) saying it Doesn't
        if(!isUserLoggedIn){
            openLoginScreen();
        }
        currerntID = String.valueOf(prefs.getInt("id", 0));
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
    public void goToNewUserScreen(View v){
        Intent goToCreateUser = new Intent(getApplicationContext(), newUserScreen.class);
        startActivity(goToCreateUser); // Got to the create user Screen
    }


    public void openLoginScreen(){
        dBuilder = new AlertDialog.Builder(this);
        final View loginPopup = getLayoutInflater().inflate(R.layout.loginpopup, null);
        dBuilder.setView(loginPopup);
        dialogue = dBuilder.create();
        dialogue.show();
    }

    public void attemptLogin(View V){
        EditText givenUsernameView = findViewById(R.id.userNameText);
        EditText givenPasswordView = findViewById(R.id.passwordText);

        String givenUserName = givenUsernameView.getText().toString();
        String givenPassword = givenPasswordView.getText().toString();

        //Run These given values through a sql query

        int id = 0;


        //When everything is logged in:
//        SharedPreferences.Editor editor = prefs.edit();
//        editor.putString("Username", givenUserName);
//        editor.putInt("UserId", id);
//        editor.putString("Password", givenPassword);//needs to be scrambled
//        editor.putBoolean("LoggedIn", true);
    }

    public void goToCollectionScreen(View v){
        Intent goToCollection = new Intent(MainActivity.this, stickerWallScreen.class);
        startActivity(goToCollection);
    }
}