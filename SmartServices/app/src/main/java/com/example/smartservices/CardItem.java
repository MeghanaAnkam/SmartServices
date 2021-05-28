package com.example.smartservices;

public class CardItem {

    private String mTextResource;
    private String mTitleResource;

    public String getlink() {
        return mlink;
    }

    private String mlink;

    public CardItem(String title, String text,String link) {
        mTitleResource = title;
        mTextResource = text;
        mlink=link;
    }

    public String getText() {
        return mTextResource;
    }

    public String getTitle() {
        return mTitleResource;
    }
}