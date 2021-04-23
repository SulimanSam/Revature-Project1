package com.sam.frontcontroller;


import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.Map;

public class FrontController {


    Javalin app;
    Dispatcher dispatcher;

    public  FrontController(Javalin app){
        this.app = app;
        dispatcher = new Dispatcher(app);

        app.before("/login.html",FrontController::loginAuth);
        app.before("/home.html",FrontController::genAuth);
        app.before("/details.html",FrontController::genAuth);
        app.before("/images.html",FrontController::genAuth);
        app.before("/ManagerHome.html",FrontController::genAuth);
        app.before("/newRequest.html",FrontController::genAuth);
        app.before("/newUser.html",FrontController::genAuth);
        app.before("/profile.html",FrontController::genAuth);

    }

    private static void genAuth(Context context) {
        Map<String, String> userSession = context.sessionAttributeMap();
        if(userSession.get("loginSession")==null){
            context.redirect("/login.html");
        }

    }

    private static void loginAuth(Context context) {
        Map<String, String> userSession = context.sessionAttributeMap();
        if(userSession.get("loginSession")!=null) {
            if (userSession.get("loginRemember").equals("true")) {
                if (userSession.get("loginType").equals("manager")) {
                    context.redirect("/ManagerHome.html");
                } else {
                    context.redirect("/home.html");
                }
                //context.redirect("/home.html");
            }
        }
    }

}

