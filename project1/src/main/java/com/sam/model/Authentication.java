package com.sam.model;

public class Authentication {
    private String email;
    private String password;
    private String loginType;
    private String rememberMe;

    public Authentication() {
    }

    public Authentication(String email, String password, String loginType, String rememberMe) {
        this.email = email;
        this.password = password;
        this.loginType = loginType;
        this.rememberMe = rememberMe;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public String getRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(String rememberMe) {
        this.rememberMe = rememberMe;
    }

    @Override
    public String toString() {
        return "Authentication{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", userType='" + loginType + '\'' +
                '}';
    }
}
