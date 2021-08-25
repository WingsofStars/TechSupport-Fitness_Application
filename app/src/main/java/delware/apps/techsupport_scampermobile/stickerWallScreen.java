package delware.apps.techsupport_scampermobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class stickerWallScreen extends AppCompatActivity {
    private TextView TV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_sticker_wall);
        TV = findViewById(R.id.DescriptionText1);
        TV.setText("Tap on a sticker to learn more...");
        if(Integer.parseInt(MainActivity.currentID) != 0) {
            updateStickers();
        }

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

    public void goToDiscRackScreen(View v){
        Intent goToDiscRack = new Intent(stickerWallScreen.this, discRackScreen.class);
        startActivity(goToDiscRack);
    }

    public void updateStickers() {
        ImageView v;
        Profile user = MainActivity.databaseHandler.getUserByID(Integer.parseInt(MainActivity.currentID));

        ArrayList<RunLog> RunLogs = MainActivity.databaseHandler.getAllLogs("All");

        float totaldistance = 0f;
        int totalCalories = 0;
        float totalTime = 0f;

        if(Integer.parseInt(MainActivity.currentID) > 0) {
            v = findViewById(R.id.A01);
            v.setImageResource(android.R.drawable.btn_star_big_on);
        }
        if(RunLogs.size() > 0) {
            v = findViewById(R.id.A02);
            v.setImageResource(android.R.drawable.btn_star_big_on);
        }
        //Rival check
        if(user.getLevel() >= 2) {
            v = findViewById(R.id.A04);
            v.setImageResource(android.R.drawable.btn_star_big_on);
        }
        if(user.getLevel() >= 4) {
            v = findViewById(R.id.A11);
            v.setImageResource(android.R.drawable.btn_star_big_on);
        }
        if(user.getLevel() >= 6) {
            v = findViewById(R.id.A12);
            v.setImageResource(android.R.drawable.btn_star_big_on);
        }
        if(user.getLevel() >= 8) {
            v = findViewById(R.id.A13);
            v.setImageResource(android.R.drawable.btn_star_big_on);
        }
        if(user.getLevel() >= 10) {
            v = findViewById(R.id.A14);
            v.setImageResource(android.R.drawable.btn_star_big_on);
        }
        if(user.getLevel() >= 14) {
            v = findViewById(R.id.A21);
            v.setImageResource(android.R.drawable.btn_star_big_on);
        }
        if(user.getLevel() >= 18) {
            v = findViewById(R.id.A22);
            v.setImageResource(android.R.drawable.btn_star_big_on);
        }
        if(user.getLevel() >= 20) {
            v = findViewById(R.id.A23);
            v.setImageResource(android.R.drawable.btn_star_big_on);
        }
        if(trackAWeek(RunLogs)) {
            v = findViewById(R.id.A24);
            v.setImageResource(android.R.drawable.btn_star_big_on);
        }

        //Stat stickers
        for( int i = 0; i < MainActivity.databaseHandler.size("All"); i++) {
            totalCalories += RunLogs.get(i).calories;
            totaldistance += RunLogs.get(i).Distance;
            totalTime +=( (RunLogs.get(i).Hours) + (RunLogs.get(i).Minutes/60));
        }

        if(totaldistance >= 5) {
            v = findViewById(R.id.A31);
            v.setImageResource(android.R.drawable.btn_star_big_on);
        }
        if(totaldistance >= 10) {
            v = findViewById(R.id.A32);
            v.setImageResource(android.R.drawable.btn_star_big_on);
        }
        if(totaldistance >= 15) {
            v = findViewById(R.id.A33);
            v.setImageResource(android.R.drawable.btn_star_big_on);
        }
        if(totaldistance >= 20) {
            v = findViewById(R.id.A34);
            v.setImageResource(android.R.drawable.btn_star_big_on);
        }
        if(totalCalories >= 1500) {
            v = findViewById(R.id.A41);
            v.setImageResource(android.R.drawable.btn_star_big_on);
        }
        if(totalCalories >= 3000) {
            v = findViewById(R.id.A42);
            v.setImageResource(android.R.drawable.btn_star_big_on);
        }
        if(totalTime >= 5) {
            v = findViewById(R.id.A43);
            v.setImageResource(android.R.drawable.btn_star_big_on);
        }
        if(totalTime >= 10) {
            v = findViewById(R.id.A44);
            v.setImageResource(android.R.drawable.btn_star_big_on);
        }
    }

    class Wrapper {
        private final RunLog log;
        public Wrapper(RunLog log) {
            this.log = log;
        }
        public RunLog unwrap() {
            return log;
        }
        public boolean equals(Object other) {
            if(other instanceof Wrapper) {
                return ((Wrapper) other).log.getDate().equals(log.getDate());
            } else {
                return false;
            }
        }
        public int hashCode() {
            return log.getDate().hashCode();
        }
    }

    private boolean trackAWeek(ArrayList<RunLog> logs) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        LocalDate current = LocalDate.now();
        LocalDate weekBehind = current.minusWeeks(1);

        List<RunLog> logsOfThatWeek = logs.stream()
                .filter(log -> (LocalDate.parse(log.date, format).isAfter(weekBehind) && LocalDate.parse(log.date, format).isBefore(current))
                        || (LocalDate.parse(log.date, format).equals(weekBehind) || LocalDate.parse(log.date, format).equals(current)))
                .collect(Collectors.toList());
        long countDistinctWeekLogs = logsOfThatWeek.stream().map(Wrapper::new).distinct().map(Wrapper::unwrap).count();
        return countDistinctWeekLogs == 7;
    }

    public void showStickerDescription(View v) {
        switch(v.getId()) {
            case R.id.A01:
                TV.setText("First log in, welcome to the app...");
                break;
            case R.id.A02:
                TV.setText("Track or log your first activity...");
                break;
            case R.id.A03:
                TV.setText("Beat your rival for the first time, you know you can do it...");
                break;
            case R.id.A04:
                TV.setText("Achieve level 2...");
                break;
            case R.id.A11:
                TV.setText("Achieve level 4...");
                break;
            case R.id.A12:
                TV.setText("Achieve level 6...");
                break;
            case R.id.A13:
                TV.setText("Achieve level 8...");
                break;
            case R.id.A14:
                TV.setText("Achieve level 10...");
                break;
            case R.id.A21:
                TV.setText("Achieve level 14...");
                break;
            case R.id.A22:
                TV.setText("Achieve level 18...");
                break;
            case R.id.A23:
                TV.setText("Achieve the maximum level, what an awesome job, keep going, keep pushing yourself...");
                break;
            case R.id.A24:
                TV.setText("What a week, track or log your activity for a full week...");
                break;
            case R.id.A31:
                TV.setText("Travel a total distance of 5 miles...");
                break;
            case R.id.A32:
                TV.setText("Travel a total distance of 10 miles...");
                break;
            case R.id.A33:
                TV.setText("Travel a total distance of 15 miles...");
                break;
            case R.id.A34:
                TV.setText("Travel a total distance of 20 miles...");
                break;
            case R.id.A41:
                TV.setText("Burn a total of 1,500 calories...");
                break;
            case R.id.A42:
                TV.setText("Burn a total of 3,000 calories...");
                break;
            case R.id.A43:
                TV.setText("Exercise for a total of 5 hours...");
                break;
            case R.id.A44:
                TV.setText("Exercise for a total of 10 hours...");
                break;
            default:
                TV.setText("Invalid sticker, tap to select another one...");
                break;
        }
    }
}
