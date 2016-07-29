/* Copyright 2015 Huami Inc. All rights reserved. */
package com.huami.watch.companion.settings;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

/**
 * @author DanLv
 */
public class WatchSettings {
    private static final String TAG = "DDDD";

    public static boolean put(ContentResolver resolver, String key, String value) {
        boolean success = false;

        ContentValues values = new ContentValues();
        values.put(SettingsEntry.COLUMN_KEY, key);
        values.put(SettingsEntry.COLUMN_VALUE, value);
        values.put(SettingsEntry.COLUMN_CLOUD_SYNC_STATE, SettingsEntry.SYNC_STATE_NEED_SYNC);

        Cursor c = null;
        try {
            c = resolver.query(SettingsEntry.CONTENT_URI, SettingsEntry.COLUMNS_EMPTY,
                    SettingsEntry.COLUMN_KEY + "=?", new String[]{key,}, null);
            if (c != null && c.moveToFirst()) {
                Log.d(TAG, "Update Values : " + values.toString());
                int count = resolver.update(SettingsEntry.CONTENT_URI, values, SettingsEntry
                        .COLUMN_KEY + "=?", new String[]{key,});
                success = count > 0;
            } else {
                Log.d(TAG, "Insert Values : " + values.toString());
                resolver.insert(SettingsEntry.CONTENT_URI, values);
                success = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null) {
                c.close();
            }
        }

        return success;
    }

    public static String get(ContentResolver resolver, String key) {
        String value = null;

        Cursor c = null;
        try {
            c = resolver.query(SettingsEntry.CONTENT_URI, SettingsEntry.COLUMNS_VALUE,
                    SettingsEntry.COLUMN_KEY + "=?", new String[]{key,}, null);
            if (c != null && c.moveToFirst()) {
                value = c.getString(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null) {
                c.close();
            }
        }

        Log.d(TAG, "Get : " + key + ", " + value);
        return value;
    }

    public static class SettingsEntry {
        public static final Uri CONTENT_URI = Uri.parse("content://com.huami.watch.companion.settings");

        public static final String COLUMN_KEY = "key";
        public static final String COLUMN_VALUE = "value";
        public static final String COLUMN_CLOUD_SYNC_STATE = "cloud_sync_state";
        public static final String COLUMN_WATCH_SYNC_STATE = "watch_sync_state";

        public static final String[] COLUMNS_EMPTY = new String[]{};
        public static final String[] COLUMNS_VALUE = new String[]{COLUMN_VALUE,};
        public static final String[] COLUMNS_KEY_VALUE = new String[]{COLUMN_KEY, COLUMN_VALUE};
        public static final String[] COLUMNS_ALL = new String[]{COLUMN_KEY, COLUMN_VALUE,
                COLUMN_CLOUD_SYNC_STATE, COLUMN_WATCH_SYNC_STATE,};

        public static final int SYNC_STATE_NEED_SYNC = 0;
        public static final int SYNC_STATE_SYNCED = 1;
    }
}
