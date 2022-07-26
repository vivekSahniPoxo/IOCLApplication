package com.example.ioclapplication;

public class ReportModel {

    String tagId,location,hhrId,readTime;

    public ReportModel(String tagId, String location, String hhrId, String readTime) {
        this.tagId = tagId;
        this.location = location;
        this.hhrId = hhrId;
        this.readTime = readTime;
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
