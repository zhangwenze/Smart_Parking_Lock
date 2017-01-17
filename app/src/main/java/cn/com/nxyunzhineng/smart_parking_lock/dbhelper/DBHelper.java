package cn.com.nxyunzhineng.smart_parking_lock.dbhelper;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by wenze on 2016/10/29.
 */

public class DBHelper extends SQLiteOpenHelper{
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(ContentValues contentValues,String table){
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(table,null,contentValues);
        db.close();
    }
    public void update() {

        SQLiteDatabase db = this.getWritableDatabase();

    }


}
