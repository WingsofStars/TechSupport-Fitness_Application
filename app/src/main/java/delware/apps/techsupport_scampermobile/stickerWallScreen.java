package delware.apps.techsupport_scampermobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class stickerWallScreen extends AppCompatActivity {
    private TextView TV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_sticker_wall);
        TV = findViewById(R.id.DescriptionText1);
        TV.setText("Tap on a sticker to learn more...");
    }

    public void exitIntent(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);//Exits current intent
        intent.putExtra("EXIT", true);
        startActivity(intent);
    }

    public void goBack(View v){
        exitIntent();
    }

    public void goToDiscRackScreen(View v){
        Intent goToDiscRack = new Intent(stickerWallScreen.this, discRackScreen.class);
        startActivity(goToDiscRack);
    }

    public void showStickerDescription(View v) {
        switch(v.getId()) {
            case R.id.A01:
                TV.setText("First log in, welcome to the app...");
                break;
            case R.id.A02:
                TV.setText("Track or log your first activity...");
                break;
            case R.id.A03:
                TV.setText("Beat your rival for the first time, you know you can do it...");
                break;
            case R.id.A04:
                TV.setText("Achieve level 2...");
                break;
            case R.id.A11:
                TV.setText("Achieve level 4...");
                break;
            case R.id.A12:
                TV.setText("Achieve level 6...");
                break;
            case R.id.A13:
                TV.setText("Achieve level 8...");
                break;
            case R.id.A14:
                TV.setText("Achieve level 10...");
                break;
            case R.id.A21:
                TV.setText("Achieve level 14...");
                break;
            case R.id.A22:
                TV.setText("Achieve level 18...");
                break;
            case R.id.A23:
                TV.setText("Achieve the maximum level, what an awesome job, keep going, keep pushing yourself...");
                break;
            case R.id.A24:
                TV.setText("What a week, track or log your activity for a full week...");
                break;
            case R.id.A31:
                TV.setText("Travel a total distance of 5 miles...");
                break;
            case R.id.A32:
                TV.setText("Travel a total distance of 10 miles...");
                break;
            case R.id.A33:
                TV.setText("Travel a total distance of 15 miles...");
                break;
            case R.id.A34:
                TV.setText("Travel a total distance of 20 miles...");
                break;
            case R.id.A41:
                TV.setText("Burn a total of 1,500 calories...");
                break;
            case R.id.A42:
                TV.setText("Burn a total of 3,000 calories...");
                break;
            case R.id.A43:
                TV.setText("Exercise for a total of 5 hours...");
                break;
            case R.id.A44:
                TV.setText("Exercise for a total of 10 hours...");
                break;
            default:
                TV.setText("Invalid sticker, tap to select another one...");
                break;
        }
    }
}
