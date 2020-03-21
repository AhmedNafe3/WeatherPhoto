package com.ahmednafe3.photoweather.moduls.gallery.data.model;

public class PhotoModel {
    private String picturName;
    private String picturePath;
    private String imageUri;

    public PhotoModel() {
    }

    public PhotoModel(String picturName, String picturePath, String imageUri) {
        this.picturName = picturName;
        this.picturePath = picturePath;
        this.imageUri = imageUri;
    }

    public String getPicturName() {
        return picturName;
    }

    public void setPicturName(String picturName) {
        this.picturName = picturName;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}
