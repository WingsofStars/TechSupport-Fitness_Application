package delware.apps.techsupport_scampermobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
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