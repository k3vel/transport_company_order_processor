import java.io.Serializable;

class Cargo implements Serializable {
    private final String type;//enum
    private final double weight;
    private final boolean is_perishable;
    private final boolean is_fragile;

    public Cargo(String type, double weight, boolean is_perishable, boolean is_fragile) {
        this.type = type;
        this.weight = weight;
        this.is_perishable = is_perishable;
        this.is_fragile = is_fragile;
    }
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