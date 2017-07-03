package com.dotnextstudio.com.bnk48fanapp;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Hotsnews {
    @PropertyName("title")
    public String title;

    @PropertyName("image")
    public String image;

    @PropertyName("link")
    public String link;



    public Hotsnews() {
    }

    public Hotsnews( String title,String image,String link) {
        this.title = title;
        this.image = image;
        this.link = link;
    }





    public String getTitle() {
        return title;
    }

    public void setId(String ids) {
        title = ids;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String full_pictures) {
        image = full_pictures;
    }


    public String getLink() {
        return link;
    }

    public void setLink(String links) {
        link = links;
    }









}