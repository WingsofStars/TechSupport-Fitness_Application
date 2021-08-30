package delware.apps.techsupport_scampermobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import java.util.ArrayList;

public class rivalScreen extends RivalGuts{

    public int num =0;
    private Profile p = MainActivity.databaseHandler.getUserByID(Integer.parseInt(MainActivity.currentID));
    private int goal = 5 * (difficulty+1);
    //DBHandler data = new DBHandler(context);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rival);
    }

    public void updateText(View v){
        ArrayList<RunLog> RunLogs = MainActivity.databaseHandler.getAllLogs("ALL");
        int distance = 0;
        for( int i = 0; i < MainActivity.databaseHandler.size("ALL"); i++) {
            distance += RunLogs.get(i).Distance;
        }
        switch (num){
            case 0:
                setText(p.getUserName() + ", good to see you!");
                num++;
                break;
            case 1:
                setText("I see your level right now is " + p.getLevel() + " with " + p.getXP() +" xp, keep it up!");
                num++;
                break;
            case 2:
                setText("I see you went a whole " + distance + " miles, amazing!");
                num++;
                break;
            case 3:
                if(goal > distance)
                    setText("I went " + goal + " miles, better luck next week.");
                else if(goal == distance)
                    setText("We both went " + distance + " miles this week, awesome!");
                else
                    setText("I only went " + goal + " miles this week, you win!");
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