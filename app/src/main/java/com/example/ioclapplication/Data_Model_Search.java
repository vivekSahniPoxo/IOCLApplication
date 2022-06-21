package com.example.ioclapplication;

public class Data_Model_Search {

    public String id;
    public String pO_UNIQUE_ID;
    public String saP_ASSET_CODE;
    public String seriaL_NO;
    public String status;
    public String planT_CODE;
    public String employee;
    public String department;
    String rfidtagid ;
    public String warrantY_STATUS;
    public String tracK_ID;
    public String useR_ACK;
    public String asseT_ID;
    public String nonpO_BILLNO;
    public String alloteD_TO_PLANT;
    public String remarks;
    public String amC_ASSET_CATEGORY;
    public String amC_AMOUNT;
    public String amC_AMOUNT_TOTAL;
    public String pmdonelq;
    public String pmdonecq;
    public String fmsperday;
    public String uniquE_ID;
    public String pO_NUMBER;
    public String pO_DATE;
    public String pO_PDF_FILE;
    public String asset;
    public String quantity;
    public String contracT_NUMBER;
    public String completioN_DATE;
    public String vendor;
    public String warrantY_PERIOD;
    public String warrantY_START_DATE;
    public String warrantY_END_DATE;
    public String totaL_VALUE;
    public String oem;
    public String model;
    public String ram;
    public String hdd;
    public String os;
    public String printeR_TYPE;
    public String nO_OF_PORTS;
    public String alloteD_TO_LOCATION;
    public String alloteD_TO_USER;
    public String misC_BILL_NO;
    public String location;
    public String asseT_STATUS;
    String  StatusF="false";
    String Color;

    public Data_Model_Search(String id, String pO_UNIQUE_ID, String saP_ASSET_CODE, String seriaL_NO, String status, String planT_CODE, String employee, String department, String rfidtagid, String warrantY_STATUS, String tracK_ID, String useR_ACK, String asseT_ID, String nonpO_BILLNO, String alloteD_TO_PLANT, String remarks, String amC_ASSET_CATEGORY, String amC_AMOUNT, String amC_AMOUNT_TOTAL, String pmdonelq, String pmdonecq, String fmsperday, String uniquE_ID, String pO_NUMBER, String pO_DATE, String pO_PDF_FILE, String asset, String quantity, String contracT_NUMBER, String completioN_DATE, String vendor, String warrantY_PERIOD, String warrantY_START_DATE, String warrantY_END_DATE, String totaL_VALUE, String oem, String model, String ram, String hdd, String os, String printeR_TYPE, String nO_OF_PORTS, String alloteD_TO_LOCATION, String alloteD_TO_USER, String misC_BILL_NO, String location) {
        this.id = id;
        this.pO_UNIQUE_ID = pO_UNIQUE_ID;
        this.saP_ASSET_CODE = saP_ASSET_CODE;
        this.seriaL_NO = seriaL_NO;
        this.status = status;
        this.planT_CODE = planT_CODE;
        this.employee = employee;
        this.department = department;
        this.rfidtagid = rfidtagid;
        this.warrantY_STATUS = warrantY_STATUS;
        this.tracK_ID = tracK_ID;
        this.useR_ACK = useR_ACK;
        this.asseT_ID = asseT_ID;
        this.nonpO_BILLNO = nonpO_BILLNO;
        this.alloteD_TO_PLANT = alloteD_TO_PLANT;
        this.remarks = remarks;
        this.amC_ASSET_CATEGORY = amC_ASSET_CATEGORY;
        this.amC_AMOUNT = amC_AMOUNT;
        this.amC_AMOUNT_TOTAL = amC_AMOUNT_TOTAL;
        this.pmdonelq = pmdonelq;
        this.pmdonecq = pmdonecq;
        this.fmsperday = fmsperday;
        this.uniquE_ID = uniquE_ID;
        this.pO_NUMBER = pO_NUMBER;
        this.pO_DATE = pO_DATE;
        this.pO_PDF_FILE = pO_PDF_FILE;
        this.asset = asset;
        this.quantity = quantity;
        this.contracT_NUMBER = contracT_NUMBER;
        this.completioN_DATE = completioN_DATE;
        this.vendor = vendor;
        this.warrantY_PERIOD = warrantY_PERIOD;
        this.warrantY_START_DATE = warrantY_START_DATE;
        this.warrantY_END_DATE = warrantY_END_DATE;
        this.totaL_VALUE = totaL_VALUE;
        this.oem = oem;
        this.model = model;
        this.ram = ram;
        this.hdd = hdd;
        this.os = os;
        this.printeR_TYPE = printeR_TYPE;
        this.nO_OF_PORTS = nO_OF_PORTS;
        this.alloteD_TO_LOCATION = alloteD_TO_LOCATION;
        this.alloteD_TO_USER = alloteD_TO_USER;
        this.misC_BILL_NO = misC_BILL_NO;
        this.location = location;
        this.asseT_STATUS = asseT_STATUS;
    }




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getpO_UNIQUE_ID() {
        return pO_UNIQUE_ID;
    }

    public void setpO_UNIQUE_ID(String pO_UNIQUE_ID) {
        this.pO_UNIQUE_ID = pO_UNIQUE_ID;
    }

    public String getSaP_ASSET_CODE() {
        return saP_ASSET_CODE;
    }

    public void setSaP_ASSET_CODE(String saP_ASSET_CODE) {
        this.saP_ASSET_CODE = saP_ASSET_CODE;
    }

    public String getSeriaL_NO() {
        return seriaL_NO;
    }

    public void setSeriaL_NO(String seriaL_NO) {
        this.seriaL_NO = seriaL_NO;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPlanT_CODE() {
        return planT_CODE;
    }

    public void setPlanT_CODE(String planT_CODE) {
        this.planT_CODE = planT_CODE;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getRfidtagid() {
        return rfidtagid;
    }

    public void setRfidtagid(String rfidtagid) {
        this.rfidtagid = rfidtagid;
    }

    public String getWarrantY_STATUS() {
        return warrantY_STATUS;
    }

    public void setWarrantY_STATUS(String warrantY_STATUS) {
        this.warrantY_STATUS = warrantY_STATUS;
    }

    public String getTracK_ID() {
        return tracK_ID;
    }

    public void setTracK_ID(String tracK_ID) {
        this.tracK_ID = tracK_ID;
    }

    public String getUseR_ACK() {
        return useR_ACK;
    }

    public void setUseR_ACK(String useR_ACK) {
        this.useR_ACK = useR_ACK;
    }

    public String getAsseT_ID() {
        return asseT_ID;
    }

    public void setAsseT_ID(String asseT_ID) {
        this.asseT_ID = asseT_ID;
    }

    public String getNonpO_BILLNO() {
        return nonpO_BILLNO;
    }

    public void setNonpO_BILLNO(String nonpO_BILLNO) {
        this.nonpO_BILLNO = nonpO_BILLNO;
    }

    public String getAlloteD_TO_PLANT() {
        return alloteD_TO_PLANT;
    }

    public void setAlloteD_TO_PLANT(String alloteD_TO_PLANT) {
        this.alloteD_TO_PLANT = alloteD_TO_PLANT;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getAmC_ASSET_CATEGORY() {
        return amC_ASSET_CATEGORY;
    }

    public void setAmC_ASSET_CATEGORY(String amC_ASSET_CATEGORY) {
        this.amC_ASSET_CATEGORY = amC_ASSET_CATEGORY;
    }

    public String getAmC_AMOUNT() {
        return amC_AMOUNT;
    }

    public void setAmC_AMOUNT(String amC_AMOUNT) {
        this.amC_AMOUNT = amC_AMOUNT;
    }

    public String getAmC_AMOUNT_TOTAL() {
        return amC_AMOUNT_TOTAL;
    }

    public void setAmC_AMOUNT_TOTAL(String amC_AMOUNT_TOTAL) {
        this.amC_AMOUNT_TOTAL = amC_AMOUNT_TOTAL;
    }

    public String getPmdonelq() {
        return pmdonelq;
    }

    public void setPmdonelq(String pmdonelq) {
        this.pmdonelq = pmdonelq;
    }

    public String getPmdonecq() {
        return pmdonecq;
    }

    public void setPmdonecq(String pmdonecq) {
        this.pmdonecq = pmdonecq;
    }

    public String getFmsperday() {
        return fmsperday;
    }

    public void setFmsperday(String fmsperday) {
        this.fmsperday = fmsperday;
    }

    public String getUniquE_ID() {
        return uniquE_ID;
    }

    public void setUniquE_ID(String uniquE_ID) {
        this.uniquE_ID = uniquE_ID;
    }

    public String getpO_NUMBER() {
        return pO_NUMBER;
    }

    public void setpO_NUMBER(String pO_NUMBER) {
        this.pO_NUMBER = pO_NUMBER;
    }

    public String getpO_DATE() {
        return pO_DATE;
    }

    public void setpO_DATE(String pO_DATE) {
        this.pO_DATE = pO_DATE;
    }

    public String getpO_PDF_FILE() {
        return pO_PDF_FILE;
    }

    public void setpO_PDF_FILE(String pO_PDF_FILE) {
        this.pO_PDF_FILE = pO_PDF_FILE;
    }

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getContracT_NUMBER() {
        return contracT_NUMBER;
    }

    public void setContracT_NUMBER(String contracT_NUMBER) {
        this.contracT_NUMBER = contracT_NUMBER;
    }

    public String getCompletioN_DATE() {
        return completioN_DATE;
    }

    public void setCompletioN_DATE(String completioN_DATE) {
        this.completioN_DATE = completioN_DATE;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getWarrantY_PERIOD() {
        return warrantY_PERIOD;
    }

    public void setWarrantY_PERIOD(String warrantY_PERIOD) {
        this.warrantY_PERIOD = warrantY_PERIOD;
    }

    public String getWarrantY_START_DATE() {
        return warrantY_START_DATE;
    }

    public void setWarrantY_START_DATE(String warrantY_START_DATE) {
        this.warrantY_START_DATE = warrantY_START_DATE;
    }

    public String getWarrantY_END_DATE() {
        return warrantY_END_DATE;
    }

    public void setWarrantY_END_DATE(String warrantY_END_DATE) {
        this.warrantY_END_DATE = warrantY_END_DATE;
    }

    public String getTotaL_VALUE() {
        return totaL_VALUE;
    }

    public void setTotaL_VALUE(String totaL_VALUE) {
        this.totaL_VALUE = totaL_VALUE;
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

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getHdd() {
        return hdd;
    }

    public void setHdd(String hdd) {
        this.hdd = hdd;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getPrinteR_TYPE() {
        return printeR_TYPE;
    }

    public void setPrinteR_TYPE(String printeR_TYPE) {
        this.printeR_TYPE = printeR_TYPE;
    }

    public String getnO_OF_PORTS() {
        return nO_OF_PORTS;
    }

    public void setnO_OF_PORTS(String nO_OF_PORTS) {
        this.nO_OF_PORTS = nO_OF_PORTS;
    }

    public String getAlloteD_TO_LOCATION() {
        return alloteD_TO_LOCATION;
    }

    public void setAlloteD_TO_LOCATION(String alloteD_TO_LOCATION) {
        this.alloteD_TO_LOCATION = alloteD_TO_LOCATION;
    }

    public String getAlloteD_TO_USER() {
        return alloteD_TO_USER;
    }

    public void setAlloteD_TO_USER(String alloteD_TO_USER) {
        this.alloteD_TO_USER = alloteD_TO_USER;
    }

    public String getMisC_BILL_NO() {
        return misC_BILL_NO;
    }

    public void setMisC_BILL_NO(String misC_BILL_NO) {
        this.misC_BILL_NO = misC_BILL_NO;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAsseT_STATUS() {
        return asseT_STATUS;
    }

    public void setAsseT_STATUS(String asseT_STATUS) {
        this.asseT_STATUS = asseT_STATUS;
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
