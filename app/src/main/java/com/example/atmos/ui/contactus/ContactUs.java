package com.example.atmos.ui.contactus;

public class ContactUs {

    private String name, post, number;
    private int publicityType;

    public static final int PUBLICITY_TYPE_ORGANIZING_BODY = 1;
    public static final int PUBLICITY_TYPE_TECH_SENATE = 2;

    public ContactUs(String name, String post, String number, int publicityType) {
        this.name = name;
        this.post = post;
        this.number = number;
        this.publicityType = publicityType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getPublicityType() {
        return publicityType;
    }
}
