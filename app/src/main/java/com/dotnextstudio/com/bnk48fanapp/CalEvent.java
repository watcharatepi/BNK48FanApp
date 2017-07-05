package com.dotnextstudio.com.bnk48fanapp;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class CalEvent {
    @PropertyName("title")
    public String title;

    @PropertyName("date")
    public String date;

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


    public CalEvent() {
    }

    public CalEvent( String title,String date,String link,String member ,String message, String picture, String updated_time) {
        this.date = date;
        this.message = message;
        this.title = title;
        this.link = link;
        this.member = member;
        this.picture = picture;
        this.updated_time = updated_time;

    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("date", date);
        result.put("message", message);
        result.put("title", title);
        result.put("link", link);
        result.put("member", member);
        result.put("picture", picture);
        result.put("updated_time", updated_time);

        return result;
    }




    public String getDate() {
        return date;
    }

    public void setDate(String ids) {
        date = ids;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String titles) {
        title = titles;
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