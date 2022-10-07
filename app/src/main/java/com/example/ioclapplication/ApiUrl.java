package com.example.ioclapplication;

public class ApiUrl {
    public static String baseUrl = "http://164.52.223.163:4510/api/";

//    public static String baseUrl =  "http://mudvprfidiis:82/api/";

    public static final String SearchApi = baseUrl + "GetAssetInfoBySearch?AssetId=";
    public static final String IdentifyApi = baseUrl + "GetAssetInfoBySearch?RfidTagId=";
    public static final String AssetIDDAta = baseUrl + "GetAssetInfo?Assetid=";
    public static final String GetAssetID = baseUrl + "GetAssetId";
    public static final String MapRfidID = baseUrl + "MapRfidTag";
    public static final String ScannedItemList = baseUrl + "ReadRfidByDate?Date=";
    public static final String GetMappedITem = baseUrl + "GetMapedItems";
    public static final String WriteRfidDetails = baseUrl + "WriteRfidTagDetails";
    public static final String LocationID = baseUrl + "GetLocation";
    public static final String UpdateLocation = baseUrl + "UpdateLocation";
}

