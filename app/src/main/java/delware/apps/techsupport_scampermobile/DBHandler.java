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
    private static final String SALT = "Salt";
    private static final String KEY_WEIGHT = "Weight";
    private static final String KEY_HEIGHT = "Height";
    private static final String KEY_LEVEL = "Level";
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
    static boolean usernameExists;


    @Override
    //creates the user database
    public void onCreate(SQLiteDatabase db) {
        //UserTable
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USERS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_NAME + " TEXT, " + KEY_PASSWORD + " TEXT, " + SALT + " TEXT, "+  KEY_HEIGHT + " TEXT, "  + KEY_WEIGHT + " TEXT, "
                + KEY_LEVEL + " INTEGER, " + KEY_XP + " INTEGER);";
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
    //adds the profile w/ the key id to the database
    public int addNewUser(String userName, String password, String salt, double height, double weight, int level, int xp){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, userName);
        values.put(KEY_PASSWORD, password);
        values.put(SALT, salt);
        values.put(KEY_HEIGHT, weight);
        values.put(KEY_WEIGHT, height);

        values.put(KEY_LEVEL, level);
        values.put(KEY_XP, xp);
        //Inserts table columns
        long id = -1;
        try {
            id = db.insertOrThrow(TABLE_USERS, null, values);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

        //closes database connection
        db.close();
        return (int)id;
    }

    // Get profile from user id
    public Profile getUserByID(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[] {KEY_ID,KEY_NAME,KEY_PASSWORD,SALT, KEY_WEIGHT, KEY_HEIGHT, KEY_LEVEL,KEY_XP}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if(cursor == null) return new Profile();
        else cursor.moveToFirst();

        return constructProfile(cursor);
    }

    public Profile getUserByUsername(String username){
        String usernameQuery = "SELECT * FROM " + TABLE_USERS + " WHERE " + KEY_NAME + " = \"" + username + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(usernameQuery, null);
        if(cursor == null) {
            return new Profile();}
        else cursor.moveToFirst();

        return constructProfile(cursor);
    }

    private Profile constructProfile(Cursor cursor)
    {
        int id = Integer.parseInt(cursor.getString(0));
        String userName = cursor.getString(1);
        String password = cursor.getString(2);
        String salt = cursor.getString(3);
        double height = Double.parseDouble(cursor.getString(4)); // in feet
        double weight = Double.parseDouble(cursor.getString(5));
        int level = Integer.parseInt(cursor.getString(6));
        int xp = Integer.parseInt(cursor.getString(7));
        return new Profile(id, userName, password,salt, height, weight, level, xp);
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
                profile.setWeight(Double.parseDouble(cursor.getString(3)));
                profile.setHeight(Double.parseDouble(cursor.getString(4)));
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
        values.put(KEY_XP, profile.getXP());
        values.put(KEY_LEVEL, profile.getLevel());
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
        String queryString = "Select * from " + RUN_LOGS +" Where "+KEY_ID+" =" + MainActivity.currentID;//where id = id
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
        ArrayList<RunLog> returnList = new ArrayList<>();
        ArrayList<RunLog> ReversereturnList = new ArrayList<>();
        if(cardioType.equalsIgnoreCase("All")) {

            String queryString = "Select * from " + RUN_LOGS + " Where " + KEY_ID + " = " + MainActivity.currentID ;//where id = id
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
                    runlog.UserId = cursor.getInt(0);
                    returnList.add(runlog);
                } while (cursor.moveToNext());
            } else {
                //oh well
            }
            cursor.close();
            db.close();

            for (int i = 0; i < returnList.size(); i++) {
                ReversereturnList.add(returnList.get((returnList.size()-1)-i));
            }
        }
        else{
            String queryString = "Select * from " + RUN_LOGS + " Where " + KEY_ID + " = " + MainActivity.currentID+ " and " + CARDIO_TYPE + " = \"" + cardioType + "\"";//where id = id
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
            for (int i = 0; i < returnList.size(); i++) {
                ReversereturnList.add(returnList.get((returnList.size()-1)-i));
            }

        }
        return ReversereturnList;
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
        cv.put(KEY_ID, MainActivity.currentID);
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
        String queryString = "Select * from " + RUN_LOGS + " where " + KEY_ID+ " = "+ MainActivity.currentID;
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
            String queryString = "Select * from " + RUN_LOGS + " where " + KEY_ID+ " = "+ MainActivity.currentID +" and " + CARDIO_TYPE + " = \"" + cardioType + "\"";
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

    public void updateLogs(int id, int Hours, int minutes, float distance, int calories){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(HOURS, Hours);
        values.put(MINUTES, minutes);
        values.put(DISTANCE, distance);
        values.put(CALORIES, calories);
        //updates row
        String sqlString = "Update " + RUN_LOGS + " SET " + HOURS + " = " + Hours + ", "+
                MINUTES + " = " + minutes + ", "+ DISTANCE + " = " + distance + ", "+
                CALORIES + " = " + calories + " "+  " where ID = "+id;
            db.execSQL(sqlString);

    }

    public RunLog getLogById(int id){
        String grabLogQuery = "SELECT * FROM " + RUN_LOGS + " WHERE " + "ID" + " = " + id ;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(grabLogQuery, null);
        if(cursor == null) {
            }
        else cursor.moveToFirst();
         RunLog runLog = construcRunLog(cursor);
         db.close();
        return runLog;
    }
    private RunLog construcRunLog(Cursor cursor)
    {

        float distance = cursor.getFloat(1);
        System.out.println(cursor.getFloat(1));
        int hours = cursor.getInt(2);
        float minutes = cursor.getFloat(3);
        int calories = cursor.getInt(4);
        String  date = cursor.getString(5);
        String  cType = cursor.getString(6);
        return new RunLog(distance,hours,minutes,calories,date,cType);
    }

    public void delete(int id){
        String deleteLogQuery = "Delete FROM " + RUN_LOGS + " WHERE " + "ID" + " = " + id ;
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(deleteLogQuery);
    }

    public void updateAllRunLogIds(){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "Select * from "+RUN_LOGS;
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            int id = 0;
            do {
                id++;
            }while (cursor.moveToNext());
        }
        else {
            //oh well
        }
        cursor.close();
        db.close();
    }


}
