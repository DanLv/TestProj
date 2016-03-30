/*
 * SettingsProviderTestActivity
 * Version : 1.0
 * 2016-03-15
 * CopyRight (c) Huami Company 2015
 *      All rights reserved
 */
package me.dan.testproj;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.huami.watch.companion.settings.SettingsDBHelper;
import com.huami.watch.companion.settings.SettingsProvider;

/**
 * @author DanLv
 */
public class SettingsProviderTestActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_provider_test);
    }

    private int mInsertCount;

    public void onPutClick(View v) {
        ContentValues values = new ContentValues();
        values.put(SettingsDBHelper.Entry.COLUMN_KEY, "Key" + ++mInsertCount);
        values.put(SettingsDBHelper.Entry.COLUMN_VALUE, "Value" + System.currentTimeMillis());
        Log.d("DDDD", "Values : " + values.toString());

        ContentResolver resolver = getContentResolver();
        resolver.insert(SettingsProvider.CONTENT_URI, values);
    }

    public void onGetClick(View v) {
        ContentResolver resolver = getContentResolver();
        Cursor c = resolver.query(SettingsProvider.CONTENT_URI, SettingsDBHelper.Entry
                .COLUMNS_VALUE, SettingsDBHelper.Entry.COLUMN_KEY + "=?", new
                String[]{"Key1"}, null);
        if (c != null && c.moveToFirst()) {
            String queryValue = c.getString(0);
            Log.d("DDDD", "Query : " + queryValue);
            c.close();
        }
    }
}
