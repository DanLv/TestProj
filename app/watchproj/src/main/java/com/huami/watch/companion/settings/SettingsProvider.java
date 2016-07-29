/* Copyright 2015 Huami Inc. All rights reserved. */
package com.huami.watch.companion.settings;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * @author DanLv
 */
public class SettingsProvider extends ContentProvider {
    private static final String PROVIDER_NAME = "com.huami.watch.companion.settings";
    public static final Uri CONTENT_URI = Uri.parse("content://" + PROVIDER_NAME);

    private SettingsDBHelper mOpenHelper;
    private SQLiteDatabase mDB;

    @Override
    public boolean onCreate() {
        mOpenHelper = new SettingsDBHelper(getContext());
        mDB = mOpenHelper.getWritableDatabase();
        return mDB != null;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        Cursor c = mDB.query(SettingsDBHelper.Entry.TABLE_NAME, projection, selection, selectionArgs,
                sortOrder, null, null);
        //c.setNotificationUri(getContext().getContentResolver(), uri);

        return c;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long rowID = mDB.insert(SettingsDBHelper.Entry.TABLE_NAME, null, values);

        if (rowID > 0) {
            //getContext().getContentResolver().notifyChange(uri, null);
            return uri;
        }
        throw new SQLException("Failed to add a record into " + uri);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = mDB.delete(SettingsDBHelper.Entry.TABLE_NAME, selection, selectionArgs);
        //getContext().getContentResolver().notifyChange(uri, null);

        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int count = mDB.update(SettingsDBHelper.Entry.TABLE_NAME, values, selection, selectionArgs);
        //getContext().getContentResolver().notifyChange(uri, null);

        return count;
    }
}
