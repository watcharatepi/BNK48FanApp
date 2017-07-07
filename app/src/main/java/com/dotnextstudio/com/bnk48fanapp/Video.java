package com.dotnextstudio.com.bnk48fanapp;

import com.google.firebase.database.PropertyName;

/**
 * Created by watcharatepinkong on 7/6/17.
 */

public class Video {

    @PropertyName("video")
    public String video;

    @PropertyName("title")
    public String title;

    @PropertyName("url")
    public String url;

    @PropertyName("member")
    public String member;

    public Video() {
    }


    public Video( String video,String title,String url,String member ) {
        this.video = video;
        this.title = title;
        this.url = url;
        this.member = member;

    }



    public String getVideo() {
        return video;
    }

    public void setVideo(String videos) {
        video = videos;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String titles) {
        title = titles;
    }



    public String getUrl() {
        return url;
    }

    public void setUrl(String urls) {
        url = urls;
    }



    public String getMember() {
        return member;
    }

    public void setMember(String members) {
        member = members;
    }


}
