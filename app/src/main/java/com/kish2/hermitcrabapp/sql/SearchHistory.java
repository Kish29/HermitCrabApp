package com.kish2.hermitcrabapp.sql;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class SearchHistory extends SQLiteOpenHelper {

    private static final String DB_NAME = "search_history.db";
    private static final Integer DB_VERSION = 1;

    private static final String DB_TABLE = "search_history";
    private static final String ID = "_id";
    private static final String NAME = "search";
    private static final String DB_CREATE = "create table " + DB_TABLE + " (" + ID + " integer primary key autoincrement, " + NAME + " varchar(200))";

    public SearchHistory(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /* 打开数据库并建立records表，用search记录搜索字段 */
        db.execSQL(DB_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /* 模糊查询*/
    public ArrayList<String> matchedHistory(String prefix) {
        /* 模糊搜索，通过降序排列，显示最新的消息 */
        String GET_SEARCH_LIST = "select " + NAME + " from " + DB_TABLE + " where " + NAME + " like '%" + prefix + "%' order by " + ID + " desc";
        @SuppressLint("Recycle") Cursor cursor = getReadableDatabase().rawQuery(GET_SEARCH_LIST, null);
        if (cursor.getCount() <= 0) {
            return null;
        }
        int cursorLen = cursor.getCount();
        if (cursor.moveToFirst()) {
            ArrayList<String> res = new ArrayList<>();
            for (int i = 0; i < cursorLen; i++) {
                res.add(cursor.getString(cursor.getColumnIndex(NAME)));
                cursor.moveToNext();
            }
            return res;
        }
        return null;
    }

    private boolean hasThisRecord(String search) {
        return matchedHistory(search) != null;
    }

    public void insert(String search) {
        if (hasThisRecord(search))
            return;
        getWritableDatabase().execSQL("insert into " + DB_TABLE + "(" + NAME + ")" + " values('" + search + "')");
        close();
    }
}
