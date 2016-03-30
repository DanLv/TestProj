/*
 * SettingsProviderTestActivity
 * Version : 1.0
 * 2016-03-15
 * CopyRight (c) Huami Company 2015
 *      All rights reserved
 */
package me.dan.app1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.huami.watch.companion.settings.WatchSettings;

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
}
