package com.oilpalm3f.nursery.dbmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NurseryAcitivity {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("ActivityTypeId")
    @Expose
    private Integer activityTypeId;
    @SerializedName("Code")
    @Expose
    private String code;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("TargetDays")
    @Expose
    private Integer targetDays;

    @SerializedName("IsMultipleEntries")
    @Expose
    private String IsMultipleEntries;

    @SerializedName("IsActive")
    @Expose
    private int isActive;
    @SerializedName("CreatedByUserId")
    @Expose
    private Integer createdByUserId;
    @SerializedName("CreatedDate")
    @Expose
    private String createdDate;
    @SerializedName("UpdatedByUserId")
    @Expose
    private Integer updatedByUserId;
    @SerializedName("UpdatedDate")
    @Expose
    private String updatedDate;
    @SerializedName("ServerUpdatedStatus")
    @Expose
    private int serverUpdatedStatus;

    public String getIsMultipleEntries() {
        return IsMultipleEntries;
    }

    public void setIsMultipleEntries(String isMultipleEntries) {
        IsMultipleEntries = isMultipleEntries;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getActivityTypeId() {
        return activityTypeId;
    }

    public void setActivityTypeId(Integer activityTypeId) {
        this.activityTypeId = activityTypeId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTargetDays() {
        return targetDays;
    }

    public void setTargetDays(Integer targetDays) {
        this.targetDays = targetDays;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public Integer getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(Integer createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getUpdatedByUserId() {
        return updatedByUserId;
    }

    public void setUpdatedByUserId(Integer updatedByUserId) {
        this.updatedByUserId = updatedByUserId;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public int getServerUpdatedStatus() {
        return serverUpdatedStatus;
    }

    public void setServerUpdatedStatus(int serverUpdatedStatus) {
        this.serverUpdatedStatus = serverUpdatedStatus;
    }
}
