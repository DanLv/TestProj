/*
 * SettingsProviderTestActivity
 * Version : 1.0
 * 2016-03-15
 * CopyRight (c) Huami Company 2015
 *      All rights reserved
 */
package me.dan.app1;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.huami.watch.companion.settings.WatchSettings;
import com.huami.watch.companion.settings.UserSettingsManager;

/**
 * @author DanLv
 */
public class SettingsProviderTestActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_provider_test);
    }

    private int mIndexCount;

    public void onPutClick(View v) {
        WatchSettings.put(getContentResolver(), "K_" + ++mIndexCount, "V_" + System
                .currentTimeMillis());
    }

    public void onGetClick(View v) {
        WatchSettings.get(getContentResolver(), "K_" + mIndexCount--);
    }

    public void onGetAllNeedSync(View v) {
        Map<String, String> settings = UserSettingsManager.getManager(this).getAllNeedSync();
        String json = UserSettingsManager.toJSONStr(settings, null);
        Map<String, String> keyValues = UserSettingsManager.JSONToSettings(json);
    }

    public void onUpdateAllToSynced(View v) {
        UserSettingsManager.getManager(this).updateAllToSynced();
    }

    public void onAddAll(View v) {
        int count = 10;
        Map<String, String> keyValues = new HashMap<>();
        for (int i = 0; i < count; i++) {
            keyValues.put("Key_" + i, "Value_" + i);
        }

        UserSettingsManager.getManager(this).addAll(keyValues);
    }
}
