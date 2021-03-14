package com.example.jddemo.es;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 程序员  by dell
 * time  2021-03-14
 **/
public class EsData {

    private String user;

    private String  postDate;

    private String message;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
