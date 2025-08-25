package com.order_processor;

import com.order_processor.models.RouteSegment;
import com.order_processor.models.transport.TransportUnit;
import com.order_processor.processing.CompanyOrderProcessor;
import com.order_processor.processing.Order;
import com.order_processor.processing.Response;
import com.order_processor.DemoDataCreator;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;

public class Launcher {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
/*
        DemoDataCreator d = new com.order_processor.DemoDataCreator();
        d.writeDemoRoutes();
        d.writeDemoTransport();
        d.writeDemoOrder(12087,"Bullets",1000,false,true,
                "Joe Biden","228337","Kharkiv","Poltava");
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