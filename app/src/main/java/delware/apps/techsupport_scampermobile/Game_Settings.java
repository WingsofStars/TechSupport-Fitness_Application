package delware.apps.techsupport_scampermobile;

import androidx.appcompat.app.AppCompatActivity;
<<<<<<< HEAD
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class Game_Settings extends RivalGuts {
=======

import android.os.Bundle;

public class Game_Settings extends AppCompatActivity {
>>>>>>> main

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_settings);
<<<<<<< HEAD
        SeekBar sBar = (SeekBar) findViewById(R.id.seekBar2);
        TextView textOutput = findViewById(R.id.textOut);
        sBar.setProgress(difficulty);
        sBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                difficulty = progress + 1;
                textOutput.setText("Difficulty level " + difficulty);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                textOutput.setText("Drag to set difficulty!");
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                textOutput.setText("Level set to " + difficulty);
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

=======
    }
>>>>>>> main
}