package jp.co.jecnet.originapp.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import jp.co.jecnet.originapp.model.CookingData;

public class CookingOpenHelper extends SQLiteOpenHelper {
    // データーベースのバージョン
    public static final int DATABASE_VERSION = 1;
    // データーベース名
    public static final String DATABASE_NAME = "CookDB2.db";

    public CookingOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE COOK" +
                "(cooking_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "cooking_photo_1 BLOB," +
                "cooking_photo_2 BLOB," +
                "cooking_data TEXT," +
                "cooking_memo TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertCookDate(CookingData cookingData) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("cooking_photo_1",cookingData.getCooking_photo_1());
        contentValues.put("cooking_photo_2",cookingData.getCooking_photo_2());
        contentValues.put("cooking_data",cookingData.getCooking_data());
        contentValues.put("cooking_memo",cookingData.getCooking_memo());
        SQLiteDatabase database = getWritableDatabase();
        long ret = -1;
        try {
            ret = database.insert("COOK", "", contentValues);
        } finally {
            database.close();
        }

    }
}
