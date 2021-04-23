package com.sam.service;

import com.sam.dao.ReimbursementDAO;
import com.sam.dao.ReimbursementDaoImpl;
import com.sam.model.AddReimbursement;
import com.sam.model.Reimbursement;


import java.util.ArrayList;
import java.util.List;


public class ReimbServiceImpl implements ReimbService{
    ReimbursementDAO reimbursementDAO = new ReimbursementDaoImpl();

    @Override
    public List<Reimbursement> getAllReimbursement() {
        return reimbursementDAO.selectAllReimbursement();
    }

    @Override
    public List<Reimbursement> getAllReimbursementByAuthorID(int id) {
        return reimbursementDAO.selectReimbursementByAuthorID(id);
    }

    @Override
    public List<Double> getInfo(int id) {

        List<Double> list = new ArrayList<>();
        list.add((double) reimbursementDAO.countOfType("approved",id));
        list.add((double) reimbursementDAO.countOfType("denied",id));
        list.add(Math.round(reimbursementDAO.sumOfApprovedAmount(id)* 100.0) / 100.0);
        list.add((double) reimbursementDAO.countOfType("pending",id));
        return list;
    }

    @Override
    public List<Double> getAllInfo() {

        List<Double> list = new ArrayList<>();
        list.add((double) reimbursementDAO.countOfType("approved"));
        list.add((double) reimbursementDAO.countOfType("denied"));
        list.add(Math.round(reimbursementDAO.sumOfApprovedAmount()* 100.0) / 100.0);
        list.add((double) reimbursementDAO.countOfType("pending"));
        return list;
    }

    @Override
    public void addNewReimbursement(AddReimbursement newReimbursement) {
        Reimbursement reimbursement = new Reimbursement(0
                ,newReimbursement.getAmount()
                ,null
                ,null
                ,newReimbursement.getDescription()
                ,newReimbursement.getUserName()
                ,""
                ,""
                ,newReimbursement.getReimbursementType());
        int reID = reimbursementDAO.insertReimbursement(reimbursement);
        reimbursementDAO.insertImages(newReimbursement,reID);

    }

    @Override
    public void updateStatus(int reimbID, int resolverID, int status ) {

        reimbursementDAO.updateReimbursementStatus(reimbID,resolverID,status);

    }



    @Override
    public List<String> getImagesByID(int id) {
        return reimbursementDAO.selectImagesByID(id);
    }


    @Override
    public Reimbursement getReimbursementByID(int id) {
        return reimbursementDAO.selectReimbursementById(id);
    }

    @Override
    public String getUserEmail(int id) {
        return reimbursementDAO.selectUserEmailByReimbursementID(id);
    }


}
