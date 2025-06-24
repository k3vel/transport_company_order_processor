import java.io.Serializable;

class Order implements Serializable {
    private int orderId;
    private String customer_name;
    private String customer_contacts;
    private Cargo cargo;
    private String start;
    private String destination;
    //no flags in this version

    public Order() {}
    public Order(int orderId, String customer_name, String customer_contacts, Cargo cargo, String start, String destination) {
        this.orderId = orderId;
        this.customer_name = customer_name;
        this.customer_contacts = customer_contacts;
        this.cargo = cargo;
        this.start = start;
        this.destination = destination;
    }

    public int getOrderId() {return orderId;}
    public String getCustomer_name() {return customer_name;}
    public String getCustomer_contacts() {return customer_contacts;}
    public Cargo getCargo() {return cargo;}
    public String getStart() {return start;}
    public String getDestination() {return destination;}

    @Override
    public String toString() {
        return "Order №" + orderId + " " + customer_name + ". Contacts: " + customer_contacts + "\n   " + cargo +
                "\n   " + start + "->" + destination + "\n";
    }
}