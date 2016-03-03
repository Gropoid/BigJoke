package com.gropoid.bigjoke.backend;

/** The object model for the data we are sending through endpoints */
public class JokeBean {

    private String mJoke;

    public String getJoke() {
        return mJoke;
    }

    public void setJoke(String data) {
        mJoke = data;
    }
}