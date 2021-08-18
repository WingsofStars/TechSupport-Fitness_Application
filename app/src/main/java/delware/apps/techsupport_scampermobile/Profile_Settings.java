package delware.apps.techsupport_scampermobile;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import delware.apps.techsupport_scampermobile.Screens.settings;
import delware.apps.techsupport_scampermobile.Utils;
import delware.apps.techsupport_scampermobile.R.layout.*;


public class Profile_Settings extends AppCompatActivity {
    TextView userNameTxt;
    TextView Age;
    TextView Height1;
    TextView Height2;
    TextView Weight;
    Button btn;
    public AlertDialog.Builder dBuilder;
    public AlertDialog dialogue;
    public Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);
        userNameTxt = findViewById(R.id.username);
        //Age = findViewById(R.id.);
        Height1 = findViewById(R.id.Height1);
        Height2 = findViewById(R.id.height2);
        Weight = findViewById(R.id.Weight);
        btn = findViewById(R.id.button3);
        if (MainActivity.prefs.getBoolean("LoggedIn", false)) {
            Profile profile = MainActivity.databaseHandler.getUserByID(Integer.valueOf(MainActivity.currentID));
            userNameTxt.setText(profile.getUserName());
            int Height = (int) profile.getHeight();

            Height1.setText(String.valueOf(Height / 12) + " ft");
            Height2.setText(String.valueOf(Height % 12) + " inches");
            Weight.setText(String.valueOf(profile.getWeight()) + " lbs");
        } else {
            userNameTxt.setText("Log in to get this information");
            btn.setText("Log In");
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {







                if (btn.getText().toString().equalsIgnoreCase("Sign Out")) {
                    SharedPreferences.Editor editor = MainActivity.prefs.edit();
                    editor.putString("Username", "");
                    editor.putInt("id", 0);
                    editor.putBoolean("LoggedIn", false);
                    editor.apply();
                    MainActivity.currentID = String.valueOf(MainActivity.prefs.getInt("id", 0));
                    editor.apply();
                    Toast.makeText(Profile_Settings.this, "User Signed Out", Toast.LENGTH_LONG);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);//Exits current intent
                    intent.putExtra("EXIT", true);
                    startActivity(intent);
                    btn.setText("Log In");
                } else if (btn.getText().toString().equalsIgnoreCase("Log In")) {
                    Button button;
                    dBuilder = new AlertDialog.Builder(Profile_Settings.this);
                    final View loginPopup = getLayoutInflater().inflate(R.layout.loginpopup, null);
                    dBuilder.setView(loginPopup);
                    dialogue = dBuilder.create();
                    dialogue.show();

                    button = (Button) loginPopup.findViewById(R.id.btnLogin);

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String ErrorText = "Invalid Information";
                            Profile p;
                            int id;
                            TextView textViewException = loginPopup.findViewById(R.id.tvException);
                            EditText givenUsernameView = loginPopup.findViewById(R.id.txtLoginUsername);
                            EditText givenPasswordView = loginPopup.findViewById(R.id.txtLoginPassword);

                            String givenUserName = givenUsernameView.getText().toString();
                            String givenPassword = givenPasswordView.getText().toString();
                            String encLoginPassword = utils.getSha256Hash(givenPassword);
                            try {
                                p = MainActivity.databaseHandler.getUserByUsername(givenUserName, givenPassword);

                                id = p.getUserID();

                                if (id == -1) {
                                    textViewException.setText(ErrorText);
                                    return;
                                }
                            } catch (Exception e) {
                                textViewException.setText("ERROR");
                                return;
                            }


                            //When everything is logged in:
                            SharedPreferences.Editor editor = MainActivity.prefs.edit();
                            editor.putString("Username", givenUserName);
                            editor.putInt("id", id);
                            editor.putBoolean("LoggedIn", true);
                            editor.apply();
                            MainActivity.currentID = String.valueOf(MainActivity.prefs.getInt("id", 0));
                            btn.setText("Sign Out");

                            dialogue.dismiss();



                            Profile profile = MainActivity.databaseHandler.getUserByID(Integer.valueOf(MainActivity.currentID));
                            userNameTxt.setText(profile.getUserName());
                            int Height = (int) profile.getHeight();

                            Height1.setText(String.valueOf(Height / 12) + " ft");
                            Height2.setText(String.valueOf(Height % 12) + " inches");
                            Weight.setText(String.valueOf(profile.getWeight()) + " lbs");
                        }



                    });


                }









            }
        });

    }

            //    THIS IS AN IMPORTANT FUNCTION TO EXIT THE CURRENT INTENT AND GO BACK TO THE PREVIOUS ACTIVITY
            public void exitIntent() {
                Intent intent = new Intent(getApplicationContext(), settings.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);//Exits current intent
                intent.putExtra("EXIT", true);
                startActivity(intent);
            }

            //Clickable Icon Lets the user exit current intent/activity and return to the previous screen
            public void goBack(View v) {
                exitIntent();
            }

            public void signOut(View v) {
                SharedPreferences.Editor editor = MainActivity.prefs.edit();
                editor.putString("Username", "");
                editor.putInt("id", 0);
                editor.putBoolean("LoggedIn", false);
                editor.apply();
                MainActivity.currentID = String.valueOf(MainActivity.prefs.getInt("id", 0));
                editor.apply();
                Toast.makeText(Profile_Settings.this, "User Signed Out", Toast.LENGTH_LONG);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);//Exits current intent
                intent.putExtra("EXIT", true);
                startActivity(intent);
            }
        }

