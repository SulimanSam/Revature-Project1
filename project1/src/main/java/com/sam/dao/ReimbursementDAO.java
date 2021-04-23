package com.sam.dao;

import com.sam.model.AddReimbursement;
import com.sam.model.Reimbursement;

import java.util.List;

public interface ReimbursementDAO {

    //CRUD METHODS ONLY

    //CREATE
    int insertReimbursement(Reimbursement reimbursement);
    void insertImages(AddReimbursement reimbursement,int reimbursementID);

    //READ All
    List<Reimbursement> selectAllReimbursement();
    //Read By id
    Reimbursement selectReimbursementById(int id);
    //Read by authorID
    List<Reimbursement> selectReimbursementByAuthorID(int authorID);
    //Read Image
    List<String> selectImagesByID(int id);

    String selectUserEmailByReimbursementID(int id);

    void updateReimbursementStatus(int reimbID, int resolverID, int status);

    //update
    void updateReimbursement(Reimbursement reimbursement);

    //DELETE
    void deleteReimbursement(Reimbursement reimbursement);

    int countOfType(String status, int authorID);

    int countOfType(String status);

    double sumOfApprovedAmount(int authorID);

    double sumOfApprovedAmount();
}
