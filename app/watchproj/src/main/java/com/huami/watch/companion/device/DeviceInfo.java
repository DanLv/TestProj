/* Copyright 2015 Huami Inc. All rights reserved. */
package com.huami.watch.companion.device;

/**
 * @author DanLv
 */
public class DeviceInfo {
    private String androidDeviceId;
    private String cpuId;

    public String getAndroidDeviceId() {
        return androidDeviceId;
    }

    public void setAndroidDeviceId(String androidDeviceId) {
        this.androidDeviceId = androidDeviceId;
    }

    public String getCpuId() {
        return cpuId;
    }

    public void setCpuId(String cpuId) {
        this.cpuId = cpuId;
    }

    @Override
    public String toString() {
        return "<AndroidDeviceId : " + androidDeviceId + ", CpuId : " + cpuId + ">";
    }
}
