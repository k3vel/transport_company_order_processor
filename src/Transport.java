import java.io.Serializable;
import java.util.Comparator;

abstract class TransportUnit implements Serializable {
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
}


class Vehicle_Comparator implements Comparator<TransportUnit> {
    @Override
    public int compare(TransportUnit o1, TransportUnit o2) {
        return Double.compare(o1.getPrice_per_km(),o2.getPrice_per_km());
    }
}


class Truck extends TransportUnit implements Serializable{
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
        if (route.getType()==2 || route.getType()==3 || route.getType()==6)
            return false;
        if (route.getDifficulty_index()>=0.7)
            return false;
        return true;
    }

    @Override
    public String toString(){return super.toString();}
}


class Ferry extends TransportUnit implements Serializable{
    public Ferry(){}
    public Ferry(int id, boolean is_free, String location, double capacity, boolean has_refregerator,
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
        if (route.getType()==1 || route.getType()==3 || route.getType()==5)
            return false;
        return true;
    }

    @Override
    public String toString(){return super.toString();}
}


class Plane extends TransportUnit implements Serializable{
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
        if (route.getType()==1 || route.getType()==2 || route.getType()==4)
            return false;
        return true;
    }

    @Override
    public String toString(){return super.toString();}
}


class Drone extends TransportUnit implements Serializable{
    private double max_distance;

    public Drone(){}
    public Drone(int id, boolean is_free, String location, double capacity, boolean has_refregerator,
                 double avg_speed, double price_per_km, double max_distance){
        super(id,is_free,location,capacity,has_refregerator,avg_speed,price_per_km);
        this.max_distance=max_distance;
    }

    @Override
    public boolean Can_Deliver(Cargo cargo, RouteSegment route) {
        if (!is_free())
            return false;
        if (getCapacity()<cargo.getWeight())
            return false;
        if (route.getType()==1 || route.getType()==2 || route.getType()==4)
            return false;
        if (route.getDistance()>max_distance)
            return false;
        return true;
    }

    @Override
    public String toString(){return super.toString()+" Max distance: "+max_distance;}
}