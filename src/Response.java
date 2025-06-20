import java.io.Serializable;
import java.util.ArrayList;

class Response implements Serializable {
    private final int status; //0 or 1
    private final ArrayList<Assignment> delivery_plan;
    private final double price;
    private final double time;

    public Response(int status, ArrayList<Assignment> delivery_plan, double price, double time) {
        if (status!=0 && status!=1)
            throw new IllegalArgumentException("Invalid status");
        this.delivery_plan = delivery_plan;
        this.status = status;
        this.price = price;
        this.time = time;
    }
    public Response(int status){
        this(status,null,0,0);
    }

    public int getStatus() {return status;}
    public ArrayList<Assignment> getDelivery_plan() {return delivery_plan;}
    public double getPrice() {return price;}
    public double getTime() {return time;}

    @Override
    public String toString() {
        if (status==1)
            return "Sorry but we do not serve this route.";
        if (status==2)
            return "Sorry but all transport units are currently busy.";
        String message = "The order will cost "+price+"₴ and take approximately "+time+" hours to complete.\n\n";
        for (Assignment as : delivery_plan){
            message += as.getSegment().getStart() + " -> " + as.getSegment().getDestination() +
                    " : vehicle №" + as.getVehicle().getId() + "\n";
        }
        return message;
    }
}

class Assignment implements Serializable {
    private RouteSegment segment;
    private TransportUnit vehicle;

    public Assignment(RouteSegment segment, TransportUnit vehicle) {this.segment = segment;this.vehicle = vehicle;}
    public RouteSegment getSegment() {return segment;}
    public TransportUnit getVehicle() {return vehicle;}
    public void setVehicle(TransportUnit vehicle) {this.vehicle = vehicle;}
    public void setSegment(RouteSegment segment) {this.segment = segment;}
}