package com.order_processor.models.transport;

import com.order_processor.models.Cargo;
import com.order_processor.models.RouteSegment;

import java.io.Serializable;

public class Truck extends TransportUnit implements Serializable {
    public Truck(){}
    public Truck(int id, boolean is_free, String location, double capacity, boolean has_refregerator,
                 double avg_speed, double price_per_km){
        super(id,is_free,location,capacity,has_refregerator,avg_speed,price_per_km);
    }

    @Override
    public boolean Can_Deliver(Cargo cargo, RouteSegment route) {
        if (!is_free())
            return false;
        if (getCapacity()<cargo.getWeight())
            return false;
        if (cargo.is_perishable() && !has_refregerator())
            return false;
        if (route.getType() == RouteSegment.RouteType.WATER || route.getType() == RouteSegment.RouteType.AIR ||
                route.getType() == RouteSegment.RouteType.WATER_AND_AIR)
            return false;
        if (route.getDifficulty_index()>=0.7)
            return false;
        return true;
    }

    @Override
    public String toString(){return super.toString();}
}