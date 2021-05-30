package com.example.a2.post;

import java.util.Date;

public class Credentials {
    private int id;
    private String username;
    private String passwordhash;
    private String signupdate;

    public Credentials(int id) {
        this.id = id;
    }

    public Credentials(int id, String username, String passwordhash, String signupdate) {
        this.id = id;
        this.username = username;
        this.passwordhash = passwordhash;
        this.signupdate = signupdate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordhash() {
        return passwordhash;
    }

    public void setPasswordhash(String passwordhash) {
        this.passwordhash = passwordhash;
    }

    public String getSignupdate() {
        return signupdate;
    }

    public void setSignupdate(String signupdate) {
        this.signupdate = signupdate;
    }
}
