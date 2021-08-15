package delware.apps.techsupport_scampermobile;
import static android.content.Context.MODE_PRIVATE;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.constraintlayout.solver.state.State;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "UsersDB.db";
    private static final String TABLE_USERS = "Users";
    //Columns names
    private static final String KEY_ID = "UserId";
    private static final String KEY_NAME = "Username";
    private static final String KEY_PASSWORD = "Password";
    private static final String KEY_WEIGHT = "Weight";
    private static final String KEY_HEIGHT = "Height";
    private static final String KEY_lEVEL = "Level";
    private static final String KEY_XP = "XP";
    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public static final String RUN_LOGS = " Run_Logs";
    public static final String HOURS = "HOURS";
    public static final String MINUTES = "Minutes";
    public static final String CALORIES = "Calories";
    public static final String DATE = "Date";
    public static final String DISTANCE = "Distance";
    public static final String CARDIO_TYPE = "Cardio_Type";

    static SharedPreferences prefs;


    @Override
    //creates the user database
    public void onCreate(SQLiteDatabase db) {
        //USerTable
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USERS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_NAME + " TEXT, " + KEY_PASSWORD + "TEXT, " + KEY_WEIGHT + "TEXT, " + KEY_HEIGHT + "TEXT, "
                + KEY_lEVEL + "INTEGER, " + KEY_XP + "INTEGER);";
        db.execSQL(CREATE_USER_TABLE);
        //RunLogs Tbale
    String createLogTable = "Create Table" + RUN_LOGS + " (ID INTEGER Primary Key AutoIncrement, " + DISTANCE + " Real," + HOURS + " int, " + MINUTES + " real, " + CALORIES + " int, " + DATE + " Text," +CARDIO_TYPE + " Text," +KEY_ID + " int, " + "FOREIGN KEY ("+KEY_ID+") REFERENCES "+TABLE_USERS+"("+KEY_ID+"))";
        db.execSQL(createLogTable);



    }

    @Override
    //Runs when database is changed and updates to newest version
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);

    }
    //adding new user
    public void addNewUser(Profile profile){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, profile.getUserName());
        values.put(KEY_PASSWORD, profile.getPassword());
        values.put(KEY_WEIGHT, profile.getWeight());
        values.put(KEY_HEIGHT, profile.getHeight());
        values.put(KEY_lEVEL, profile.getLevel());
        values.put(KEY_XP, profile.getXP());
        //Inserts table columns
        db.insert(TABLE_USERS, null, values);
        //closes database connection
        db.close();
    }

    public Profile getUser(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[] {KEY_ID,KEY_NAME,KEY_PASSWORD,KEY_WEIGHT,KEY_HEIGHT,KEY_lEVEL,KEY_XP}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Profile user = new Profile(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),
                cursor.getString(3),cursor.getString(4),Integer.parseInt(cursor.getString(5)),Integer.parseInt(cursor.getString(6)));
        return user;
    }

    public List<Profile>getAllProfiles(){
        List<Profile> profileList = new ArrayList<Profile>();
        //Query to select all
        String selectQuery = "SELECT * FROM " + TABLE_USERS;
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        //looping through all rows and adding to list
        if (cursor.moveToFirst()){
            do {
                Profile profile = new Profile();
                profile.setUserID(Integer.parseInt(cursor.getString(0)));
                profile.setUserName(cursor.getString(1));
                profile.setPassword(cursor.getString(2));
                profile.setWeight(cursor.getString(3));
                profile.setHeight(cursor.getString(4));
                profile.setLevel(Integer.parseInt(cursor.getString(5)));
                profile.setXP(Integer.parseInt(cursor.getString(6)));
                profileList.add(profile);
            }while (cursor.moveToNext());
        }
        return profileList;
    }

    public int getProfileCount(){
        String selectQuery = "SELECT * FROM " + TABLE_USERS;
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.close();
        return cursor.getCount();
    }
    //Needs more work
    public int updateUser(Profile profile){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, profile.getUserName());
        values.put(KEY_PASSWORD, profile.getPassword());
        values.put(KEY_WEIGHT, profile.getWeight());
        values.put(KEY_HEIGHT, profile.getHeight());
        //updates row
        return db.update(TABLE_USERS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(profile.getUserID())});

    }
    public void deleteUser(Profile profile) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USERS,KEY_ID + " =?",
                new String[]{String.valueOf(profile.getUserID())});
        db.close();
    }


    public ArrayList<RunLog> getAllLogs(){


        ArrayList<RunLog> returnList = new ArrayList<>();
        String queryString = "Select * from " + RUN_LOGS +" Where "+KEY_ID+" =" + MainActivity.currerntID;//where id = id
        SQLiteDatabase db = this.getReadableDatabase(); // Open Database
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            do {
                float distance = cursor.getFloat(1);
                int hours = cursor.getInt(2);
                float minutes = cursor.getFloat(3);
                int calories = cursor.getInt(4);
                String date = cursor.getString(5);
                String type = cursor.getString(6);
                RunLog runlog = new RunLog(distance,hours,minutes,calories,date,type);
                returnList.add(runlog);
            }while (cursor.moveToNext());
        }
        else {
            //oh well
        }
        cursor.close();
        db.close();
        return returnList;
    }
    public ArrayList<RunLog> getAllLogs(String cardioType){

        if(cardioType.equalsIgnoreCase("All")) {
            ArrayList<RunLog> returnList = new ArrayList<>();
            String queryString = "Select * from " + RUN_LOGS + " Where " + KEY_ID + " =" + MainActivity.currerntID ;//where id = id
            SQLiteDatabase db = this.getReadableDatabase(); // Open Database
            Cursor cursor = db.rawQuery(queryString, null);
            if (cursor.moveToFirst()) {
                do {
                    float distance = cursor.getFloat(1);
                    int hours = cursor.getInt(2);
                    float minutes = cursor.getFloat(3);
                    int calories = cursor.getInt(4);
                    String date = cursor.getString(5);
                    String type = cursor.getString(6);
                    RunLog runlog = new RunLog(distance, hours, minutes, calories, date, type);
                    returnList.add(runlog);
                } while (cursor.moveToNext());
            } else {
                //oh well
            }
            cursor.close();
            db.close();
            return returnList;
        }
        else{
            ArrayList<RunLog> returnList = new ArrayList<>();
            String queryString = "Select * from " + RUN_LOGS + " Where " + KEY_ID + " =" + MainActivity.currerntID+ " and " + CARDIO_TYPE + " = \"" + cardioType + "\"";//where id = id
            SQLiteDatabase db = this.getReadableDatabase(); // Open Database
            Cursor cursor = db.rawQuery(queryString, null);
            if (cursor.moveToFirst()) {
                do {
                    float distance = cursor.getFloat(1);
                    int hours = cursor.getInt(2);
                    float minutes = cursor.getFloat(3);
                    int calories = cursor.getInt(4);
                    String date = cursor.getString(5);
                    String type = cursor.getString(6);
                    RunLog runlog = new RunLog(distance, hours, minutes, calories, date, type);
                    returnList.add(runlog);
                } while (cursor.moveToNext());
            } else {
                //oh well
            }
            cursor.close();
            db.close();
            return returnList;
        }
    }


    public boolean addLog(RunLog runlog){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CARDIO_TYPE, runlog.getCardioType());
        cv.put(DISTANCE, runlog.getDistance());
        cv.put(HOURS, runlog.getHours());
        cv.put(MINUTES, runlog.getMinutes());
        cv.put(CALORIES, runlog.getCalories());
        cv.put(DATE, runlog.getDate());
        cv.put(KEY_ID, MainActivity.currerntID);
        long insert = db.insert(RUN_LOGS, null, cv);
        if (insert == 1){
            return false;
        }
        else{
            return true;
        }

    }

    public int size(String cardioType){
        int size = 0;
        if(cardioType.equalsIgnoreCase("All")){
        String queryString = "Select * from " + RUN_LOGS + " where " + KEY_ID+ " = "+ MainActivity.currerntID ;
        SQLiteDatabase db = this.getReadableDatabase(); // Open Database
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            do {
                size++;
            }while (cursor.moveToNext());
        }
        else {
            //oh well
        }
        cursor.close();
        db.close();
        return size;
    }
        else {
            String queryString = "Select * from " + RUN_LOGS + " where " + KEY_ID+ " = "+ MainActivity.currerntID +" and " + CARDIO_TYPE + " = \"" + cardioType + "\"";
            SQLiteDatabase db = this.getReadableDatabase(); // Open Database
            Cursor cursor = db.rawQuery(queryString, null);
            if(cursor.moveToFirst()){
                do {
                    size++;
                }while (cursor.moveToNext());
            }
            else {
                //oh well
            }
            cursor.close();
            db.close();
            return size;
        }
    }
}
