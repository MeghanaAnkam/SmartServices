package com.example.smartservices;
public class CleanAdminGetter {
    private String dustplace;
    private String rollno;
    private String url;
    private String email;

    public String getEmail() {
        return email;
    }




    public String getDustplace() {
        return dustplace;
    }

    public void setDustplace(String dustplace) {
        this.dustplace = dustplace;
    }

    public String getRollno() {
        return rollno;
    }

    public void setRollno(String rollno) {
        this.rollno = rollno;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}