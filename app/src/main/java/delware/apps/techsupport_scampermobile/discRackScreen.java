package delware.apps.techsupport_scampermobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class discRackScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_disc_rack);
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
}
