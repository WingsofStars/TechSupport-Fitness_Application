package delware.apps.techsupport_scampermobile.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import delware.apps.techsupport_scampermobile.MainActivity;
import delware.apps.techsupport_scampermobile.R;

public class newUserScreen extends AppCompatActivity {
    public SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_screen);
        prefs = getSharedPreferences("prefs", MODE_PRIVATE);
    }

    public  void createUser(View v){
        SharedPreferences.Editor editor = prefs.edit(); // Editor for the SP's
        editor.putBoolean("Exists", true);// Marks the SP as the USer has been Created, so it won't show up on the next boot
        exitIntent();
        Toast.makeText(getApplicationContext(), "User Created", Toast.LENGTH_LONG).show();//Makes a dialogue box that says the user is created
    }


    public void exitIntent(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);//Exits current intent
        intent.putExtra("EXIT", true);
        startActivity(intent);
    }
}