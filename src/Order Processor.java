import java.io.Serializable;
import java.util.ArrayList;

class Order implements Serializable {
    private int orderId;
    private String customer_name;
    private String customer_contacts;
    private Cargo cargo;
    private String start;
    private String destination;
    private int flag; //1 - fastest option, 2 - cheapest option

    public Order() {}
    public Order(int orderId, String customer_name, String customer_contacts, Cargo cargo, String start, String destination, int flag) {
        if (flag!=1 && flag!=2)
            throw new IllegalArgumentException("Invalid flag");
        this.orderId = orderId;
        this.customer_name = customer_name;
        this.customer_contacts = customer_contacts;
        this.cargo = cargo;
        this.start = start;
        this.destination = destination;
        this.flag = flag;
    }

    public int getOrderId() {return orderId;}
    public String getCustomer_name() {return customer_name;}
    public String getCustomer_contacts() {return customer_contacts;}
    public Cargo getCargo() {return cargo;}
    public String getStart() {return start;}
    public String getDestination() {return destination;}
    public int getFlag() {return flag;}

    public void setOrderId(int orderId) {this.orderId = orderId;}
    public void setCustomer_name(String customer_name) {this.customer_name = customer_name;}
    public void setCustomer_contacts(String customer_contacts) {this.customer_contacts = customer_contacts;}
    public void setCargo(Cargo cargo) {this.cargo = cargo;}
    public void setStart(String start) {this.start = start;}
    public void setDestination(String destination) {this.destination = destination;}
    public void setFlag(int flag) {this.flag = flag;}

    @Override
    public String toString() {
        String option="";
        if (flag==1)
            option="Fastest";
        else option="Cheapest";
        return orderId+"\nOrder: "+customer_name+" "+customer_contacts+"\n"+cargo+"\n"+start+"->"+destination+"\nOption: "+option;
    }
}
class Response implements Serializable {
    private final int status; //0 or 1
    private final double price;
    private final double time;

    public Response(int status, double price, double time) {
        if (status!=0 && status!=1)
            throw new IllegalArgumentException("Invalid status");
        this.status = status;
        this.price = price;
        this.time = time;
    }
    public Response(int status){
        this(status,0,0);
    }

    public int getStatus() {return status;}
    public double getPrice() {return price;}
    public double getTime() {return time;}

    @Override
    public String toString() {
        if (status==1)
            return "Sorry but we do not serve this route.";
        if (status==2)
            return "Sorry but all transport units are currently busy.";
        return "The order will cost "+price+" and take approximately "+time+" hours to complete.";
    }
}

class CompanyOrderProcessor implements Serializable {
    private ArrayList<TransportUnit> transportUnits;
    private ArrayList<Route> routes;

    public CompanyOrderProcessor(ArrayList<TransportUnit> transportUnits, ArrayList<Route> routes) {
        this.transportUnits = transportUnits;
        this.routes = routes;
    }

    public ArrayList<TransportUnit> getTransportUnits() {return transportUnits;}
    public ArrayList<Route> getRoutes() {return routes;}

    public void addTransportUnit(TransportUnit transportUnit) {transportUnits.add(transportUnit);}
    public void addRoute(Route route) {routes.add(route);}

    public Response Process(Order order){
        ArrayList<TransportUnit> buffer=new ArrayList<>();
        TransportUnit optimal=null;
        Response response;
        double min;
        Route route=null;
        int check=0;
        for (Route rt : routes) {
            if (rt.getStart().equals(order.getStart()) && rt.getDestination().equals(order.getDestination())) {
                route=new Route(rt);
                check=1;
                break;
            }
        }
        if (check==0)
            return new Response(1);
        if (order.getFlag()==1){
            for (TransportUnit unit: transportUnits)
                if (unit.Can_Deliver(order.getCargo(),route))
                    buffer.add(unit);
            min=buffer.getFirst().Delivery_Time(order.getCargo(),route);
            optimal=buffer.getFirst();
            for (TransportUnit unit: buffer){
                if (unit.Delivery_Time(order.getCargo(),route)<min){
                    optimal=unit;
                    min=unit.Delivery_Time(order.getCargo(),route);
                }
            }
        }
        else{
            for (TransportUnit unit: transportUnits)
                if (unit.Can_Deliver(order.getCargo(),route))
                    buffer.add(unit);
            min=buffer.getFirst().Delivery_Price(order.getCargo(),route);
            optimal=buffer.getFirst();
            for (TransportUnit unit: buffer){
                if (unit.Delivery_Price(order.getCargo(),route)<min){
                    optimal=unit;
                    min=unit.Delivery_Price(order.getCargo(),route);
                }
            }
        }

        if (optimal==null)
            response=new Response(2);
        response=new Response(0,optimal.Delivery_Price(order.getCargo(),route),optimal.Delivery_Time(order.getCargo(),route));
        return response;
    }
}
/*
Response status:
0 - ok
1 - wrong route
2 - no transport can deliver this
 */