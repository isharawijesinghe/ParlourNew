package com.ss.parlour.authorizationservice.util.bean.requests;

public class UserRegisterRequestBean {

    private String loginName;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String token;
    private String userActionType;


    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserActionType() {
        return userActionType;
    }

    public void setUserActionType(String userActionType) {
        this.userActionType = userActionType;
    }
}
