package com.example.jddemo.es.param;

/**
 * 程序员  by dell
 * time  2021-03-20
 **/

public class Location {

    /**
     * 纬度
     */
    private double lat;

    /**
     * 经度
     */
    private double lon;

    /**
     * 距离
     */
    private String distance;

    /**
     * 距离单位 m,km
     */
    private String distanceUnit;


    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDistanceUnit() {
        return distanceUnit;
    }

    public void setDistanceUnit(String distanceUnit) {
        this.distanceUnit = distanceUnit;
    }
}
