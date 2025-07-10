package com.order_processor.models.transport;

import com.order_processor.models.Cargo;
import com.order_processor.models.RouteSegment;

import java.io.Serializable;

public class Drone extends TransportUnit implements Serializable {
    private double max_distance;

    public Drone(){}
    public Drone(int id, boolean is_free, String location, double capacity, double avg_speed, double price_per_km,
                 double max_distance){
        super(id,is_free,location,capacity,false,avg_speed,price_per_km);
        this.max_distance=max_distance;
    }

    @Override
    public boolean Can_Deliver(Cargo cargo, RouteSegment route) {
        if (!is_free())
            return false;
        if (getCapacity()<cargo.getWeight())
            return false;
        if (cargo.is_perishable())
            return false;
        if (route.getType() == RouteSegment.RouteType.GROUND || route.getType() == RouteSegment.RouteType.WATER ||
                route.getType() == RouteSegment.RouteType.GROUND_AND_WATER)
            return false;
        if (route.getDistance()>max_distance)
            return false;
        return true;
    }

    @Override
    public String toString(){return super.toString()+" Max distance: "+max_distance;}
}