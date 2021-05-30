package com.example.a2.post;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Memoir {
    private String movieName;
    private String releaseYear;
    private String IMG;
    private String POSTCODE;
    private String COMMENT;
    private String STARIMG;
    private String WDATE;

    public Memoir(String movieName, String releaseYear, String POSTCODE, String COMMENT, String STARIMG, String WDATE) {
        this.movieName = movieName;
        this.releaseYear = releaseYear;
        this.IMG = "";
        this.POSTCODE = POSTCODE;
        this.COMMENT = COMMENT;
        this.STARIMG = STARIMG;
        this.WDATE = WDATE;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getIMG() {
        return IMG;
    }

    public void setIMG(String IMG) {
        this.IMG = IMG;
    }

    public String getPOSTCODE() {
        return POSTCODE;
    }

    public void setPOSTCODE(String POSTCODE) {
        this.POSTCODE = POSTCODE;
    }

    public String getCOMMENT() {
        return COMMENT;
    }

    public void setCOMMENT(String COMMENT) {
        this.COMMENT = COMMENT;
    }

    public String getSTARIMG() {
        return STARIMG;
    }

    public void setSTARIMG(String STARIMG) {
        this.STARIMG = STARIMG;
    }

    public String getWDATE() {
        return WDATE;
    }

    public void setWDATE(String WDATE) {
        this.WDATE = WDATE;
    }
    //{"MOVIE", "RDATE", "IMG", "WDATE", "POSTCODE", "COMMENT", "STARIMG",};


}
