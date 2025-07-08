package com.order_processor.models.transport;

import com.order_processor.models.Cargo;
import com.order_processor.models.RouteSegment;

import java.io.Serializable;

public class Plane extends TransportUnit implements Serializable {
    public Plane(){}
    public Plane(int id, boolean is_free, String location, double capacity, boolean has_refregerator,
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
        if (route.getType() == RouteSegment.RouteType.GROUND || route.getType() == RouteSegment.RouteType.WATER ||
                route.getType() == RouteSegment.RouteType.GROUND_AND_WATER)
            return false;
        return true;
    }

    @Override
    public String toString(){return super.toString();}
}