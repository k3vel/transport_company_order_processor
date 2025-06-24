import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


class CompanyOrderProcessor {
    private final ArrayList<TransportUnit> transportUnits;
    private final Map<String, ArrayList<RouteSegment>> routes;

    public CompanyOrderProcessor(ArrayList<TransportUnit> transportUnits, Map<String, ArrayList<RouteSegment>> routes) {
        this.transportUnits = transportUnits;
        this.transportUnits.sort(new Vehicle_Comparator());
        this.routes = routes;
    }

    public ArrayList<TransportUnit> getTransportUnits() {return transportUnits;}
    public Map<String, ArrayList<RouteSegment>> getRoutes() {return routes;}

    private Time_and_Price Transportation_Details(TransportUnit transportUnit, String destination) {
        HashMap<String,Time_and_Price> prices = new HashMap<>(routes.size());
        HashMap<String,Boolean> is_final = new HashMap<>(routes.size());
        final double INF = Double.MAX_VALUE;
        for (String station : routes.keySet()) {
            prices.put(station, new Time_and_Price(0.0,INF));
            is_final.put(station, false);
        }
        prices.put(transportUnit.getLocation(), new Time_and_Price(0.0,0.0));
        for (int i=0; i < routes.size(); i++) {
            String current = nearest1(prices,is_final);
            if (current==null)
                return new Time_and_Price(-1.0,-1.0);
            if (current.equals(destination))
                return prices.get(destination);
            is_final.put(current, true);
            for (RouteSegment dest : routes.get(current)) {
                Cargo empty_cargo = new Cargo("none",0.0,false,false);
                if (!transportUnit.Can_Deliver(empty_cargo,dest))
                    continue;
                if (!is_final.get(dest.getDestination()) && prices.get(current).getPrice() +
                transportUnit.Delivery_Price(empty_cargo,dest) < prices.get(dest.getDestination()).getPrice()) {
                    prices.get(dest.getDestination()).setPrice(prices.get(current).getPrice() +
                            transportUnit.Delivery_Price(empty_cargo,dest));
                    prices.get(dest.getDestination()).setTime(prices.get(current).getTime() +
                            transportUnit.Delivery_Time(empty_cargo,dest));
                }
            }
        }
        Time_and_Price res = prices.get(destination);
        if (res.getPrice() == INF)
            return new Time_and_Price(-1.0,-1.0);
        return res;
    }
    private String nearest1(HashMap<String, Time_and_Price> prices, HashMap<String, Boolean> is_final) {
        double min_price = Double.MAX_VALUE;
        String nearest = null;
        for (String station : routes.keySet()) {
            if (prices.get(station).getPrice() < min_price && !is_final.get(station)) {
                min_price = prices.get(station).getPrice();
                nearest = station;
            }
        }
        return nearest;
    }
    private String nearest2(HashMap<String, State> states, HashMap<String, Boolean> is_final) {
        double min_price = Double.MAX_VALUE;
        String nearest = null;
        for (String station : routes.keySet()) {
            if (states.get(station).getTp().getPrice() < min_price && !is_final.get(station)) {
                min_price = states.get(station).getTp().getPrice();
                nearest = station;
            }
        }
        return nearest;
    }

    public Response Process(Order order){
        if (!routes.containsKey(order.getStart()) || !routes.containsKey(order.getDestination()))
            return new Response(1);
        HashMap<String,State> states = new HashMap<>(routes.size());
        HashMap<String,Boolean> is_final = new HashMap<>(routes.size());
        final double INF = Double.MAX_VALUE;
        Cargo cargo = order.getCargo();

        for (String station : routes.keySet()) {
            states.put(station,new State(new Time_and_Price(0.0,INF),null,null));
            is_final.put(station, false);
        }
        states.put(order.getStart(), new State(new Time_and_Price(0.0,0.0),null,null));

        for (int i=0; i < routes.size(); i++) {
            String current = nearest2(states,is_final);
            if (current==null)
                return new Response(1);
            if (current.equals(order.getDestination()))
                break;
            is_final.put(current, true);
            for (RouteSegment seg : routes.get(current)) {
                String dest = seg.getDestination();
                if (!is_final.get(dest)) {
                    Time_and_Price min;
                    TransportUnit best_veh = null;
                    if (states.get(current).getVehicle() != null &&
                            states.get(current).getVehicle().Can_Deliver(cargo, seg)) {
                        best_veh = states.get(current).getVehicle();
                        min = new Time_and_Price(best_veh.Delivery_Time(cargo, seg),
                                best_veh.Delivery_Price(cargo, seg));
                    } else
                        min = new Time_and_Price(0, INF);
                    for (TransportUnit vehicle : transportUnits) {
                        if (vehicle.is_free() && vehicle.Can_Deliver(cargo, seg)) {
                            Time_and_Price tpr = Transportation_Details(vehicle, current);
                            tpr.addPrice(vehicle.Delivery_Price(cargo, seg));
                            tpr.addTime(vehicle.Delivery_Time(cargo, seg));
                            if (tpr.getPrice() < min.getPrice()) {
                                min = tpr;
                                best_veh = vehicle;
                            }
                        }
                    }
                    double new_price = min.getPrice() + states.get(current).getTp().getPrice(),
                            old_price = states.get(dest).getTp().getPrice();
                    if (new_price < old_price) {
                        states.get(dest).getTp().setPrice(new_price);
                        states.get(dest).getTp().setTime(min.getTime() +
                                states.get(current).getTp().getTime());
                        states.get(dest).setVehicle(best_veh);
                        states.get(dest).setPredecessor(seg);
                    }
                }
            }
        }
        if (states.get(order.getDestination()).getTp().getPrice() == INF)
            return new Response(2);
        ArrayList<Assignment> delivery_plan=new ArrayList<>();
        String current = order.getDestination();
        while (states.get(current).getPredecessor() != null) {
            RouteSegment seg = states.get(current).getPredecessor();
            Assignment a = new Assignment(seg, states.get(current).getVehicle());
            delivery_plan.add(a);
            current = seg.getStart();
        }
        Collections.reverse(delivery_plan);
        double time = states.get(order.getDestination()).getTp().getTime(),
                price = states.get(order.getDestination()).getTp().getPrice();
        return new Response(0,delivery_plan,price,time);
    }
}
/*
Response status:
0 - ok
1 - wrong route
2 - no transport can deliver this
 */


class Time_and_Price{
    private double time;
    private double price;

    public Time_and_Price(double time, double price) {this.time=time;this.price=price;}
    public double getTime() {return time;}
    public double getPrice() {return price;}
    public void addPrice(double price) {this.price += price;}
    public void addTime(double time) {this.time += time;}
    public void setPrice(double price) {this.price = price;}
    public void setTime(double time) {this.time = time;}
}


class State{
    private Time_and_Price tp;
    private TransportUnit vehicle;
    private RouteSegment predecessor;

    public State(Time_and_Price tp, TransportUnit vehicle, RouteSegment predecessor) {
        this.tp=tp;
        this.vehicle=vehicle;
        this.predecessor=predecessor;
    }
    public Time_and_Price getTp() {return tp;}
    public TransportUnit getVehicle() {return vehicle;}
    public RouteSegment getPredecessor() {return predecessor;}
    public void setTp(Time_and_Price tp) {this.tp = tp;}
    public void setVehicle(TransportUnit vehicle) {this.vehicle = vehicle;}
    public void setPredecessor(RouteSegment predecessor) {this.predecessor = predecessor;}
}