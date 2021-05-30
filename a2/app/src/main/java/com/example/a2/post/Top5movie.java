package com.example.a2.post;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Top5movie {

    @SerializedName("MovieName")
    @Expose
    private String movieName;
    @SerializedName("ReleaseYear")
    @Expose
    private String releaseYear;
    @SerializedName("RatingScore")
    @Expose
    private String ratingScore;
    private String snippet;

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public Top5movie withMovieName(String movieName) {
        this.movieName = movieName;
        return this;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Top5movie withReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
        return this;
    }

    public String getRatingScore() {
        return ratingScore;
    }

    public void setRatingScore(String ratingScore) {
        this.ratingScore = ratingScore;
    }

    public Top5movie withRatingScore(String ratingScore) {
        this.ratingScore = ratingScore;
        return this;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }
}
