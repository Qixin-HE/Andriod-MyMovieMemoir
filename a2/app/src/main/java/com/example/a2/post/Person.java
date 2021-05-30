package com.example.a2.post;


public class Person {
    private int id;
    private String fname;
    private String lname;
    private String gender;
    private String dob;
    private int snumber;
    private String sname;
    private String state;
    private String postcode;
    private Credentials credentialsid;

    public Person(int id, String fname, String lname, String gender, String dob, int snumber, String sname, String state, String postcode) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.gender = gender;
        this.dob = dob;
        this.snumber = snumber;
        this.sname = sname;
        this.state = state;
        this.postcode = postcode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public int getSnumber() {
        return snumber;
    }

    public void setSnumber(int snumber) {
        this.snumber = snumber;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public void setCredentialsid(int id, String username, String hash, String signupdate) {
        credentialsid = new Credentials(id, username, hash, signupdate);
    }


    public int getCredentialsid() {
        return credentialsid.getId();
    }


    /*
    public Credentials getCredentialsid() {
        return credentialsid;
    }

    public void setCredentialsid(Credentials credentialsid) {
        this.credentialsid = credentialsid;
    }

     */
}
