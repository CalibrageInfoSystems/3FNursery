package com.oilpalm3f.nursery.dbmodels;

public class ConsignmentData {

    private  String ConsignmentCode;
    private String Originname;
    private String Vendorname;
    private String Varietyname;
    private  int EstimatedQuantity;
    private String CreatedDate;
    private String ArrivedDate;
    private int ArrivedQuantity;

    public int getEstimatedQuantity() {
        return EstimatedQuantity;
    }

    public void setEstimatedQuantity(int estimatedQuantity) {
        EstimatedQuantity = estimatedQuantity;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getArrivedDate() {
        return ArrivedDate;
    }

    public void setArrivedDate(String arrivedDate) {
        ArrivedDate = arrivedDate;
    }

    public int getArrivedQuantity() {
        return ArrivedQuantity;
    }

    public void setArrivedQuantity(int arrivedQuantity) {
        ArrivedQuantity = arrivedQuantity;
    }

    public String getConsignmentCode() {
        return ConsignmentCode;
    }

    public void setConsignmentCode(String consignmentCode) {
        ConsignmentCode = consignmentCode;
    }

    public String getOriginname() {
        return Originname;
    }

    public void setOriginname(String originname) {
        Originname = originname;
    }

    public String getVendorname() {
        return Vendorname;
    }

    public void setVendorname(String vendorname) {
        Vendorname = vendorname;
    }

    public String getVarietyname() {
        return Varietyname;
    }

    public void setVarietyname(String varietyname) {
        Varietyname = varietyname;
    }
}
