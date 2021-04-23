package com.sam.controller;

import com.sam.model.Authentication;
import com.sam.model.User;
import com.sam.service.UsersService;
import com.sam.service.UsersServiceImpl;
import io.javalin.http.Context;
import org.apache.commons.mail.*;


import static com.sam.service.UsersServiceImpl.currentUser;

public class UsersController{

    private static final UsersService usersService= new UsersServiceImpl();


    public UsersController(){

    }

    public static void logout(Context context) {

        context.sessionAttribute("currentUser", null);
        context.sessionAttribute("loginSession", null);
        context.sessionAttribute("loginType", null);
        context.sessionAttribute("loginRemember", null);
        context.redirect("/login.html");
    }


    public static void getAllUsers(Context context) {
        context.json(usersService.geAllUsers());
    }

    public static void addUser(Context context) {
        User user= context.bodyAsClass(User.class);

        String message = "Dear "+user.getFirstName()+" "+ user.getLastName()+
                ", \nA new account has been created for you in The Reimbursement System." +
                "\nYour User name is: "+user.getUserName()+
                "\nYour password is :  "+user.getPassword()+
                " Please change your password as soon as possible." +
                "log in and finish setting up your account." +
                " To start using the System, log in at: http://localhost:9001/";
        int result;

        if(usersService.checkIfUserExist(user)){
            context.result(String.valueOf(0));
            return;
        }else{
            result = usersService.createNewUser(user);
        }


        if(result>0){
            try {
                Email email = new SimpleEmail();
                email.setHostName("smtp.googlemail.com");
                email.setSmtpPort(465);
                email.setAuthenticator(
                        new DefaultAuthenticator(System.getenv("P1_EMAILING_ADDRESS")//
                                                , System.getenv("P1_EMAILING_PASSWORD")));//
                email.setSSLOnConnect(true);
                email.setFrom(System.getenv("P1_EMAILING_ADDRESS"));
                email.setSubject("New User Account");
                email.setMsg(message);
                email.addTo(user.getEmail());
                email.send(); // will throw email-exception if something is wrong
            } catch (EmailException e) {
                e.printStackTrace();
            }
            context.result(String.valueOf(result));
        }



    }

    public static void updateUser(Context context) {

        User user= context.bodyAsClass(User.class);
        int result;

        if(user.getIsChange()==1){
            result = usersService.updateUserPassword(user);
        }else{
            result = usersService.updateUser(user);
        }
        context.result(String.valueOf(result));

    }

    public static void getCurrentUser(Context context) {
        User user = context.sessionAttribute("currentUser");
        context.json(user).status(200);
    }

    public static void userAuthentication(Context context){

        Authentication authentication = context.bodyAsClass(Authentication.class);
        int result = usersService.login(authentication.getEmail().toLowerCase(), authentication.getPassword(), authentication.getLoginType(), authentication.getRememberMe());

        if(result==0) {
            System.out.println("wrong user name or password");
            //wrong user name or password

        }else if(result==1){
            //user login as employee or manager
            context.sessionAttribute("currentUser", currentUser);
            context.sessionAttribute("loginSession", "True");
            context.sessionAttribute("loginType", authentication.getLoginType());
            context.sessionAttribute("loginRemember", authentication.getRememberMe());


        }else if(result==2){
            //employee trying to login as manager
            System.out.println("employee trying to login as manager");
        }else if(result==3) {
            //manager trying to login as employee
            context.sessionAttribute("currentUser", currentUser);
            context.sessionAttribute("loginSession", "True");
            context.sessionAttribute("loginType", authentication.getLoginType());
            context.sessionAttribute("loginRemember", authentication.getRememberMe());
        }
        System.out.println(context.sessionAttributeMap());
        context.result(String.valueOf(result)).status(200);
    }
}

