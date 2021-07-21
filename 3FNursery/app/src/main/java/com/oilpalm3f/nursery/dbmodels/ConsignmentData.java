package com.oilpalm3f.nursery.dbmodels;

public class ConsignmentData {

    private  String ConsignmentCode;
    private String Originname;
    private String Vendorname;
    private String Varietyname;

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
