package delware.apps.techsupport_scampermobile;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "UsersDB";
    private static final String TABLE_USERS = "Users";
    //Columns names
    private static final String KEY_ID = "UserId";
    private static final String KEY_NAME = "Username";
    private static final String KEY_PASSWORD = "Password";
    private static final String KEY_WEIGHT = "Weight";
    private static final String KEY_HEIGHT = "Height";
    private static final String KEY_LEVEL = "Level";
    private static final String KEY_XP = "XP";
    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    //creates the user database
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USERS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_NAME + " TEXT, " + KEY_PASSWORD + " TEXT, " + KEY_WEIGHT + " TEXT, " + KEY_HEIGHT + " TEXT, "
                + KEY_LEVEL + " INTEGER, " + KEY_XP + " INTEGER);";
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    //Runs when database is changed and updates to newest version
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);

    }
    //adding new user :)
    public int addNewUser(String userName, String password, double height, double weight, int level, int xp){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, userName);
        values.put(KEY_PASSWORD, password);
        values.put(KEY_WEIGHT, height);
        values.put(KEY_HEIGHT, weight);
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
    public Profile getUser(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[] {KEY_ID,KEY_NAME,KEY_PASSWORD,KEY_WEIGHT,KEY_HEIGHT, KEY_LEVEL,KEY_XP}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        String userName = cursor.getString(1);
        String password = cursor.getString(2);
        double height = Double.parseDouble(cursor.getString(3)); // in feet
        double weight = Double.parseDouble(cursor.getString(4));
        int level = Integer.parseInt(cursor.getString(5));
        int xp = Integer.parseInt(cursor.getString(6));
        return new Profile(id, userName, password, height, weight, level, xp);
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
}
