/*
 * SettingsDBHelper
 * Version : 1.0
 * 2016-03-15
 * CopyRight (c) Huami Company 2015
 *      All rights reserved
 */
package com.huami.watch.companion.settings;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * @author DanLv
 */
public class SettingsDBHelper extends SQLiteOpenHelper {
    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "Settings.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Entry.TABLE_NAME + " (" +
                    Entry._ID + " INTEGER PRIMARY KEY," +
                    Entry.COLUMN_KEY + " TEXT," +
                    Entry.COLUMN_VALUE + " TEXT," +
                    Entry.COLUMN_CLOUD_SYNC_STATE + " INTEGER," +
                    Entry.COLUMN_WATCH_SYNC_STATE + " INTEGER)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Entry.TABLE_NAME;

    public SettingsDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL(SQL_DELETE_ENTRIES);
        //onCreate(db);
    }

    public static abstract class Entry extends SettingsEntry implements BaseColumns {
        public static final String TABLE_NAME = "Settings";
    }
}
