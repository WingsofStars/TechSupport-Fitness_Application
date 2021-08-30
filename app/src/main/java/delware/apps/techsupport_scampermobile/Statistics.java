package delware.apps.techsupport_scampermobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class Statistics extends AppCompatActivity {
     TextView calories;
    TextView distance;
    TextView time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        calories = findViewById(R.id.calories);
        time = findViewById(R.id.time);
        distance = findViewById(R.id.distance);
        setStatsScreen();
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
        Intent intent = new Intent(getApplicationContext(), settings.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);//Exits current intent
        intent.putExtra("EXIT", true);
        startActivity(intent);
    }

    //Clickable Icon Lets the user exit current intent/activity and return to the previous screen
    public void goBack(View v) {
        exitIntent();
    }
}