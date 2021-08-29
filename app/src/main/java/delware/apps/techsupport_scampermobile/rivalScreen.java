package delware.apps.techsupport_scampermobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.text.DecimalFormat;

public class rivalScreen extends RivalGuts{

    public int num = 0;
    public int difficulty = 0;
    DecimalFormat df = new DecimalFormat("0.00");
    Profile p = MainActivity.databaseHandler.getUserByID(Integer.parseInt(MainActivity.currentID));
    ArrayList<RunLog> logs = MainActivity.databaseHandler.getAllLogs("All");
    RunLog mostCurrent = logs.get(0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rival);
    }

    public void updateText(View v){
        switch (num){
            case 0:
                setText(p.getUserName() + ", good to see you!");
                num++;
                break;
            case 1:
                setText("I see you are at level " + p.getLevel()  + " right now, keep it up!");
                num++;
                break;
            case 2:
                setText("I see you went " + mostCurrent.getCardioType() + " today! A whole " + df.format((double)mostCurrent.getDistance()) + " miles, amazing!");
                num++;
                break;
            case 3:
                setText("I went " + mostCurrent.getCardioType() + " this week for " + df.format((double)(mostCurrent.getDistance()+ 0.4)) + " miles, better luck next week.");
                num++;
                break;
            case 4:
                setText("Good talk, see you again soon!");
                num++;
                break;
            case 5:
                randomSaying();
                break;
        }


    }

    public void randomSaying(){

            int num = (int) (Math.random() * 10)+1;
            switch(num){
                case 1:
                    setText("There is only one thing better then whey protein, our savor jesus christ bro!");
                    break;
                case 2:
                    setText("Yeah!");
                    break;
                case 3:
                    setText("Whey protein!");
                    break;
                case 4:
                    setText("Looking good dude!");
                    break;
                case 5:
                    setText("More whey protein!");
                    break;
                case 6:
                    setText("God is good.");
                    break;
                case 7:
                    setText("Keep pushing dude!");
                    break;
                case 8:
                    setText("Make your dreams come true and live on bro!");
                    break;
                case 9:
                    setText("Get out there, move, exercise, explore your boundaries bro!");
                    break;
                case 10:
                    setText("Got to talk to you later bro, got to hit the gym and pump the iron!");
                    break;
            }
    }
    public void setText(String text){
        TextView textOutput = findViewById(R.id.textOutput);
        textOutput.setText(text);
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