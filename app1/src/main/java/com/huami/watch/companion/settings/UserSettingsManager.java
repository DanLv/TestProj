/* Copyright 2015 Huami Inc. All rights reserved. */
package com.huami.watch.companion.settings;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.util.Log;

import com.huami.watch.companion.settings.WatchSettings.SettingsEntry;

/**
 * @author DanLv
 */
public class UserSettingsManager {
    private static final String TAG = "DDDD";

    private static UserSettingsManager sManager;

    private Context mContext;

    private UserSettingsManager(Context context) {
        mContext = context;
    }

    public static UserSettingsManager getManager(Context context) {
        if (sManager == null) {
            sManager = new UserSettingsManager(context);
        } else {
            sManager.mContext = context;
        }

        return sManager;
    }

    public Map<String, String> getAllNeedSync() {
        Log.d(TAG, "Get All Need Sync!!");
        Map<String, String> keyValues = new HashMap<>();

        Cursor c = null;
        try {
            ContentResolver resolver = mContext.getContentResolver();
            c = resolver.query(SettingsEntry.CONTENT_URI, SettingsEntry.COLUMNS_ALL, null,
                    null, null);
            //SettingsEntry.COLUMN_CLOUD_SYNC_STATE + "!=?",
            //        new String[]{String.valueOf(SettingsEntry.SYNC_STATE_SYNCED),}

            if (c != null) {
                Log.d(TAG, "All Settings Count : " + c.getCount());
                while (c.moveToNext()) {
                    if (c.getInt(c.getColumnIndex(SettingsEntry.COLUMN_CLOUD_SYNC_STATE)) !=
                            SettingsEntry.SYNC_STATE_SYNCED) {
                        String key = c.getString(0);
                        String value = c.getString(1);

                        keyValues.put(key, value);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null) {
                c.close();
            }
        }

        Log.d(TAG, "All Need Sync Count : " + keyValues.size());
        return keyValues;
    }

    public void updateAllToSynced() {
        Log.d(TAG, "Update All To Synced!!");
        ContentValues values = new ContentValues();
        values.put(SettingsEntry.COLUMN_CLOUD_SYNC_STATE, SettingsEntry.SYNC_STATE_SYNCED);

        Cursor c = null;
        try {
            ContentResolver resolver = mContext.getContentResolver();
            Log.d(TAG, "Update Values : " + values.toString());
            int count = resolver.update(SettingsEntry.CONTENT_URI, values, null, null);
            Log.d(TAG, "All To Synced Count : " + count);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null) {
                c.close();
            }
        }
    }

    public void addAll(Map<String, String> keyValues) {
        if (keyValues == null || keyValues.size() == 0) {
            return;
        }
        Log.d(TAG, "Add All Count : " + keyValues.size());

        for (Map.Entry<String, String> entry : keyValues.entrySet()) {
            WatchSettings.put(mContext.getContentResolver(), entry.getKey(), entry.getValue());
        }
        updateAllToSynced();
    }

    public static String toJSONStr(Map<String, String> keyValues, String filter) {
        if (keyValues == null || keyValues.size() == 0) {
            return "";
        }

        Log.d(TAG, "Settings To JSON Filter : " + filter);
        JSONObject object = new JSONObject();
        try {
            for (Map.Entry<String, String> entry : keyValues.entrySet()) {
                if (!TextUtils.isEmpty(filter) && entry.getKey().startsWith(filter)) {
                    continue;
                }
                object.put(entry.getKey(), entry.getValue());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String jsonStr = object.toString();
        Log.d(TAG, "Settings To JSON : " + object.length() + ", " + jsonStr);

        return jsonStr;
    }

    public static Map<String, String> JSONToSettings(String jsonStr) {
        if (TextUtils.isEmpty(jsonStr)) {
            return null;
        }

        Log.d(TAG, "JSON To Settings : " + jsonStr);
        Map<String, String> keyValues = new HashMap<>();
        try {
            JSONObject json = new JSONObject(jsonStr);
            if (json.length() > 0) {
                Iterator<String> keys = json.keys();
                while (keys.hasNext()) {
                    String keyStr = keys.next();
                    keyValues.put(keyStr, json.getString(keyStr));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "JSON To Settings Count : " + keyValues.size());
        return keyValues;
    }
}
