package delware.apps.techsupport_scampermobile.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import delware.apps.techsupport_scampermobile.MainActivity;
import delware.apps.techsupport_scampermobile.R;

public class settings extends AppCompatActivity {

    public static final String PROFILE = "Profile Settings";
    public static final String GAME = "GAME Settings";
    public static final String STATISTICS = "Statistics";
    public static final String DEV = "Dev Settings";
    public ListView subMenu;
    public ArrayList<String> submenuItems = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        subMenu = findViewById(R.id.subMenu);
        submenuItems.add(PROFILE);
        submenuItems.add(GAME);
        submenuItems.add(STATISTICS);
        submenuItems.add(DEV);
        SimpleListAdapter adapter = new SimpleListAdapter(this, R.layout.row , submenuItems);
        subMenu.setAdapter(adapter);

        subMenu.setOnItemClickListener( new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (id == submenuItems.indexOf(PROFILE)){
                    Intent goToProfileSettings = new Intent(getApplicationContext(), Profile_Settings.class);
                    startActivity(goToProfileSettings);
                }
                else if(id == submenuItems.indexOf(GAME)){
                    Intent goToGame = new Intent(getApplicationContext(),Game_Settings.class);
                    startActivity(goToGame);
                }
                else if(id == submenuItems.indexOf(STATISTICS)){
                    Intent goToStats = new Intent(getApplicationContext(),Statistics.class);
                    startActivity(goToStats);
                }
                else if(id == submenuItems.indexOf(DEV)){
                    Intent goToDev = new Intent(getApplicationContext(),Dev_Settings.class);
                    startActivity(goToDev);
                }

//                switch (adapter.getItem(position)){
//                    case 1:
//
//                        break;
//                    case 2:
//                        break;
//                }
            }
        });

    }

//    public void SaveAll(){
//        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
//        SharedPreferences.Editor editor = prefs.edit();
//
//        //editor.put("name", value);
//
//        editor.apply();
//    }


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
}