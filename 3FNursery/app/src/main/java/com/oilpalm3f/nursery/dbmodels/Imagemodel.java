package com.oilpalm3f.nursery.dbmodels;

public class Imagemodel {
    private int ImageString;
    private String FileName;
    private String FileLocation;
    private String FileExtension;
    public Imagemodel(int imageString) {
        ImageString = imageString;
    }

    public int getImageString() {
        return ImageString;
    }

    public void setImageString(int imageString) {
        ImageString = imageString;
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


}

