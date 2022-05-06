package com.example.upload.domain;

import java.util.List;

public class Item {

    private Long id;
    private String itemName;
    private UploadFile attachFile;
    private List<UploadFile> imageFiles;

    public Item() {
    }

    public Item(Long id, String itemName, UploadFile attachFile, List<UploadFile> imageFiles) {
        this.id = id;
        this.itemName = itemName;
        this.attachFile = attachFile;
        this.imageFiles = imageFiles;
    }

    public Long getId() {
        return id;
    }

    public String getItemName() {
        return itemName;
    }

    public UploadFile getAttachFile() {
        return attachFile;
    }

    public List<UploadFile> getImageFiles() {
        return imageFiles;
    }
}
