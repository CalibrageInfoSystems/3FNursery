package com.oilpalm3f.nursery.dbmodels;

public class ExistingData {

    private String  Value;
    private String Field;
    private int ServerUpdatedStatus;

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public String getField() {
        return Field;
    }

    public void setField(String field) {
        Field = field;
    }

    public int getServerUpdatedStatus() {
        return ServerUpdatedStatus;
    }

    public void setServerUpdatedStatus(int serverUpdatedStatus) {
        ServerUpdatedStatus = serverUpdatedStatus;
    }
}
