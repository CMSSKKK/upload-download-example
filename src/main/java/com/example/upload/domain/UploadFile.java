package com.example.upload.domain;

public class UploadFile {

    private String uploadFilename;
    private String storedFilename;

    public UploadFile(String uploadFilename, String storedFilename) {
        this.uploadFilename = uploadFilename;
        this.storedFilename = storedFilename;
    }

    public String getUploadFilename() {
        return uploadFilename;
    }

    public String getStoredFilename() {
        return storedFilename;
    }


}
