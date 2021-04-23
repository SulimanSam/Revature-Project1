package com.sam;

import com.sam.frontcontroller.FrontController;
import com.sam.model.User;
import io.javalin.Javalin;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.create(javelinConfig -> javelinConfig.addStaticFiles("/WebContent")).start(9001);

        app.get("/", context -> context.redirect("/login.html"));
        app.error(404, context -> context.redirect("/page404.html"));
        FrontController fc = new FrontController(app);


    }

}
