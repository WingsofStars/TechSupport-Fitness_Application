package delware.apps.techsupport_scampermobile;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class Game_Settings extends RivalGuts {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_settings);
        SeekBar sBar = findViewById(R.id.seekBar2);
        TextView textOutput = findViewById(R.id.textOut);

        sBar.setProgress(getDifficulty());
        textOutput.setText((getDifficulty()+1) + "");
        sBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setDifficulty(progress);
                textOutput.setText((getDifficulty()+1) + "");
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                textOutput.setText("Drag to set difficulty!");
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                textOutput.setText("Level set to " + (getDifficulty()+1));
            }
        });
    }
    public void pickRivalOne(View v){

    }
    public void pickRivalTwo(View v){

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
}