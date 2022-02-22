package com.oilpalm3f.nursery.dbmodels;

public class NurseryRMTransctions {
    private  int Id;
    private  String TransactionId;
    private  int ActivityId;
    private  int StatusTypeId;
    private  String Comment;
    private int IsActive;
    private int CreatedByUserId;
    private  String Desc;

    public NurseryRMTransctions(String transactionId, int activityId, int statusTypeId, String comment, String desc, String createdDate) {
        TransactionId = transactionId;
        ActivityId = activityId;
        StatusTypeId = statusTypeId;
        Comment = comment;
        Desc = desc;
        CreatedDate = createdDate;

    }
//    public NurseryRMTransctions(String transactionId, int activityId, String Desc, String comment, String CreatedDate) {
//        TransactionId = transactionId;
//        ActivityId = activityId;
//        Desc = Desc;
//        Comment = comment;
//        CreatedDate = CreatedDate;
//    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTransactionId() {
        return TransactionId;
    }

    public void setTransactionId(String transactionId) {
        TransactionId = transactionId;
    }

    public int getActivityId() {
        return ActivityId;
    }

    public void setActivityId(int activityId) {
        ActivityId = activityId;
    }

    public int getStatusTypeId() {
        return StatusTypeId;
    }

    public void setStatusTypeId(int statusTypeId) {
        StatusTypeId = statusTypeId;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public int getIsActive() {
        return IsActive;
    }

    public void setIsActive(int isActive) {
        IsActive = isActive;
    }

    public int getCreatedByUserId() {
        return CreatedByUserId;
    }

    public void setCreatedByUserId(int createdByUserId) {
        CreatedByUserId = createdByUserId;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public int getUpdatedByUserId() {
        return UpdatedByUserId;
    }

    public void setUpdatedByUserId(int updatedByUserId) {
        UpdatedByUserId = updatedByUserId;
    }

    public String getUpdatedDate() {
        return UpdatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        UpdatedDate = updatedDate;
    }

    public int getServerUpdatedStatus() {
        return ServerUpdatedStatus;
    }

    public void setServerUpdatedStatus(int serverUpdatedStatus) {
        ServerUpdatedStatus = serverUpdatedStatus;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    private String CreatedDate;
    private int UpdatedByUserId;
    private String UpdatedDate;
    private int ServerUpdatedStatus;

}
