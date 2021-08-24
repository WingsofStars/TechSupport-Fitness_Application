package delware.apps.techsupport_scampermobile;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class cardioDisplayScreen extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

int Position1;
int Position2;

    //    THIS IS AN IMPORTANT FUNCTION TO EXIT THE CURRENT INTENT AND GO BACK TO THE PREVIOUS ACTIVITY
    public void exitIntent(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);//Exits current intent
        intent.putExtra("EXIT", true);
        startActivity(intent);
    }

    //Clickable Icon Lets the user exit current intent/activity and return to the previous screen
    public void goBack(View v){
        exitIntent();
    }



    private AlertDialog.Builder dialogeBuilder;
    private  AlertDialog dialog;
    private EditText minutes, hours, distance, calories, date;
    private Button enter, cancel;
    public SwipeRefreshLayout pulltoRefresh;
    public ListView listView;
    private Spinner type;

    public Spinner typeSelector1;
    public Spinner typeSelector2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardio_display_screen);
        typeSelector1 = findViewById(R.id.cardioType);
        typeSelector2 = findViewById(R.id.Type);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.CardioType, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        typeSelector1.setAdapter(adapter);


        typeSelector1.setOnItemSelectedListener(this);


        listView = (ListView) findViewById(R.id.listView);


        Refresh();


        pulltoRefresh = findViewById(R.id.pulltoRefresh);
        pulltoRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Refresh();
                pulltoRefresh.setRefreshing(false);
            }
        });
        //Log Modification
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView IdTxt = view.findViewById(R.id.IDtxt);
                id = Integer.parseInt(IdTxt.getText().toString());
                Update((int) id);
            }
        });
    }

    //Spinner Selection
    @Override

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent == typeSelector1) {
            listView.setAdapter(null);
            Position1 = position;

            if (MainActivity.databaseHandler.size(parent.getItemAtPosition(position).toString()) >= 1) {
                //runsymbol.setImageResource(R.drawable.runempty);
                //emptymessage.setText("");

                Refresh();
                Toast.makeText(this, parent.getItemAtPosition(position).toString() + " selected", Toast.LENGTH_LONG).show();

            }
        }
        else {
            Position2 = position;
        }

        }




    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void createNewLog(View view){//Creates a popup window to take inputs to Log

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.CardioTypeSelection, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        SimpleDateFormat formater = new SimpleDateFormat("MM/dd/yyyy");

        String Date = formater.format(Calendar.getInstance().getTime());

        dialogeBuilder = new AlertDialog.Builder(this);
        final View addNewLogPopup = getLayoutInflater().inflate(R.layout.addlogpopup, null);
        distance = (EditText) addNewLogPopup.findViewById(R.id.Distance);
        calories = addNewLogPopup.findViewById(R.id.Calories);
        minutes = addNewLogPopup.findViewById(R.id.Minutes);
        hours = addNewLogPopup.findViewById(R.id.Hours);
        date = addNewLogPopup.findViewById(R.id.Date);
        type = addNewLogPopup.findViewById(R.id.Type);

        TextView Error = addNewLogPopup.findViewById(R.id.Error);

        enter = (Button)addNewLogPopup.findViewById(R.id.Save);
        cancel = (Button)addNewLogPopup.findViewById(R.id.Cancel);



        dialogeBuilder.setView(addNewLogPopup);
        dialog = dialogeBuilder.create();
        dialog.show();
        type.setAdapter(adapter);
        type.setOnItemSelectedListener(this);

        date.setText(Date);//show Date

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check Data


<<<<<<< HEAD


                try {
                    int Hours;
                    float Distance = Float.valueOf(distance.getText().toString());
                    int Calories = Integer.valueOf(calories.getText().toString());
                    float Minutes = Float.valueOf(minutes.getText().toString());
                    if(hours.getText().toString().equals("")){
                        Hours = 0;
                    }
                    else {
                        Hours = Integer.valueOf(hours.getText().toString());
                    }
=======
                int Hours;
                float Distance = Float.valueOf(distance.getText().toString());
                int Calories = Integer.valueOf(calories.getText().toString());
                float Minutes = Float.valueOf(minutes.getText().toString());
                if(hours.getText().toString().equals("")){
                    Hours = 0;
                }
                else {
                    Hours = Integer.valueOf(hours.getText().toString());
                }

                try {
>>>>>>> main
                    if (Distance == 0 ){
                        Error.setText("Zero is not an acceptable entry for Distance.");
                        return;
                    }

                    String Type = type.getItemAtPosition(Position2).toString();
                    String Date = date.getText().toString();


                    //save and create instance
                    RunLog runLog = new RunLog(Distance, Hours, Minutes, Calories, Date, Type);

                    MainActivity.databaseHandler.addLog(runLog);


                    //MainActivity.Logs.Log.add(runLog);


                    Toast.makeText(getApplicationContext(), "Data Saved", Toast.LENGTH_LONG).show();
                    Error.setText("");
                    dialog.dismiss();
                    Refresh();

                }catch (Exception e){
                    Error.setText("Your Information is Either Incomplete or Faulty");
                }
                //XP Tie in
<<<<<<< HEAD
                //Todo update xp formula
                MainActivity.xpSystem.xpCheck(25, MainActivity.databaseHandler.getUserByID(Integer.parseInt(MainActivity.currentID)));
=======
                int xpAddition = (int) (((Hours * 60) + Minutes)/Distance);
                System.out.println("Earned xp: " + xpAddition);
                MainActivity.xpSystem.xpCheck(xpAddition, MainActivity.databaseHandler.getUserByID(Integer.parseInt(MainActivity.currentID)));
>>>>>>> main


            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    };

//    public void goBack(View v){
//        MainActivity.RunLogs = MainActivity.databaseHandler.getAllLogs();
//        //MainActivity.SetStartScreen();
//        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);//Goes back to the original Page
//        intent.putExtra("EXIT", true);//Doesn't Open a new intent, actual goes gack to the original intent
//        startActivity(intent);
//    }

    public void Refresh() {
//        if(MainActivity.Logs.Log.size() > MainActivity.LogsforDisplay.size()){
//        MainActivity.updateDisplayLog();}
//        ImageView runsymbol = findViewById(R.id.runsymboll);
//        runsymbol.setImageResource(R.drawable.runsymbol);
//        TextView emptymessage = findViewById(R.id.message);
//        emptymessage.setText(R.string.newlogmessage);
        if(MainActivity.databaseHandler.size(typeSelector1.getItemAtPosition(Position1).toString()) >= 1) {
            //runsymbol.setImageResource(R.drawable.runempty);
            //emptymessage.setText("");

            listView.setAdapter(null);

            LogAdapter adapter = new LogAdapter(this, R.layout.adapter_view_layout, MainActivity.databaseHandler.getAllLogs(typeSelector1.getItemAtPosition(Position1).toString()));
            listView.setAdapter(adapter);
        }


    }

    public void Update(int id){
        dialogeBuilder = new AlertDialog.Builder(this);
        final View updateLog = getLayoutInflater().inflate(R.layout.update_log_popup, null);
        Button Save;
        Button Cancel;
        Button Delete;

        EditText distance;
        EditText calories;
        EditText hours;
        EditText minutes;
        EditText date;


        Save = (Button)updateLog.findViewById(R.id.Save);
        Cancel = (Button)updateLog.findViewById(R.id.Cancel);
        Delete = (Button)updateLog.findViewById(R.id.Delete);
        calories = (EditText)updateLog.findViewById(R.id.Calories);
        distance = (EditText) updateLog.findViewById(R.id.Distance);
        hours = (EditText) updateLog.findViewById(R.id.Hours);
        minutes = (EditText) updateLog.findViewById(R.id.Minutes);
        date = (EditText) updateLog.findViewById(R.id.Date);
        RunLog currentLog = MainActivity.databaseHandler.getLogById(id);


        calories.setText(String.valueOf(currentLog.getCalories()));
        distance.setText(String.valueOf(currentLog.getDistance()));
        hours.setText(String.valueOf(currentLog.getHours()));
        minutes.setText(String.valueOf(currentLog.getMinutes()));
        date.setText(String.valueOf(currentLog.getDate()));




        dialogeBuilder.setView(updateLog);
        dialog = dialogeBuilder.create();
        dialog.show();
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.databaseHandler.delete(id);
                dialog.dismiss();
                Refresh();
            }
        });
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.databaseHandler.updateLogs(id, Integer.parseInt(hours.getText().toString()), Integer.parseInt(minutes.getText().toString()), Float.valueOf(distance.getText().toString()), Integer.parseInt(calories.getText().toString()));
                dialog.dismiss();
                Refresh();
            }
        });
    }


}