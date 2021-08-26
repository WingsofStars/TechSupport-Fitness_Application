package delware.apps.techsupport_scampermobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class rivalScreen extends RivalGuts{

    public int num =0;
    public int difficulty = 0;
    Profile p = new Profile(10,"cj","123@","salt",5.3,120.5,19,"male",0,0);
    RunLog r = new RunLog(3,2,12,200,"8/19/2021","running");
    //DBHandler data = new DBHandler();
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
                setText("I see your level right now is " + p.getLevel() + " with " + p.getXP() +", keep it up!");
                num++;
                break;
            case 2:
                setText("I see you went " + r.getCardioType() + " today! A whole " + (int)r.getDistance() + " miles, amazing!");
                num++;
                break;
            case 3:
                setText("I went " + r.getCardioType() + " this week for " + (int)(r.getDistance()+1) + " miles, better luck next week.");
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

            int num = (int) (Math.random() *6)+1;
            switch(num){
                case 1:
                    setText("There is only one thing better then whey protein, our savor jesus christ bro!");
                    break;
                case 2:
                    setText("yeah!");
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