package com.lambdaschool.dogsinitial;

import java.io.Serializable;

public class MessageDetail implements Serializable{
    private String text;
    private String date;
    private String param;

    public MessageDetail(){

    }

    public MessageDetail(String text, String date, String param) {
        this.text = text;
        this.date = date;
        this.param = param;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    @Override
    public String toString(){
        return "MessageDetail{"+
                "text='" + text + '\'' +
                "date='" + date + '\'' +
                "param='" + param + '\'' +
                '}';
    }
}