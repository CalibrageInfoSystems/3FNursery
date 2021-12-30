package com.oilpalm3f.nursery.dbmodels;

public class NurseryVisitLog {
    private Integer Id;

    private  String NurseryCode;
    private  int LogTypeId;
    private String CosignmentCode;
    private String ClientName;
    private String MobileNumber;
    private String Location;
    private Double Latitude;
    private Double Longitude;
    private String Comments;
    private String FileName;
    private String FileLocation;
    private String FileExtension;
    private int CreatedByUserId;
    private String CreatedDate;
    private int ServerUpdatedStatus;
    private String ImageString;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getNurseryCode() {
        return NurseryCode;
    }

    public void setNurseryCode(String nurseryCode) {
        NurseryCode = nurseryCode;
    }

    public int getLogTypeId() {
        return LogTypeId;
    }

    public void setLogTypeId(int logTypeId) {
        LogTypeId = logTypeId;
    }

    public String getCosignmentCode() {
        return CosignmentCode;
    }

    public void setCosignmentCode(String cosignmentCode) {
        CosignmentCode = cosignmentCode;
    }

    public String getClientName() {
        return ClientName;
    }

    public void setClientName(String clientName) {
        ClientName = clientName;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public Double getLatitude() {
        return Latitude;
    }

    public void setLatitude(Double latitude) {
        Latitude = latitude;
    }

    public Double getLongitude() {
        return Longitude;
    }

    public void setLongitude(Double longitude) {
        Longitude = longitude;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }

    public String getFileLocation() {
        return FileLocation;
    }

    public void setFileLocation(String fileLocation) {
        FileLocation = fileLocation;
    }

    public String getFileExtension() {
        return FileExtension;
    }

    public void setFileExtension(String fileExtension) {
        FileExtension = fileExtension;
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

    public int getServerUpdatedStatus() {
        return ServerUpdatedStatus;
    }

    public void setServerUpdatedStatus(int serverUpdatedStatus) {
        ServerUpdatedStatus = serverUpdatedStatus;
    }

    public String getImageString() {
        return ImageString;
    }

    public void setImageString(String imageString) {
        ImageString = imageString;
    }
}
