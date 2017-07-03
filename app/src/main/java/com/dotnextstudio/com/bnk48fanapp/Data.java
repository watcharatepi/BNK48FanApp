package com.dotnextstudio.com.bnk48fanapp;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Data {
    @PropertyName("full_picture")
    public String full_picture;

    @PropertyName("id")
    public String id;

    @PropertyName("link")
    public String link;

    @PropertyName("member")
    public String member;

    @PropertyName("message")
    public String message;

    @PropertyName("picture")
    public String picture;

    @PropertyName("updated_time")
    public String updated_time;


    public Data() {
    }

    public Data( String full_picture,String id,String link,String member ,String message, String picture, String updated_time) {
        this.id = id;
        this.message = message;
        this.full_picture = full_picture;
        this.link = link;
        this.member = member;
        this.picture = picture;
        this.updated_time = updated_time;

    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("message", message);
        result.put("full_picture", full_picture);
        result.put("link", link);
        result.put("member", member);
        result.put("picture", picture);
        result.put("updated_time", updated_time);

        return result;
    }




    public String getId() {
        return id;
    }

    public void setId(String ids) {
        id = ids;
    }


    public String getFull_picture() {
        return full_picture;
    }

    public void setFull_picture(String full_pictures) {
        full_picture = full_pictures;
    }




    public String getLink() {
        return link;
    }

    public void setLink(String links) {
        link = links;
    }


    public String getMember() {
        return member;
    }

    public void setMember(String members) {
        member = members;
    }


    public String getMessage() {
        return message;
    }


    public void setMessage(String messages) {
        message = messages;
    }


    public String getPicture() {
        return picture;
    }

    public void setPicture(String pictures) {
        picture = pictures;
    }

    public String getUpdated_time() {
        return updated_time;
    }

    public void setUpdated_time(String updated_times) {
        updated_time = updated_times;
    }








}