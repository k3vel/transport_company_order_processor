import java.io.Serializable;

class Cargo implements Serializable {
    private String type;//enum
    private double weight;
    private boolean is_perishable;
    private boolean is_fragile;

    public Cargo(String type, double weight, boolean is_perishable, boolean is_fragile) {
        this.type = type;
        this.weight = weight;
        this.is_perishable = is_perishable;
        this.is_fragile = is_fragile;
    }
    public Cargo(){}
    public Cargo(Cargo c){
        this.type = c.getType();
        this.weight = c.getWeight();
        this.is_perishable = c.is_perishable();
        this.is_fragile = c.is_fragile();
    }

    public String getType() {return type;}
    public double getWeight() {return weight;}
    public boolean is_perishable() {return is_perishable;}
    public boolean is_fragile() {return is_fragile;}

    public void setType(String type) {this.type = type;}
    public void setWeight(double weight) {this.weight = weight;}
    public void setIs_perishable(boolean is_perishable) {this.is_perishable = is_perishable;}
    public void setIs_fragile(boolean is_fragile) {this.is_fragile = is_fragile;}

    @Override
    public String toString() {
        String isper,isfrag;
        if(is_perishable)
            isper = "perishable";
        else isper = "not perishable";
        if (is_fragile)
            isfrag = "fragile";
        else isfrag = "not fragile";

        return type+" "+weight+" kg "+isper+" "+isfrag;
    }
}