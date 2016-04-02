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
public class HIDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "HI.db";
    public static final String BID_TABLE_NAME = "bid";

    public HIDBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table " + BID_TABLE_NAME +
                        " (_order integer primary key, " +
                        "dueDate text," +
                        "firstName text," +
                        "chilName text, " +
                        "bcg text," +
                        "bcgScar text," +
                        "bcgRepeatDose text," +
                        "opv0 text," +
                        "opv1 text," +
                        "dptHepBHib1 text," +
                        "pcv1 text," +
                        "rv1 text," +
                        "opv2 text," +
                        "pcv2 text," +
                        "rv2 text," +
                        "opv3 text," +
                        "dptHepBHib3 text," +
                        "pcv3 text," +
                        "opv4 text," +
                        "measles1 text," +
                        "measles2 text," +
                        "isOverdue int" +
                        ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + BID_TABLE_NAME);
        onCreate(db);
    }

    public boolean insertBid(HIDBbidrow row) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("_order", row.order);
        contentValues.put("dueDate", row.dueDate);
        contentValues.put("firstName", row.firstName);
        contentValues.put("chilName", row.chilName);
        contentValues.put("bcg", row.bcg);
        contentValues.put("bcgScar", row.bcgScar);
        contentValues.put("bcgRepeatDose", row.bcgRepeatDose);
        contentValues.put("opv0", row.opv0);
        contentValues.put("opv1", row.opv1);
        contentValues.put("dptHepBHib1", row.dptHepBHib1);
        contentValues.put("pcv1", row.pcv1);
        contentValues.put("rv1", row.rv1);
        contentValues.put("opv2", row.opv2);
        contentValues.put("pcv2", row.pcv2);
        contentValues.put("rv2", row.rv2);
        contentValues.put("opv3", row.opv3);
        contentValues.put("dptHepBHib3", row.dptHepBHib3);
        contentValues.put("pcv3", row.pcv3);
        contentValues.put("opv4", row.opv4);
        contentValues.put("measles1", row.measles1);
        contentValues.put("measles2", row.measles2);
        contentValues.put("isOverdue", (row.isOverdue ? 1 : 0));
        db.insert(BID_TABLE_NAME, null, contentValues);
        return true;
    }

    public Integer deleteBid() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(BID_TABLE_NAME,
                null,
                null);
    }

    public List<HIDBbidrow> getAllBid() {
        ArrayList<HIDBbidrow> list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + BID_TABLE_NAME, null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            list.add(new HIDBbidrow(
                    res.getInt(res.getColumnIndex("_order")),
                    res.getString(res.getColumnIndex("dueDate")),
                    res.getString(res.getColumnIndex("firstName")),
                    res.getString(res.getColumnIndex("chilName")),
                    res.getString(res.getColumnIndex("dob")),
                    res.getString(res.getColumnIndex("bcg")),
                    res.getString(res.getColumnIndex("bcgScar")),
                    res.getString(res.getColumnIndex("bcgRepeatDose")),
                    res.getString(res.getColumnIndex("opv0")),
                    res.getString(res.getColumnIndex("opv1")),
                    res.getString(res.getColumnIndex("dptHepBHib1")),
                    res.getString(res.getColumnIndex("pcv1")),
                    res.getString(res.getColumnIndex("rv1")),
                    res.getString(res.getColumnIndex("opv2")),
                    res.getString(res.getColumnIndex("pcv2")),
                    res.getString(res.getColumnIndex("rv2")),
                    res.getString(res.getColumnIndex("opv3")),
                    res.getString(res.getColumnIndex("dptHepBHib3")),
                    res.getString(res.getColumnIndex("pcv3")),
                    res.getString(res.getColumnIndex("opv4")),
                    res.getString(res.getColumnIndex("measles1")),
                    res.getString(res.getColumnIndex("measles2")),
                    (res.getInt(res.getColumnIndex("isOverdue")) == 1)
            ));
            res.moveToNext();
        }
        return list;
    }
}