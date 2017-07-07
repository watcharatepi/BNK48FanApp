package com.dotnextstudio.com.bnk48fanapp;

/**
 * Created by watcharatepinkong on 7/6/17.
 */

public class DataEvent {
    public String title;
    public long date;
    public String time;
    public String detail;
    public String member;


    public DataEvent( String title,long date,String time,String detail ,String member) {
        this.title = title;
        this.date = date;
        this.time = time;
        this.detail = detail;
        this.member = member;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String titles) {
        title = titles;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long datas) {
        date = datas;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String times) {
        times = time;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String details) {
        detail = detail;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String members) {
        member = members;
    }


}
