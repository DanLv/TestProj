/*
 * ZipTest
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

import android.os.Environment;
import android.test.AndroidTestCase;
import android.text.TextUtils;
import android.util.Log;

/**
 * @author DanLv
 */
public class ZipTest extends AndroidTestCase {
    @Override
    protected void setUp() throws Exception {
    }

    public void testUnzipFile() {
        File file = new File(Environment.getExternalStorageDirectory() + "/agps_data");
        ZipUtil.unzip(file);
    }
}
