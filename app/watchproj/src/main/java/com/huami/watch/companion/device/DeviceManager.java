/*
 * DeviceManager
 * Version : 1.0
 * 2016-05-06
 * CopyRight (c) Huami Company 2015
 *      All rights reserved
 */
package com.huami.watch.companion.device;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

/**
 * @author DanLv
 */
public class DeviceManager {
    private static final String TAG = "Device-Manger";

    private static DeviceManager sManager;

    private DeviceManager() {
    }

    public static DeviceManager getManager() {
        if (sManager == null) {
            sManager = new DeviceManager();
        }

        return sManager;
    }

    public DeviceInfo getDeviceInfo(Context context) {
        DeviceInfo info = new DeviceInfo();
        info.setAndroidDeviceId(DeviceUtil.retrieveAndroidDeviceId(context));
        info.setCpuId(DeviceUtil.retrieveCpuID());

        Log.d(TAG, "Get DeviceInfo : " + info);
        return info;
    }

    public String getDeviceInfoJson(Context context) {
        JSONObject json = new JSONObject();
        DeviceInfo info = getDeviceInfo(context);

        try {
            if (info.getAndroidDeviceId() != null) {
                json.put("AndroidDID", info.getAndroidDeviceId());
            }
            if (info.getCpuId() != null) {
                json.put("CPUID", info.getCpuId());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "Get DeviceInfo : " + json.toString());
        return json.toString();
    }
}
