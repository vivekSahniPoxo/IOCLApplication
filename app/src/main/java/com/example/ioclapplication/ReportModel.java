package com.example.ioclapplication;

public class ReportModel {

    String tagId,location,hhrId,readTime,asset_ID,assetName;

    public ReportModel(String tagId, String location, String hhrId, String readTime,String asset_ID,String assetName) {
        this.tagId = tagId;
        this.location = location;
        this.hhrId = hhrId;
        this.readTime = readTime;
        this.asset_ID = asset_ID;
        this.assetName = assetName;
    }




    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getHhrId() {
        return hhrId;
    }

    public void setHhrId(String hhrId) {
        this.hhrId = hhrId;
    }

    public String getReadTime() {
        return readTime;
    }

    public void setReadTime(String readTime) {
        this.readTime = readTime;
    }

    public String setSetAssetID(String asset_ID){
        this.asset_ID = asset_ID;
        return asset_ID;
    }

    public String setAssetName(String assetName){
        this.assetName = assetName;
        return assetName;
    }

    public String getAssetID() {
        return asset_ID;
    }
    public String getAssetName() {
        return assetName;
    }
}
