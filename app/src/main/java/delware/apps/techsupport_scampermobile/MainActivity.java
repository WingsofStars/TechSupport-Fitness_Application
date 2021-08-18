package delware.apps.techsupport_scampermobile;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    static String currentID;
    public static DBHandler databaseHandler;
    public Utils utils;
    static ArrayList<RunLog> RunLogs = new ArrayList<>();
    public Button btn;

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


        boolean isUserLoggedIn = prefs.getBoolean("LoggedIn", false); //Checks for user account if it doesn't exists, it creates a SP(Shared Preference) saying it Doesn't
        if(!isUserLoggedIn){
            currentID = String.valueOf(prefs.getInt("id", 0));
            openLoginScreen();
        }
        currentID = String.valueOf(prefs.getInt("id", 0));

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

        btn = (Button)loginPopup.findViewById(R.id.btnLogin);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ErrorText = "Invalid Information";
                Profile p;
                int id;
                TextView textViewException = loginPopup.findViewById(R.id.tvException);
                EditText givenUsernameView = loginPopup.findViewById(R.id.txtLoginUsername);
                EditText givenPasswordView = loginPopup.findViewById(R.id.txtLoginPassword);

                String givenUserName = givenUsernameView.getText().toString();
                String givenPassword = givenPasswordView.getText().toString();
                String encLoginPassword = utils.getSha256Hash(givenPassword);
                try {
                    p = databaseHandler.getUserByUsername(givenUserName, givenPassword);

                    id = p.getUserID();

                    if (id == -1) {
                        textViewException.setText(ErrorText);
                        return;
                    }
                }
                catch (Exception e){
                    textViewException.setText("ERROR");
                    return;
                }


                //When everything is logged in:
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("Username", givenUserName);
                editor.putInt("id", id);
                editor.putBoolean("LoggedIn", true);
                editor.apply();
                currentID = String.valueOf(prefs.getInt("id", 0));

                dialogue.dismiss();
            }
        });

    }



    public void goToCollectionScreen(View v){
        Intent goToCollection = new Intent(MainActivity.this, stickerWallScreen.class);
        startActivity(goToCollection);
    }
}