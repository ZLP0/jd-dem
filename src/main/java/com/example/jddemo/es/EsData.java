package com.example.jddemo.es;

import com.example.jddemo.es.annotation.ESQuery;
import com.example.jddemo.es.annotation.ESTable;
import com.example.jddemo.es.param.Location;

/**
 * 程序员  by dell
 * time  2021-03-14
 **/
@ESTable(INDEX_NAME = "es_test2")
public class EsData {

    @ESQuery(columnName = "user", queryType = ESQuery.ConstantQueryType.TERM)
    private String user;

    private String postDate;
    @ESQuery(columnName = "message", queryType = ESQuery.ConstantQueryType.MATCH_QUERY)
    private String message;

    //@ESQuery(ColumnName = "food",QueryType = ESQuery.ConstantQueryType.TERMS)
    private String[] food;

    //@ESQuery(ColumnName = "location",QueryType = ESQuery.ConstantQueryType.GEO_DISTANCE)
    private Location location;

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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
