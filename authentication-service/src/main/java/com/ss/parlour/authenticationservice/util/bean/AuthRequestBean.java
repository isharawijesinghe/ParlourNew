package com.ss.parlour.authenticationservice.util.bean;

public class AuthRequestBean {
    private String loginName;
    private String pw;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }
}
