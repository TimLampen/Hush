package com.example.jonathanstroz.backgroundnotificationreciever;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.jonathanstroz.backgroundnotificationreciever.MainActivity.mDatabaseHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String TABLE_NAME = "notifications";
    private static final String COL1 = "source";
    private static final String COL2 = "title";
    private static final String COL3 = "message";
    private static final String COL4 = "time";
    private static final String COL5 = "dismissed";

    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "Create TABLE " + TABLE_NAME + "("
                + COL1 + " TEXT NOT NULL, "
                + COL2 + " TEXT NOT NULL, "
                + COL3 + " TEXT NOT NULL, "
                + COL4 + " TEXT NOT NULL, "
                + COL5 + " TEXT);";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE EXISTS" + TABLE_NAME);
        onCreate(db);
    }

    public long addData(String source, String title, String message, Long time, String reason){
        //Fix adding to DB only adds one line
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, source);
        contentValues.put(COL2, title);
        contentValues.put(COL3,  message);
        contentValues.put(COL4, time);
        contentValues.put(COL5, reason);
        long rowId = db.insert(TABLE_NAME, null, contentValues);
        return rowId;
    }

    public static String getDb(){

        try {
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        String list = "";
        if (cursor.moveToFirst()){
            while(cursor.moveToNext()){
                String source = cursor.getString(cursor.getColumnIndex(COL1));
                String title = cursor.getString(cursor.getColumnIndex(COL2));
                String message = cursor.getString(cursor.getColumnIndex(COL3));
                String time = cursor.getString(cursor.getColumnIndex(COL4));
                String reason = cursor.getString(cursor.getColumnIndex(COL5));
                list += source + "\t" + title + "\t" + message + "\t" + time + "\t" + reason + "\n";
            }
        }
        cursor.close();
        // close db connection
        db.close();

        // return notes list
        return list;
        }catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
