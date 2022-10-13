package com.ss.parlour.authenticationservice.domain;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.sql.Timestamp;

@Table("user")
public class User {

    @PrimaryKey
    private String loginName;
    private String address;
    private Timestamp createdDate;
    private String defaultEmail;
    private String defaultMobile;
    private String firstName;
    private int gender;
    private String loginPw;
    private String otherEmails;
    private String otherMobiles;
    private String otherNames;
    private String secondName;
    private String title;
    private String role;
    private boolean enabled = false;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public String getDefaultEmail() {
        return defaultEmail;
    }

    public void setDefaultEmail(String defaultEmail) {
        this.defaultEmail = defaultEmail;
    }

    public String getDefaultMobile() {
        return defaultMobile;
    }

    public void setDefaultMobile(String defaultMobile) {
        this.defaultMobile = defaultMobile;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getLoginPw() {
        return loginPw;
    }

    public void setLoginPw(String loginPw) {
        this.loginPw = loginPw;
    }

    public String getOtherEmails() {
        return otherEmails;
    }

    public void setOtherEmails(String otherEmails) {
        this.otherEmails = otherEmails;
    }

    public String getOtherMobiles() {
        return otherMobiles;
    }

    public void setOtherMobiles(String otherMobiles) {
        this.otherMobiles = otherMobiles;
    }

    public String getOtherNames() {
        return otherNames;
    }

    public void setOtherNames(String otherNames) {
        this.otherNames = otherNames;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
