package com.oilpalm3f.nursery.dbmodels;

public class NurseryRMTransctions {

    private  String TransactionId;
    private int ActivityTypeId;
    private  int StatusTypeId;
    private String CreatedDate;




    public String getTransactionId() {
        return TransactionId;
    }

    public void setTransactionId(String transactionId) {
        TransactionId = transactionId;
    }


    public int getActivityTypeId() {
        return ActivityTypeId;
    }

    public void setActivityTypeId(int activityTypeId) {
        ActivityTypeId = activityTypeId;
    }

    public int getStatusTypeId() {
        return StatusTypeId;
    }

    public void setStatusTypeId(int statusTypeId) {
        StatusTypeId = statusTypeId;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }


}


