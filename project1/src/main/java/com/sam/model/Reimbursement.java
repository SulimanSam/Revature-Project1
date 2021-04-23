package com.sam.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


public class Reimbursement {
    private int reimbursementID;
    private double amount;
    private Date submitDate;
    private Date resolveDate;
    private String description;
    private List<Image> images =new ArrayList<>();
    private String author;
    private String resolver;
    private String status;
    private String type;

    public Reimbursement() {
    }
//org.apache.commons.codec.binary.Base64.decodeBase64(image);
    public Reimbursement(int reimbursementID
            , double amount
            , Date submitDate
            , Date resolveDate
            , String description
            , String author, String resolver, String status, String type) {
        this.reimbursementID = reimbursementID;
        this.amount = amount;
        this.submitDate = submitDate;
        this.resolveDate = resolveDate;
        this.description = description;
        this.author = author;
        this.resolver = resolver;
        this.status = status;
        this.type = type;
    }

    public int getReimbursementID() {
        return reimbursementID;
    }

    public void setReimbursementID(int reimbursementID) {
        this.reimbursementID = reimbursementID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }

    public Date getResolveDate() {
        return resolveDate;
    }

    public void setResolveDate(Date resolveDate) {
        this.resolveDate = resolveDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getResolver() {
        return resolver;
    }

    public void setResolver(String resolver) {
        this.resolver = resolver;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Reimbursement{" +
                "reimbursementID=" + reimbursementID +
                ", Amount=" + amount +
                ", submitDate=" + submitDate +
                ", resolveDate=" + resolveDate +
                ", description='" + description + '\'' +
                ", images=" + images +
                ", author='" + author + '\'' +
                ", resolver='" + resolver + '\'' +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
