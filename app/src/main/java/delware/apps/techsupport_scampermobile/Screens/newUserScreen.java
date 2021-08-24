package delware.apps.techsupport_scampermobile.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;

import delware.apps.techsupport_scampermobile.DBHandler;
import delware.apps.techsupport_scampermobile.MainActivity;
import delware.apps.techsupport_scampermobile.PasswordUtils;
import delware.apps.techsupport_scampermobile.Profile;
import delware.apps.techsupport_scampermobile.R;
import delware.apps.techsupport_scampermobile.RegexRunner;

public class newUserScreen extends AppCompatActivity {
    public SharedPreferences prefs;
    public static DBHandler db;
    Spinner sGender;
    public String strGender = "Male";
    LocalDate current = LocalDate.now();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_screen);
        prefs = getSharedPreferences("prefs", MODE_PRIVATE);

        db = new DBHandler(this);

        sGender = findViewById(R.id.s_Gender);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.GenderSelection, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        sGender.setAdapter(adapter);
    }
    //gets text from text box and runs it against regex and passes it to Profile
    public void createUser(View v){
        // Getting Variables from the EditTexts
        TextView txtException = findViewById(R.id.txtViewException);
        EditText txtUsername = findViewById(R.id.txtUsername);
        EditText txtPassword = findViewById(R.id.txtPassword);
        EditText etHeight = findViewById(R.id.Height1);
        EditText etWeight = findViewById(R.id.Weight);
        EditText etBirthYear = findViewById(R.id.et_BirthYear);




        sGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 1: strGender = "Male";
                    break;
                    case 2: strGender = "female";
                    break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

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
        //calculates Age
        String strBirthYear = etBirthYear.getText().toString();
        int birthYear = Integer.parseInt(strBirthYear);
        int age = current.getYear() - birthYear;


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
        Profile p = Profile.Create(strUsername, encPassword, salt, height, weight, age, strGender, 1, 0);

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