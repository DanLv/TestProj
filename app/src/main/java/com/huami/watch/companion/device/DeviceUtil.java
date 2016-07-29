/* Copyright 2015 Huami Inc. All rights reserved. */
package com.huami.watch.companion.device;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;

/**
 * @author DanLv
 */
public class DeviceUtil {
    private static final String TAG = "Device-Util";

    public static String retrieveAndroidDeviceId(Context context) {
        String deviceId = null;
        try {
            deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure
                    .ANDROID_ID);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d(TAG, "Retrieve Android DeviceId : <" + deviceId + ">");
        return deviceId;
    }

    public static String retrieveCpuID() {
        String cpuId = null;

        try {
            String out = execCmd("cat proc/jz/efuse/chip_num");
            Log.d(TAG, "Out : " + out);
            if (out != null && out.contains("chip_num")) {
                String[] splits = out.split(":");
                if (splits.length >= 2) {
                    cpuId = splits[1].trim();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d(TAG, "Retrieve CpuId : <" + cpuId + ">");
        return cpuId;
    }

    private static String execCmd(String input) {
        String output = "";

        try {
            Process p = Runtime.getRuntime().exec(input);
            BufferedReader ie = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String error;
            while ((error = ie.readLine()) != null
                    && !error.equals("null")) {
                output += error + "\n";
            }

            String line;
            while ((line = in.readLine()) != null
                    && !line.equals("null")) {
                output += line + "\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return output;
    }
}
