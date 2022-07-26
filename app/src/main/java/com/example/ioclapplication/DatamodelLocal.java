package com.example.ioclapplication;

public class DatamodelLocal {
    String DeviceName="";


    public DatamodelLocal(String deviceName) {
        DeviceName = deviceName;
    }

    public DatamodelLocal() {

    }

    public String getDeviceName() {
        return DeviceName;
    }

    public void setDeviceName(String deviceName) {
        DeviceName = deviceName;
    }
}
