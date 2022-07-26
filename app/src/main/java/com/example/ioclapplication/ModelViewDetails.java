package com.example.ioclapplication;

public class ModelViewDetails {
    public String id;
    public String tagId;
    public String location;
    public String hhrId;
    public String readTime;
      public String AssetId;
      public String Assetname;

    public ModelViewDetails(String id, String tagId, String location, String hhrId, String readTime, String assetId, String assetname) {
        this.id = id;
        this.tagId = tagId;
        this.location = location;
        this.hhrId = hhrId;
        this.readTime = readTime;
        AssetId = assetId;
        Assetname = assetname;
    }

    public String getAssetId() {
        return AssetId;
    }

    public void setAssetId(String assetId) {
        AssetId = assetId;
    }

    public String getAssetname() {
        return Assetname;
    }

    public void setAssetname(String assetname) {
        Assetname = assetname;
    }

    public ModelViewDetails() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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




}
