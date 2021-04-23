package com.sam.service;

import com.sam.model.AddReimbursement;
import com.sam.model.Reimbursement;

import java.util.List;

public interface ReimbService {

    List<Reimbursement> getAllReimbursement();
    List<Reimbursement> getAllReimbursementByAuthorID(int id);
    List<Double> getInfo(int id);

    List<Double> getAllInfo();

    void addNewReimbursement(AddReimbursement reimbursement);

    void updateStatus(int reimbID, int resolverID, int status);

    List<String> getImagesByID(int id);

    Reimbursement getReimbursementByID(int id);

    String getUserEmail(int id);
}
