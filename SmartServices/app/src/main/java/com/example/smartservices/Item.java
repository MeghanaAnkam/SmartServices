package com.example.smartservices;

public class Item {

    private String imageResourse;
    private String title;
    private String desc;
    private boolean isShrink = true;

    public Item() {
    }

    public Item(String imageResourse, String title, String desc) {
        this.imageResourse = imageResourse;
        this.title = title;
        this.desc = desc;
    }

    public String getImageResourse() {
        return imageResourse;
    }

    public void setImageResourse(String imageResourse) {
        this.imageResourse = imageResourse;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isShrink() {
        return isShrink;
    }

    public void setShrink(boolean shrink) {
        isShrink = shrink;
    }
}
