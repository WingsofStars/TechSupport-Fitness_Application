package delware.apps.techsupport_scampermobile.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import delware.apps.techsupport_scampermobile.DBHandler;
import delware.apps.techsupport_scampermobile.MainActivity;
import delware.apps.techsupport_scampermobile.Profile;
import delware.apps.techsupport_scampermobile.R;
import delware.apps.techsupport_scampermobile.RegexRunner;
import delware.apps.techsupport_scampermobile.Utils;

public class newUserScreen extends AppCompatActivity {
    public SharedPreferences prefs;
    public static DBHandler db;
    public Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_screen);
        prefs = getSharedPreferences("prefs", MODE_PRIVATE);

        db = new DBHandler(this);
    }
    //gets text from text box and runs it against regex and passes it to Profile
    public void createUser(View v){
        // Getting Variables from the EditTexts
        TextView txtException = findViewById(R.id.txtViewException);
        EditText txtUsername = findViewById(R.id.txtUsername);
        EditText txtPassword = findViewById(R.id.txtPassword);
        EditText etHeight = findViewById(R.id.Height);
        EditText etWeight = findViewById(R.id.Weight);

        String strUsername = txtUsername.getText().toString();
        String strPassword = txtPassword.getText().toString();
        //encrypts password
        String encPassword = utils.getSha256Hash(strPassword);

        String strHeight = etHeight.getText().toString();
        String strWeight = etWeight.getText().toString();
        if(strHeight.isEmpty() || strWeight.isEmpty()) {
            txtException.setText("height and weight cant be null");
            return;
        }

        double height = Double.parseDouble(strHeight);
        double weight = Double.parseDouble(strWeight);

        // Regex Username and Password Check
        RegexRunner regexRunner = new RegexRunner();

        if (!regexRunner.isValidUserName(strUsername)) {
            txtException.setText("Username must be at least 4 characters long, contain no white-space, and does not exceed 12 characters");
            return;
        }

        if (!regexRunner.isValidPassword(strPassword)){
            txtException.setText("Password must have at least 1 capital, lowercase, number, and special character");
            return;
        }

        Log.d("Insert: ", "Inserting");
        Profile p = Profile.Create(strUsername, encPassword, height, weight, 0, 0);

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