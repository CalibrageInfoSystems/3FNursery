package com.oilpalm3f.nursery.dbmodels;

public class NurseryIrrigationLog {



    int Id;
    String LogDate;
    String IrrigationCode;
    double RegularMale;
    double RegularFemale;
    double ContractMale;
    double ContractFemale;
    int StatusTypeId;
    String Comments;
    int IsActive;
    int CreatedByUserId;
    String CreatedDate;
    int UpdatedByUserId;
    String UpdatedDate;
    int ServerUpdatedStatus;

    public String getIrrigationCode() {
        return IrrigationCode;
    }

    public void setIrrigationCode(String irrigationCode) {
        IrrigationCode = irrigationCode;
    }



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



    public double getRegularMale() {
        return RegularMale;
    }

    public void setRegularMale(double regularMale) {
        RegularMale = regularMale;
    }

    public double getRegularFemale() {
        return RegularFemale;
    }

    public void setRegularFemale(double regularFemale) {
        RegularFemale = regularFemale;
    }

    public double getContractMale() {
        return ContractMale;
    }

    public void setContractMale(double contractMale) {
        ContractMale = contractMale;
    }

    public double getContractFemale() {
        return ContractFemale;
    }

    public void setContractFemale(double contractFemale) {
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
