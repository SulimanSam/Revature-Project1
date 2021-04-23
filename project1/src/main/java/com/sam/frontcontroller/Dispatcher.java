package com.sam.frontcontroller;



import com.sam.controller.ReimbController;
import com.sam.controller.UsersController;
import io.javalin.Javalin;

public class Dispatcher {

    Javalin app;
    public Dispatcher(Javalin app){

        this.app=app;

        app.get("/logout", UsersController::logout);
        app.post("/login", UsersController::userAuthentication);
        app.get("/getUserInfo", UsersController::getCurrentUser);
        app.get("/getAllUsers", UsersController::getAllUsers);
        app.post("/userUpdate", UsersController::updateUser);
        app.post("/newUser", UsersController::addUser);


        app.get("/getReimbursements", ReimbController::getUserReimbursements);
        app.post("/newRequest", ReimbController::newRequest);
        app.get("/:reimbID/getImages", ReimbController::getImagesByID);
        app.get("/getAllReimbursements", ReimbController::getAllReimbursements);
        app.post("/reimbursementApprove", ReimbController::approveReimbursement);
        app.post("/reimbursementDeny", ReimbController::denyReimbursement);
        app.post("/reimbursementApprove2", ReimbController::approveReimbursementin);
        app.post("/reimbursementDeny2", ReimbController::denyReimbursementin);
        app.get("/:reimbID/getReimbursement", ReimbController::getReimbursementByID);



    }
}
