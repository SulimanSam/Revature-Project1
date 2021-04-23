package com.sam.model;

import java.util.Arrays;

public class Image {
    private int imageId;
    private int reimbId;
    private byte[] image;

    public Image() {
    }

    public Image(int imageId, int reimbId, byte[] image) {
        this.imageId = imageId;
        this.reimbId = reimbId;
        this.image = image;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getReimbId() {
        return reimbId;
    }

    public void setReimbId(int reimbId) {
        this.reimbId = reimbId;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Image{" +
                "imageId=" + imageId +
                ", reimbId=" + reimbId +
                ", image=" + Arrays.toString(image) +
                '}';
    }
}
