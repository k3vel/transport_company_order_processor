package com.order_processor.models;

import java.io.Serializable;

public class RouteSegment implements Serializable {
    private final String start;
    private final String destination;
    private final double distance;
    private final RouteType type;
    private final double difficulty_index;

    public enum RouteType{
        GROUND, WATER, AIR, GROUND_AND_WATER, AIR_AND_GROUND, WATER_AND_AIR, GROUND_AND_WATER_AND_AIR
    }

    public RouteSegment(String start, String destination, double distance, RouteType type, double difficulty_index) {
        this.start = start;
        this.destination = destination;
        this.distance = distance;
        this.type = type;
        this.difficulty_index = difficulty_index;
    }

    public String getStart() {return start;}
    public String getDestination() {return destination;}
    public double getDistance() {return distance;}
    public RouteType getType() {return type;}
    public double getDifficulty_index() {return difficulty_index;}

    @Override
    public String toString() {
        return start+" -> "+destination+" "+distance+" km "+type.name()+" index: "+difficulty_index;
    }
}