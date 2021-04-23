package com.sam.model;

import java.util.ArrayList;
import java.util.List;

public class AddReimbursement {
    private String userName;
    private List<String> images = new ArrayList<>();
    private double amount;
    private String description;
    private String reimbursementType;

    public AddReimbursement() {
    }

    public AddReimbursement(String userName, List<String> images, double amount, String description, String reimbursementType) {
        this.userName = userName;
        this.images = images;
        this.amount = amount;
        this.description = description;
        this.reimbursementType = reimbursementType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReimbursementType() {
        return reimbursementType;
    }

    public void setReimbursementType(String reimbursementType) {
        this.reimbursementType = reimbursementType;
    }

    @Override
    public String toString() {
        return "AddReimbursement{" +
                "userID=" + userName +
                ", images=" + images +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", reimbursementType='" + reimbursementType + '\'' +
                '}';
    }
}
