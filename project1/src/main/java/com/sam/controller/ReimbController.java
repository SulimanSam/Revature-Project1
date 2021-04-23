package com.sam.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sam.model.AddReimbursement;

import com.sam.model.Reimbursement;
import com.sam.model.User;
import com.sam.service.ReimbService;
import com.sam.service.ReimbServiceImpl;

import io.javalin.http.Context;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import java.util.List;
import java.util.Objects;

public class ReimbController {

    private static final ReimbService reimbService = new ReimbServiceImpl();
    private static List<Reimbursement> reimbursements = null;

    public ReimbController(){


    }

    public static void denyReimbursementin(Context context) {
        System.out.println("in deny");
        AddReimbursement info=context.bodyAsClass(AddReimbursement.class);
        int reimbID = Integer.parseInt(info.getUserName());
        int resolverID = Integer.parseInt(info.getDescription());
        reimbService.updateStatus(reimbID,resolverID,3);
        senEmail("denied",reimbService.getUserEmail(reimbID));
        context.result("1");

    }

    public static void approveReimbursementin(Context context) {
        System.out.println("in approve");
        AddReimbursement info=context.bodyAsClass(AddReimbursement.class);
        int reimbID = Integer.parseInt(info.getUserName());
        int resolverID = Integer.parseInt(info.getDescription());
        reimbService.updateStatus(reimbID,resolverID,2);
        senEmail("Approved",reimbService.getUserEmail(reimbID));
        context.result("1");

    }

    public static void getReimbursementByID(Context context) {

        int reimbID = Integer.parseInt(context.pathParam("reimbID"));
        Reimbursement reimbursement = reimbService.getReimbursementByID(reimbID);

        context.json(reimbursement);
    }


    public static void approveReimbursement(Context context) {

        int reimbID = Integer.parseInt(Objects.requireNonNull(context.formParam("reimbID")));
        int resolverID = Integer.parseInt(Objects.requireNonNull(context.formParam("resolverID")));
        reimbService.updateStatus(reimbID,resolverID,2);
        senEmail("Approved",reimbService.getUserEmail(reimbID));
        context.redirect("/ManagerHome.html");
    }

    public static void denyReimbursement(Context context) {

        int reimbID = Integer.parseInt(context.formParam("reimbID"));
        int resolverID = Integer.parseInt(context.formParam("resolverID"));
        reimbService.updateStatus(reimbID,resolverID,3);
        senEmail("denied",reimbService.getUserEmail(reimbID));
        context.redirect("/ManagerHome.html");
    }

    public static void getAllReimbursements(Context context) {
        reimbursements= reimbService.getAllReimbursement();


        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.putPOJO("json",reimbursements);

        objectNode.putPOJO("info", reimbService.getAllInfo());
        context.json(objectNode).status(200);
    }

    public static void getImagesByID(Context context) {
        int reimbID = Integer.parseInt(context.pathParam("reimbID"));
        List<String> images = reimbService.getImagesByID(reimbID);

        context.json(images);
    }

    public static void newRequest(Context context) {
        AddReimbursement reimbursement = context.bodyAsClass(AddReimbursement.class);
        reimbService.addNewReimbursement(reimbursement);
        context.result("1");
    }

    public static void getUserReimbursements(Context context){
        User user = context.sessionAttribute("currentUser");

        reimbursements= reimbService.getAllReimbursementByAuthorID(user.getUserID());


        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.putPOJO("json",reimbursements);

        objectNode.putPOJO("info", reimbService.getInfo(user.getUserID()));
        context.json(objectNode).status(200);
    }

    public static void senEmail(String status, String userEmail){
        try {
            Email email = new SimpleEmail();
            email.setHostName("smtp.googlemail.com");
            email.setSmtpPort(465);
            email.setAuthenticator(
                    new DefaultAuthenticator(System.getenv("P1_EMAILING_ADDRESS")
                            , System.getenv("P1_EMAILING_PASSWORD")));
            email.setSSLOnConnect(true);
            email.setFrom(System.getenv("P1_EMAILING_ADDRESS"));
            email.setSubject(status);
            email.setMsg("Yor Request>>" + status);
            email.addTo(userEmail);
            email.send(); // will throw email-exception if something is wrong
        } catch (EmailException e) {
            e.printStackTrace();
        }
    }

}

