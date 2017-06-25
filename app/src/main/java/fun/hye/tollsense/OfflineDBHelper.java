package fun.hye.tollsense;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class OfflineDBHelper extends SQLiteOpenHelper {

    static OfflineDBHelper offlineDBHelper;

    private static final String DATABASE_NAME = "LocalDB.db";
    private static final int DATABASE_VERSION = 3;
    private static final String TABLE_NAME1 = "userInfo";

    private OfflineDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static OfflineDBHelper getInstance(Context context) {
        if (offlineDBHelper == null)
            offlineDBHelper = new OfflineDBHelper(context);
        return offlineDBHelper;
    }

    static void clear() {
        offlineDBHelper = null;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME1 + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username VARCHAR(5), VIN VARCHAR(20), address TEXT, balance INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
        onCreate(db);
    }

    public void populateTable() {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", "user1");
        contentValues.put("VIN", "VIN1");
        contentValues.put("address", "Address1");
        contentValues.put("balance", 100);
        db.insert(TABLE_NAME1, null, contentValues);

        contentValues = new ContentValues();
        contentValues.put("username", "user2");
        contentValues.put("VIN", "VIN2");
        contentValues.put("address", "Address2");
        contentValues.put("balance", 200);
        db.insert(TABLE_NAME1, null, contentValues);

        contentValues = new ContentValues();
        contentValues.put("username", "user3");
        contentValues.put("VIN", "VIN3");
        contentValues.put("address", "Address3");
        contentValues.put("balance", 300);
        db.insert(TABLE_NAME1, null, contentValues);

        contentValues = new ContentValues();
        contentValues.put("username", "user4");
        contentValues.put("VIN", "VIN4");
        contentValues.put("address", "Address4");
        contentValues.put("balance", 400);
        db.insert(TABLE_NAME1, null, contentValues);

    }

    Cursor getUserInfo(String user) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT username, VIN, address, balance FROM " + TABLE_NAME1 +
                " WHERE username = '" + user + "'", null);
    }

    void updateUser(String user, String add) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("address", add);
        db.update(TABLE_NAME1, contentValues, "username = ?", new String[]{user});
    }
}
