package com.example.sona.opticalillusions;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

/**
 * Created by Soňa on 06-Apr-17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "illusions.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_ALL = "all_table";
    public static final String COLUMN_1 = "id";
    public static final String COLUMN_2 = "name";
    public static final String COLUMN_3 = "category";
    public static final String COLUMN_4 = "description";
    public static final String COLUMN_5 = "picture";
    public static final String COLUMN_6 = "animation";

//    public static final String TABLE_CATEGORIES = "categories_table";
//    public static final String COLUMN_1 = "type";
//    public static final String COLUMN_2 = "seq";

//    CREATE TABLE prices (
//            id INTEGER PRIMARY KEY,
//            pName TEXT CHECK( LENGTH(pName) <= 100 ) NOT NULL DEFAULT '',
//            pType TEXT CHECK( pType IN ('M','R','H') ) NOT NULL DEFAULT 'M',
//            pField TEXT CHECK( LENGTH(pField) <= 50 ) NULL DEFAULT NULL,
//            pFieldExt TEXT CHECK( LENGTH(pFieldExt) <= 50 ) NULL DEFAULT NULL,
//            cmp_id INTEGER NOT NULL DEFAULT '0'
//    )
//    https://www.youtube.com/watch?v=K6cYSNXb9ew

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_String = "CREATE TABLE " + TABLE_ALL +
                            "(" + COLUMN_1 + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                            + COLUMN_2 + " TEXT," + COLUMN_3 + " TEXT," + COLUMN_4 + " TEXT,"
                            + COLUMN_5 + " INTEGER" + COLUMN_6 + " TEXT" + ")";

        db.execSQL(SQL_String);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALL);
        onCreate(db);
    }

    public void addIllusion (Illusion illusion) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_1, illusion.getId());
        values.put(COLUMN_2, illusion.getName());
        values.put(COLUMN_3, illusion.getCategory());
        values.put(COLUMN_4, illusion.getDescription());
        values.put(COLUMN_5, illusion.getPicture());
        values.put(COLUMN_6, illusion.getAnimation());

        db.insert(TABLE_ALL, null, values);
        db.close();
    }

    public Illusion getIllusion (int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_ALL, new String[] {COLUMN_1, COLUMN_2, COLUMN_3, COLUMN_4, COLUMN_5, COLUMN_6},
                COLUMN_1 + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        Illusion illusion = new Illusion(cursor.getString(1), cursor.getString(0),
                cursor.getString(2), cursor.getString(3),
                Integer.parseInt(cursor.getString(4)), cursor.getString(5));
        return illusion;
    }

    public ArrayList<Illusion> getAllIllusions () {
        ArrayList<Illusion> result = new ArrayList<>();

        String selectQuery = "SELECT * FROM" + TABLE_ALL;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Illusion illusion = new Illusion(cursor.getString(1), cursor.getString(0), cursor.getString(2),
                cursor.getString(3), Integer.parseInt(cursor.getString(4)), cursor.getString(5));
                result.add(illusion);
            } while (cursor.moveToNext());
        }
        return result;
    }

    public int updateIllusion (Illusion illusion) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_1, illusion.getId());
        values.put(COLUMN_2, illusion.getName());
        values.put(COLUMN_3, illusion.getCategory());
        values.put(COLUMN_4, illusion.getDescription());
        values.put(COLUMN_5, illusion.getPicture());
        values.put(COLUMN_6, illusion.getAnimation());

        return db.update(TABLE_ALL, values, COLUMN_1 + "=?", new String[]{illusion.getId()});
    }

    public void deleteIllusion (Illusion illusion) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ALL, COLUMN_1 + "=?", new String[]{illusion.getId()});
        db.close();
    }

    public int getNumberOfIllusions () {
        String query = "SELECT * FROM" + TABLE_ALL;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.close();
        return cursor.getCount();
    }
}
