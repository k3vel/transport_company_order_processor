package com.order_processor.models.transport;

import com.order_processor.models.Cargo;
import com.order_processor.models.RouteSegment;

import java.io.Serializable;

public abstract class TransportUnit implements Serializable {
    private int id;
    private boolean is_free;
    private String location;
    private double capacity;
    private boolean has_refregerator;
    private double avg_speed;
    private double price_per_km;

    public TransportUnit(int id, boolean is_free, String location, double capacity, boolean has_refregerator,
                         double avg_speed, double price_per_km) {
        this.id = id;
        this.is_free = is_free;
        this.location = location;
        this.capacity=capacity;
        this.has_refregerator=has_refregerator;
        this.avg_speed = avg_speed;
        this.price_per_km = price_per_km;
    }

    public TransportUnit(){}
    public int getId() {return id;}
    public boolean is_free() {return is_free;}
    public String getLocation() {return location;}
    public double getCapacity() {return capacity;}
    public boolean has_refregerator() {return has_refregerator;}
    public double getAvg_speed() {return avg_speed;}
    public double getPrice_per_km() {return price_per_km;}

    public abstract boolean Can_Deliver(Cargo cargo, RouteSegment route);
    public double Delivery_Price(Cargo cargo, RouteSegment route){
        if (!Can_Deliver(cargo,route))
            throw new RuntimeException("Can't deliver cargo");
        double price=route.getDistance()*(price_per_km+10*route.getDifficulty_index());
        price+=cargo.getWeight()*0.5;
        if (cargo.is_fragile())
            price*=1.2;
        return price;
    }
    public double Delivery_Time(Cargo cargo, RouteSegment route){
        if (Can_Deliver(cargo, route))
            return route.getDistance()/avg_speed*(1+route.getDifficulty_index());
        throw new RuntimeException("Can't deliver cargo");
    }

    @Override
    public String toString() {
        String isfr,href;
        if (is_free)
            isfr="Free";
        else isfr="Busy";
        if(has_refregerator)
            href="Refregerated";
        else href="Not Refregerated";

        return "Id: "+id+" "+isfr+" Capacity: "+capacity+" "+href+" "+avg_speed+" km/h "+price_per_km+"₴";
    }
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TransportUnit){
            TransportUnit other = (TransportUnit) obj;
            return this.id == other.id && this.is_free == other.is_free && (this.location).equals(other.location) &&
                    this.capacity == other.capacity && this.has_refregerator == other.has_refregerator &&
                    this.avg_speed == other.avg_speed && this.price_per_km == other.price_per_km;
        }
        return false;
    }
}