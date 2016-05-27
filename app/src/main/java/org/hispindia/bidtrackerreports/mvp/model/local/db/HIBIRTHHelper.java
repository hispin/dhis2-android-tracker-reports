package org.hispindia.bidtrackerreports.mvp.model.local.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nhancao on 2/8/16.
 */
public class HIBIRTHHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "HIBIRTH.db";
    public static final String BID_TABLE_NAME = "birthbid";

    public HIBIRTHHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table " + BID_TABLE_NAME +
                        " (_order integer primary key, " +

                        "firstName text," +
                        "lastname text, " +
                        "dob text," +
                        "gender text," +
                        "village text," +
                        "zone text," +
                        ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + BID_TABLE_NAME);
        onCreate(db);
    }

    public boolean insertBid(HIbidbirthrow row) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("firstName", row.firstName);
        contentValues.put("lastname", row.lastname);
        contentValues.put("dob", row.dob);
        contentValues.put("gender", row.gender);
        contentValues.put("village", row.village);
        contentValues.put("zone", row.zone);
        db.insert(BID_TABLE_NAME, null, contentValues);
        return true;
    }

    public Integer deleteBid() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(BID_TABLE_NAME,
                null,
                null);
    }

    public List<HIbidbirthrow> getAllBid() {
        ArrayList<HIbidbirthrow> list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + BID_TABLE_NAME, null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            list.add(new HIbidbirthrow(
                    res.getString(res.getColumnIndex("firstName")),
                    res.getString(res.getColumnIndex("lastname")),
                    res.getString(res.getColumnIndex("dob")),
                    res.getString(res.getColumnIndex("gender")),
                    res.getString(res.getColumnIndex("village")),
                    res.getString(res.getColumnIndex("zone"))

            ));
            res.moveToNext();
        }
        return list;
    }
}