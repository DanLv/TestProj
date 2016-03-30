/* Copyright 2015 Huami Inc. All rights reserved. */
package com.huami.watch.companion.settings;

import android.net.Uri;

/**
 * @author DanLv
 */
public class SettingsEntry {
    public static final Uri CONTENT_URI = Uri.parse("content://com.huami.watch.companion.settings");

    public static final String COLUMN_KEY = "key";
    public static final String COLUMN_VALUE = "value";
    public static final String COLUMN_CLOUD_SYNC_STATE = "cloud_sync_state";
    public static final String COLUMN_WATCH_SYNC_STATE = "watch_sync_state";

    public static final String[] COLUMNS_VALUE = new String[]{COLUMN_VALUE,};
    public static final String[] COLUMNS_ALL = new String[]{COLUMN_KEY, COLUMN_VALUE,
            COLUMN_CLOUD_SYNC_STATE, COLUMN_WATCH_SYNC_STATE,};
}
