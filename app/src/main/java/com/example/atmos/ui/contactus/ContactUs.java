package com.example.atmos.ui.contactus;

public class ContactUs {

    private String name, post, number;

    public ContactUs(String name, String post, String number) {
        this.name = name;
        this.post = post;
        this.number = number;
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
}
