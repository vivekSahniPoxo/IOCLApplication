package com.example.ioclapplication;

public class InventoryDataModel {
    public String employee;
    public String taG_ID;
    String asseT_ID;
    public String pO_NUMBER;
    public String asset;
    public String employeename;
    public String component;
    public String seriaL_NO;
    public String oem;
    public String model;
    String  StatusF="false";
    String Color;

    public InventoryDataModel(String employee, String taG_ID, String asseT_ID, String pO_NUMBER, String asset, String employeename, String component, String seriaL_NO, String oem, String model) {
        this.employee = employee;
        this.taG_ID = taG_ID;
        this.asseT_ID = asseT_ID;
        this.pO_NUMBER = pO_NUMBER;
        this.asset = asset;
        this.employeename = employeename;
        this.component = component;
        this.seriaL_NO = seriaL_NO;
        this.oem = oem;
        this.model = model;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public String getTaG_ID() {
        return taG_ID;
    }

    public void setTaG_ID(String taG_ID) {
        this.taG_ID = taG_ID;
    }

    public String getAsseT_ID() {
        return asseT_ID;
    }

    public void setAsseT_ID(String asseT_ID) {
        this.asseT_ID = asseT_ID;
    }

    public String getpO_NUMBER() {
        return pO_NUMBER;
    }

    public void setpO_NUMBER(String pO_NUMBER) {
        this.pO_NUMBER = pO_NUMBER;
    }

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public String getEmployeename() {
        return employeename;
    }

    public void setEmployeename(String employeename) {
        this.employeename = employeename;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getSeriaL_NO() {
        return seriaL_NO;
    }

    public void setSeriaL_NO(String seriaL_NO) {
        this.seriaL_NO = seriaL_NO;
    }

    public String getOem() {
        return oem;
    }

    public void setOem(String oem) {
        this.oem = oem;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getStatusF() {
        return StatusF;
    }

    public void setStatusF(String statusF) {
        StatusF = statusF;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }
}
