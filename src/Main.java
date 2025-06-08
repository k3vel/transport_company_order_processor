/*Develop a module responsible for accepting customer orders in a transport company.
A customer order is submitted at the input with the following items: cargo to be transported, starting point,
destination point, wishes (execute as quickly as possible/execute as cheaply as possible). The output should include
the expected price and execution time, if the order can be processed, otherwise - a corresponding message
for the client. A ready-made database of existing routes and available transport is provided for work.*/

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner; ///////////ADD GRAPH

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
/*
        ArrayList<TransportUnit> tr= new ArrayList<TransportUnit>();
        ArrayList<Route> rt=new ArrayList<Route>();
        rt.add(new Route("Zaporizhzhia","Dnipro",70,4,0.05));
        rt.add(new Route("Dnipro","Zaporizhzhia",70,4,0.05));
        rt.add(new Route("Dnipro","Kharkiv",220,5,0.1));
        rt.add(new Route("Kharkiv","Dnipro",220,5,0.1));
        rt.add(new Route("Dnipro","Odesa",450,1,0.2));
        rt.add(new Route("Odesa","Dnipro",450,1,0.2));
        rt.add(new Route("Odesa","Istanbul",600,6,0.1));
        rt.add(new Route("Istanbul","Odesa",600,6,0.1));
        rt.add(new Route("Zaporizhzhia","Istanbul",900,3,0.3));
        rt.add(new Route("Istanbul","Zaporizhzhia",900,3,0.3));
        rt.add(new Route("Dnipro","Kyiv",400,7,0.2));
        rt.add(new Route("Kyiv","Dnipro",400,7,0.2));
        rt.add(new Route("Kyiv","Odesa",500,5,0.1));
        rt.add(new Route("Odesa","Kyiv",500,5,0.1));

        tr.add(new Truck(123,true,20000,false,70,50));
        tr.add(new Truck(125,true,16000,true,90,70));
        tr.add(new Ferry(201,true,1000000,true,50,1000));
        tr.add(new Plane(330,true,50000,true,400,1100));
        tr.add(new Plane(344,true,60000,false,370,1100));
        tr.add(new Drone(612,true,20,false,100,10,40));

        //CompanyOrderProcessor oder_processor = new CompanyOrderProcessor(tr,rt);
        ObjectOutputStream oos1=new ObjectOutputStream(new FileOutputStream("Company Data/Routes.dat"));
        ObjectOutputStream oos2=new ObjectOutputStream(new FileOutputStream("Company Data/Transport.dat"));
        oos1.writeObject(rt);
        oos2.writeObject(tr);
        oos1.close();
        oos2.close();
        Cargo cargo = new Cargo("Bullets",10000,false,true);
        Order order = new Order(12087,"Joe Biden","228337",cargo,"Istanbul","Odesa",1);
        ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream("Order.dat"));
        oos.writeObject(order);
        oos.close();
*/

        ObjectInputStream ois1=new ObjectInputStream(new FileInputStream("Company Data/Routes.dat"));
        ObjectInputStream ois2=new ObjectInputStream(new FileInputStream("Company Data/Transport.dat"));
        ObjectInputStream ois3=new ObjectInputStream(new FileInputStream("Order.dat"));
        ArrayList<Route> routes=(ArrayList<Route>)ois1.readObject();
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
        fw.println(response);
        oos.close();
        fw.close();
    }
}