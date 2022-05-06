package com.example.upload.Controller.dto;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ItemForm {

    private String itemName;
    private MultipartFile attachFile;
    private List<MultipartFile> files;

    public ItemForm(String itemName, MultipartFile attachFile, List<MultipartFile> files) {
        this.itemName = itemName;
        this.attachFile = attachFile;
        this.files = files;
    }

    public String getItemName() {
        return itemName;
    }

    public MultipartFile getAttachFile() {
        return attachFile;
    }

    public List<MultipartFile> getFiles() {
        return files;
    }

    @Override
    public String toString() {
        return "ItemForm{" +
                "itemName='" + itemName + '\'' +
                ", attachFile=" + attachFile +
                ", files=" + files +
                '}';
    }
}
