package com.example.jddemo.es;

import com.example.jddemo.es.annotation.ESQuery;
import com.example.jddemo.es.annotation.ESTable;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 程序员  by dell
 * time  2021-03-14
 **/
@ESTable(INDEX_NAME = "es_test")
public class EsData {

    @ESQuery(ColumnName = "user",QueryType = ESQuery.ConstantQueryType.TERM)
    private String user;

    private String  postDate;

    private String message;

    @ESQuery(ColumnName = "food",QueryType = ESQuery.ConstantQueryType.TERMS)
    private String[] food;

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

    public String[] getFood() {
        return food;
    }

    public void setFood(String[] food) {
        this.food = food;
    }
}
