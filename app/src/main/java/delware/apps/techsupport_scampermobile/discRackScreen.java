package delware.apps.techsupport_scampermobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class discRackScreen extends AppCompatActivity {
    private TextView TV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_disc_rack);
        TV = findViewById(R.id.DescriptionText2);
        TV.setText("Tap on a disc to learn more...");
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

    public void goToStickerWallScreen(View v){
        Intent goToStickerWall = new Intent(discRackScreen.this, stickerWallScreen.class);
        startActivity(goToStickerWall);
    }

    public void showDiscDescription(View v) {
        switch(v.getId()) {
            case R.id.D01:
                TV.setText("Disc 1 description...");
                break;
            case R.id.D02:
                TV.setText("Disc 2 description...");
                break;
            case R.id.D11:
                TV.setText("Disc 3 description...");
                break;
            case R.id.D12:
                TV.setText("Disc 4 description...");
                break;
            default:
                TV.setText("Invalid disc, tap to select another one...");
                break;
        }
    }
}
