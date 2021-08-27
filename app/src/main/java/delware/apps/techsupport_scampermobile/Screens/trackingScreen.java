package delware.apps.techsupport_scampermobile.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import delware.apps.techsupport_scampermobile.MainActivity;
import delware.apps.techsupport_scampermobile.NavigationService;
import delware.apps.techsupport_scampermobile.R;
import delware.apps.techsupport_scampermobile.Tracking_Settings;

public class trackingScreen extends AppCompatActivity {
    public static final String INTENT_START_NAME = "inputStart";
    public Tracking_Settings trackingSettings;
    public enum State
    {
        running,
        paused,
        stopped
    }
    public Chronometer timetxt;
    public TextView distancetxt;
    public TextView speedtxt;
    public ImageView pausebtn;
    public ImageView playbtn;
    public ImageView stopbtn;
    public int playBtnPresses = 0;
    private boolean running = false;
    private long pauseOffset;
    public long totaltime; //in seconds
    public static State state;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking_screen);
        timetxt = findViewById(R.id.tvTime);
        distancetxt = findViewById(R.id.tvDistance);
        speedtxt = findViewById(R.id.tvSpeed);
        pausebtn = findViewById(R.id.btnPause);
        playbtn = findViewById(R.id.btnPlay);
        stopbtn = findViewById(R.id.btnStop);

        stopbtn.setEnabled(false);
        pausebtn.setEnabled(false);
        trackingSettings = new Tracking_Settings();
    }


    //    THIS IS AN IMPORTANT FUNCTION TO EXIT THE CURRENT INTENT AND GO BACK TO THE PREVIOUS ACTIVITY
    public void exitIntent() {
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
            timetxt.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            timetxt.start();
            running = true;
            trackingSettings.startLocationUpdates();

        }
        else {
            //resume
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

    public void StopRun(View v){
        //STOP EVERYTHING
        pausebtn.setEnabled(false);
        pausebtn.setVisibility(View.INVISIBLE);
        stopbtn.setEnabled(false);
        stopbtn.setVisibility(View.INVISIBLE);

        playbtn.setVisibility(View.VISIBLE);
        playbtn.setEnabled(true);
        timetxt.setBase(SystemClock.elapsedRealtime());
        pauseOffset=0;
        running = false;
        state = State.stopped;
        getRunIntent(state);
        //resets presses so you can restart the run
        totaltime = SystemClock.elapsedRealtime() - timetxt.getBase();
        System.out.println(totaltime);
        trackingSettings.stopLocationUpdates();


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

}