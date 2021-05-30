package com.example.a2.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

//import java.sql.Date;

@Entity
public class Credentials {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "user_name")
    public String userName;

    @ColumnInfo(name = "password_hash")
    public String passwordHash;

    @ColumnInfo(name = "sign_up_date")
    public Date signUpDate;

    public Credentials(String userName, String passwordHash, Date signUpDate) {

        this.userName = userName;
        this.passwordHash = passwordHash;
        this.signUpDate = signUpDate;
    }

    public Integer getId() {
        return uid;
    }

    public void setId(Integer uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return userName;
    }

    public void setUsername(String userName) {
        this.userName = userName;
    }

    public String getPasswordhash() {
        return passwordHash;
    }

    public void setPasswordhash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Date getSignupdate() {
        return signUpDate;
    }

    public void setSignupdate(Date signUpDate) {
        this.signUpDate = signUpDate;
    }

    /*
    @XmlTransient
    public Collection<Person> getPersonCollection() {
        return personCollection;
    }

    public void setPersonCollection(Collection<Person> personCollection) {
        this.personCollection = personCollection;
    }

     */

}
