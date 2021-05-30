package com.example.a2.Parcelable;

import android.os.Parcel;
import android.os.Parcelable;

public class Person implements Parcelable {
    private int credentialsid = 0;
    private String fname = "";


    public Person(Parcel in) {
        this.credentialsid = in.readInt();
        this.fname = in.readString();
    }

    public Person(int credentialsid, String fname) {
        this.credentialsid = credentialsid;
        this.fname = fname;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(credentialsid);
        parcel.writeString(fname);
    }

    public int describeContents() {
        return 0;
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getFname() {
        return fname;
    }

    public int getCredentialsid() {
        return credentialsid;
    }

    public void setCredentialsid(int credentialsid) {
        this.credentialsid = credentialsid;
    }
}
