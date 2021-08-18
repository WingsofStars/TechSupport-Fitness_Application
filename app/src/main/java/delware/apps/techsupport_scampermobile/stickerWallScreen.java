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
                TV.setText("1st");
                break;
            case R.id.A02:
                TV.setText("2nd");
                break;
            case R.id.A03:
                TV.setText("3rd");
                break;
            case R.id.A04:
                TV.setText("4th");
                break;
            case R.id.A11:
                TV.setText("5th");
                break;
            case R.id.A12:
                TV.setText("6th");
                break;
            case R.id.A13:
                TV.setText("7th");
                break;
            case R.id.A14:
                TV.setText("8th");
                break;
            case R.id.A21:
                TV.setText("9th");
                break;
            case R.id.A22:
                TV.setText("10th");
                break;
            case R.id.A23:
                TV.setText("11th");
                break;
            case R.id.A24:
                TV.setText("12th");
                break;
            case R.id.A31:
                TV.setText("13th");
                break;
            case R.id.A32:
                TV.setText("14th");
                break;
            case R.id.A33:
                TV.setText("15th");
                break;
            case R.id.A34:
                TV.setText("16th");
                break;
            case R.id.A41:
                TV.setText("17th");
                break;
            case R.id.A42:
                TV.setText("18th");
                break;
            case R.id.A43:
                TV.setText("19th");
                break;
            case R.id.A44:
                TV.setText("20th");
                break;
            default:
                TV.setText("Invalid sticker, tap to select another one...");
                break;
        }
    }
}
