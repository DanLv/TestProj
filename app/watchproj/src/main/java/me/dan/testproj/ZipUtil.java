/*
 * ZipUtil
 * Version : 1.0
 * 2016-04-22
 * CopyRight (c) Huami Company 2015
 *      All rights reserved
 */
package me.dan.testproj;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import android.text.TextUtils;
import android.util.Log;

/**
 * @author DanLv
 */
public class ZipUtil {
    private static final String TAG = "DDDD";

    public static void unzip(File zipFile) {
        unzip(zipFile, zipFile.getParent() + "/");
    }

    public static void unzip(File zipFile, String location) {
        if (zipFile == null) {
            throw new IllegalArgumentException("Zip file is NULL!!");
        }
        if (TextUtils.isEmpty(location)) {
            throw new IllegalArgumentException("Unzip to location is EMPTY!!");
        }

        Log.i(TAG, "Unzip File : " + zipFile + ", isExist : " + zipFile.exists() + ", To :" +
                location);
        try {
            if (!zipFile.isDirectory()) {
                zipFile.mkdirs();
            }

            try (ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFile))) {
                ZipEntry zipEntry;
                while ((zipEntry = zipIn.getNextEntry()) != null) {
                    String unzipPath = location + zipEntry.getName();

                    if (zipEntry.isDirectory()) {
                        File unzipFile = new File(unzipPath);
                        if (!unzipFile.isDirectory()) {
                            unzipFile.mkdirs();
                        }
                    } else {
                        try (FileOutputStream fOut = new FileOutputStream(unzipPath, false)) {
                            for (int b = zipIn.read(); b != -1; b = zipIn.read()) {
                                fOut.write(b);
                            }
                            zipIn.closeEntry();
                        } catch (Exception e) {
                            Log.e(TAG, "Unzip exception!!", e);
                        }
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Unzip exception", e);
        }
        Log.i(TAG, "Unzip File : " + zipFile + " Success!!");
    }
}
