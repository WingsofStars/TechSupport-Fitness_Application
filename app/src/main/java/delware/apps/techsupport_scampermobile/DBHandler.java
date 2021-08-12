package delware.apps.techsupport_scampermobile;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
    private static final String KEY_lEVEL = "Level";
    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    //creates the user database
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USERS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_NAME + " TEXT, " + KEY_PASSWORD + "TEXT, " + KEY_WEIGHT + "TEXT, " + KEY_HEIGHT + "TEXT, "
                + KEY_lEVEL + "INTEGER);";
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    //Runs when database is changed and updates to newest version
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);

    }

    public void addNewUser(Profile profile){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, profile.getUserName());
        values.put(KEY_PASSWORD, profile.getPassword());
        values.put(KEY_WEIGHT, profile.getWeight());
        values.put(KEY_HEIGHT, profile.getHeight());
        values.put(KEY_lEVEL, profile.getLevel());
        //Inserts table columns
        db.insert(TABLE_USERS, null, values);
        //closes database connection
        db.close();
    }

    public Profile getUser(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[] {KEY_ID,KEY_NAME,KEY_PASSWORD,KEY_WEIGHT,KEY_HEIGHT,KEY_lEVEL}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Profile user = new Profile(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),
                cursor.getString(3),cursor.getString(4),Integer.parseInt(cursor.getString(5)));
        return user;
    }
}
