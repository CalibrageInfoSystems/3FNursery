package com.oilpalm3f.nursery.dbmodels;

public class NurseryIrrigationLog {

    int Id;
    String LogDate;
    int ConsignmentId;
    int RegularMale;
    int RegularFemale;
    int ContractMale;
    int ContractFemale;
    int StatusTypeId;
    String Comments;
    int IsActive;
    int CreatedByUserId;
    String CreatedDate;
    int UpdatedByUserId;
    String UpdatedDate;
    int ServerUpdatedStatus;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getLogDate() {
        return LogDate;
    }

    public void setLogDate(String logDate) {
        LogDate = logDate;
    }

    public int getConsignmentId() {
        return ConsignmentId;
    }

    public void setConsignmentId(int consignmentId) {
        ConsignmentId = consignmentId;
    }

    public int getRegularMale() {
        return RegularMale;
    }

    public void setRegularMale(int regularMale) {
        RegularMale = regularMale;
    }

    public int getRegularFemale() {
        return RegularFemale;
    }

    public void setRegularFemale(int regularFemale) {
        RegularFemale = regularFemale;
    }

    public int getContractMale() {
        return ContractMale;
    }

    public void setContractMale(int contractMale) {
        ContractMale = contractMale;
    }

    public int getContractFemale() {
        return ContractFemale;
    }

    public void setContractFemale(int contractFemale) {
        ContractFemale = contractFemale;
    }

    public int getStatusTypeId() {
        return StatusTypeId;
    }

    public void setStatusTypeId(int statusTypeId) {
        StatusTypeId = statusTypeId;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
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
}
