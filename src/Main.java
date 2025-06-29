/*Develop a module responsible for accepting customer orders in a transport company.
A customer order is submitted at the input with the following items: cargo to be transported, starting point,
destination point, wishes (execute as quickly as possible/execute as cheaply as possible). The output should include
the expected price and execution time, if the order can be processed, otherwise - a corresponding message
for the client. A ready-made database of existing routes and available transport is provided for work.*/

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
/*
        DemoDataCreator d = new DemoDataCreator();
        d.writeDemoRoutes();
        d.writeDemoTransport();
        d.writeDemoOrder(12087,"Bullets",10000,false,true,
                "Joe Biden","228337","Dnipro","Istanbul");
*/
        ObjectInputStream ois1=new ObjectInputStream(new FileInputStream("Company Data/Routes.dat"));
        ObjectInputStream ois2=new ObjectInputStream(new FileInputStream("Company Data/Transport.dat"));
        ObjectInputStream ois3=new ObjectInputStream(new FileInputStream("Order.dat"));
        Map<String, ArrayList<RouteSegment>> routes=(Map<String, ArrayList<RouteSegment>>) ois1.readObject();
        ArrayList<TransportUnit> transports=(ArrayList<TransportUnit>)ois2.readObject();
        CompanyOrderProcessor order_processor = new CompanyOrderProcessor(transports,routes);
        Order order=(Order)ois3.readObject();
        ois1.close();
        ois2.close();
        ois3.close();

        Response response=order_processor.Process(order);
        ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream("Response/Response.dat"));
        oos.writeObject(response);
        PrintWriter fw=new PrintWriter("Response/Response.txt");
        System.out.println(response);
        fw.println(response);
        oos.close();
        fw.close();

    }
}