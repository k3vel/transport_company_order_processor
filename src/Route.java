import java.io.Serializable;

class Route implements Serializable {
    private String start;
    private String destination;
    private double distance;
    private int type;
    private double difficulty_index;

    public Route(String start, String destination, double distance, int type, double difficulty_index) {
        if (type<1 && type>7)
            throw new IllegalArgumentException("Type must be between 1 to 7");
        this.start = start;
        this.destination = destination;
        this.distance = distance;
        this.type=type;
        this.difficulty_index = difficulty_index;
    }
    public Route(){}
    public Route(Route route){
        this.start=route.getStart();
        this.destination=route.getDestination();
        this.distance=route.getDistance();
        this.type=route.getType();
        this.difficulty_index=route.getDifficulty_index();
    }

    public String getStart() {return start;}
    public String getDestination() {return destination;}
    public double getDistance() {return distance;}
    public int getType() {return type;}
    public double getDifficulty_index() {return difficulty_index;}

    public void setStart(String start) {this.start = start;}
    public void setDestination(String destination) {this.destination = destination;}
    public void setDistance(double distance) {this.distance = distance;}
    public void setType(int reachable_by) {this.type = reachable_by;}
    public void setDifficulty_index(double difficulty_index) {this.difficulty_index = difficulty_index;}

    @Override
    public String toString() {
        String route="";
        switch(type){
            case 1: route="GROUND"; break;
            case 2: route="WATER"; break;
            case 3: route="AIR"; break;
            case 4: route="GROUND & WATER"; break;
            case 5: route="GROUND & AIR"; break;
            case 6: route="WATER & AIR"; break;
            case 7: route="GROUND & WATER & AIR"; break;
        }
        return start+" -> "+destination+" "+distance+" km "+route+" index: "+difficulty_index;
    }
}