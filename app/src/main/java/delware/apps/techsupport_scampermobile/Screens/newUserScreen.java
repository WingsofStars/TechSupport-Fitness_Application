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
import delware.apps.techsupport_scampermobile.PasswordUtils;
import delware.apps.techsupport_scampermobile.Profile;
import delware.apps.techsupport_scampermobile.R;
import delware.apps.techsupport_scampermobile.RegexRunner;
import delware.apps.techsupport_scampermobile.Utils;

public class newUserScreen extends AppCompatActivity {
    public SharedPreferences prefs;
    public static DBHandler db;


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
        EditText etHeight = findViewById(R.id.Height1);
        EditText etWeight = findViewById(R.id.Weight);

        String strUsername = txtUsername.getText().toString();
        String strPassword = txtPassword.getText().toString();
        //encrypts password
        String salt = PasswordUtils.getSalt(10);


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
        String encPassword = PasswordUtils.generateSecurePassword(strPassword, salt);

        Log.d("Insert: ", "Inserting");
        Profile p = Profile.Create(strUsername, encPassword, salt, height, weight, 1, 0);

        SharedPreferences.Editor editor = prefs.edit(); // Editor for the SP's
        editor.putString("Username", strUsername);
        editor.putInt("id", p.getUserID());
        editor.putBoolean("LoggedIn", true);
        editor.apply();
        MainActivity.currentID = String.valueOf(prefs.getInt("id", 0));
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