package delware.apps.techsupport_scampermobile;

import android.os.Bundle;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Tracking_Settings extends AppCompatActivity {
    TextView tv_latitude, tv_longitude,tv_altitude,tv_accuracy,tv_speed,tv_sensor,tv_updates,tv_address;
    Switch sw_locationupdates, sw_gps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking_settings);

        tv_latitude = findViewById(R.id.tv_latitude);
        tv_longitude = findViewById(R.id.tv_longitude);
        tv_altitude = findViewById(R.id.tv_accuracy);
        tv_accuracy = findViewById(R.id.tv_accuracy);
        tv_speed = findViewById(R.id.tv_speed);
        tv_sensor = findViewById(R.id.tv_sensor);
        tv_updates = findViewById(R.id.tv_updates);
        tv_address = findViewById(R.id.tv_address);
        sw_locationupdates = findViewById(R.id.sw_locationupdates);
        sw_gps = findViewById(R.id.sw_gps);

    }
}
