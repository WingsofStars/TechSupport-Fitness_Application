package delware.apps.techsupport_scampermobile;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

import delware.apps.techsupport_scampermobile.Screens.newUserScreen;
import delware.apps.techsupport_scampermobile.Screens.trackingScreen;

public class MainActivity extends AppCompatActivity implements
SharedPreferences.OnSharedPreferenceChangeListener {

    static SharedPreferences prefs; // uses small save files know as "Shared Preferences"
    public AlertDialog.Builder dBuilder;
    public static AlertDialog dialogue;
    static TextView TVXP;
    public static String currentID;
    //    MediaPlayer mp;
    public static DBHandler databaseHandler;
    public static xpSystem xpSystem;
    static ArrayList<RunLog> RunLogs = new ArrayList<>();
    public Button btn;
    float x1, y1, x2, y2;
    public static boolean isFromMain;
    ImageView infoBtn;
    public static final String CHANNEL_ID = "foregroundServiceChannel";
    public static TextView Time, Distance, Calories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        Time = findViewById(R.id.time);
        Distance = findViewById(R.id.distance);
        Calories = findViewById(R.id.calories);

        infoBtn=findViewById(R.id.btnInfo);

        databaseHandler = new DBHandler(MainActivity.this);
        xpSystem = new xpSystem();
        TVXP = findViewById(R.id.xpBar);
//        mp = MediaPlayer.create(this, R.raw.duckquack);


        prefs = getSharedPreferences("prefs", MODE_PRIVATE);

        createNotificationChannel();


        boolean isUserLoggedIn = prefs.getBoolean("LoggedIn", false); //Checks for user account if it doesn't exists, it creates a SP(Shared Preference) saying it Doesn't
        if (!isUserLoggedIn) {
            currentID = String.valueOf(prefs.getInt("id", 0));
            openLoginScreen();
        }
        currentID = String.valueOf(prefs.getInt("id", 0));

        if(isUserLoggedIn) {
            Profile p;
            p = databaseHandler.getUserByID(Integer.parseInt(MainActivity.currentID));
            CalorieCalculator.setProfile(p);
            xpSystem.xpCheck(0, p);
        }else {
            TVXP.setText("0 / 0 | Level 0");
        }

        infoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInfoPopup();
            }
        });
//        boolean firstStart = prefs.getBoolean("isThisFirstStart", true);// Searches for a Shared  Preferences Value, if one doesn't exists, it is created with the default value of true
////
//        if (firstStart){
////
//            showInfoPopup();
//            SharedPreferences.Editor editor = prefs.edit(); // Makes an editor to change the values of a shared Preferences
//            editor.putBoolean("isThisFirstStart", false);//Changes Value to False
//            editor.apply();
//        }

        setWeeklyStats();

    }

    public void goToSettings(View v) {
        Intent goToSettings = new Intent(MainActivity.this, settings.class);
        startActivity(goToSettings);
    }

    public void goToCardioDisplay(View v) {
        Intent goToSettings = new Intent(MainActivity.this, cardioDisplayScreen.class);
        startActivity(goToSettings);
    }

    public void goToTrackingScreen(View v) {
        Intent goToSettings = new Intent(MainActivity.this, trackingScreen.class);
        startActivity(goToSettings);
    }

    public void goToNewUserScreen(View v) {
        Intent goToCreateUser = new Intent(getApplicationContext(), newUserScreen.class);
        startActivity(goToCreateUser); // Got to the create user Screen
    }


    public void openLoginScreen() {
        isFromMain = true;
        dBuilder = new AlertDialog.Builder(this);
        final View loginPopup = getLayoutInflater().inflate(R.layout.loginpopup, null);
        dBuilder.setView(loginPopup);
        dialogue = dBuilder.create();
        dialogue.show();

        btn = (Button) loginPopup.findViewById(R.id.btnLogin);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isValidPassword = false;
                String ErrorText = "Invalid Information";
                Profile p;
                int id;
                TextView textViewException = loginPopup.findViewById(R.id.tvException);
                EditText givenUsernameView = loginPopup.findViewById(R.id.txtLoginUsername);
                EditText givenPasswordView = loginPopup.findViewById(R.id.txtLoginPassword);

                String givenUserName = givenUsernameView.getText().toString();
                String givenPassword = givenPasswordView.getText().toString();

                try {
//                    p = databaseHandler.getUserByUsername(givenUserName, givenPassword);
//
//                    id = p.getUserID();
//
//                    if (id == -1) {
//                        textViewException.setText(ErrorText);
//                        return;
//                    }
                    p = databaseHandler.getUserByUsername(givenUserName);
                    isValidPassword = PasswordUtils.verifyUserPassword(givenPassword, p.getPassword(), p.getSalt());
                    if (isValidPassword) {
                        id = p.getUserID();
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("Username", givenUserName);
                        editor.putInt("id", id);
                        editor.putBoolean("LoggedIn", true);
                        editor.apply();
                        currentID = String.valueOf(prefs.getInt("id", 0));

                        dialogue.dismiss();
                    } else {
                        textViewException.setText("Provided Username or Password is invalid.");
                    }
                } catch (Exception e) {
                    textViewException.setText("ERROR");
                    return;
                }


                //When everything is logged in:
                xpSystem.xpCheck(0, p);
            }
        });

    }

    public void goToCollectionScreen(View v) {
        Intent goToCollection = new Intent(MainActivity.this, stickerWallScreen.class);
        startActivity(goToCollection);
    }

    public void closePopUp(View v) {
        dialogue.dismiss();
    }

//    public void playSound(View v) {
//        mp.start();
//    }


    public boolean onTouchEvent(MotionEvent touchEvent) {
        switch (touchEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = touchEvent.getX();
                y1 = touchEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = touchEvent.getX();
                y2 = touchEvent.getY();
                if (x1 > x2) {
                    Intent i = new Intent(MainActivity.this, stickerWallScreen.class);
                    startActivity(i);
//            }else if(x1 greater than x2){
//                Intent i = new Intent(MainActivity.this, SwipeRight.class);
//                startActivity(i);
//            }
                }
                break;
            }
            return false;

    }

    public void showInfoPopup(){
        ImageView exit;

        dBuilder = new AlertDialog.Builder(this);
        final View welcomePopup = getLayoutInflater().inflate(R.layout.information_popup, null);
        dBuilder.setView(welcomePopup);
        dialogue = dBuilder.create();

        exit =  welcomePopup.findViewById(R.id.exit);
        dialogue.show();
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogue.dismiss();
            }
        });
    }
    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID, "Location Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

    }
    public static void setWeeklyStats(){
        RunLogs = databaseHandler.getAllLogs("All");
        ArrayList<RunLog> last7DaysLog = new ArrayList<>();
        long DAY_IN_MS = 1000 * 60 * 60 * 24;

        Date todaysDate = new Date();
        Date previousWeekDate = new Date(todaysDate.getTime() - (7*DAY_IN_MS));

        for (int i = 0; i < RunLogs.size(); i++) {
            try {
                Date date = new SimpleDateFormat("MM/dd/yyyy").parse(RunLogs.get(i).date);
                if(date.after(previousWeekDate) && date.before(new Date(todaysDate.getTime() + (1*DAY_IN_MS)))) {
                    last7DaysLog.add(RunLogs.get(i));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        float totaldistance = 0f;
        int totalCalories = 0;
        float totalTime = 0f;

        for( int i = 0; i < last7DaysLog.size(); i++) {
            totalCalories += last7DaysLog.get(i).calories;
            totaldistance += last7DaysLog.get(i).Distance;
            totalTime +=( (last7DaysLog.get(i).Hours) + (RunLogs.get(i).Minutes/60));
        }
        Calories.setText(String.valueOf(totalCalories) + " Calories");
        Distance.setText(String.format("%.2f", totaldistance) + " Miles");
        Time.setText(String.format("%.2f", totalTime) + " Hours"); // float formatting
    }

}