package com.example.suneet86.cs415project;

import android.app.Application;

/**
 * Created by suneet86 on 21/09/2015.
 */
public class Global extends Application {

    private String userLogged;

    public String getuserLogged() {
        return userLogged;
    }

    public void setUserLogged(String userLogged) {
        this.userLogged = userLogged;
    }
}
