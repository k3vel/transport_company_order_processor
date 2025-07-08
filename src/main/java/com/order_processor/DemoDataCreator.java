package com.order_processor;

import com.order_processor.models.*;
import com.order_processor.models.transport.*;
import com.order_processor.processing.Order;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DemoDataCreator{
    public void writeDemoRoutes() throws IOException {
        Map<String, ArrayList<RouteSegment>> routes = new HashMap<>();

        RouteSegment r1 = new RouteSegment("Zaporizhzhia","Dnipro",70, RouteSegment.RouteType.GROUND_AND_WATER,0.05),
                r2 = new RouteSegment("Dnipro","Zaporizhzhia",70, RouteSegment.RouteType.GROUND_AND_WATER,0.05),
                r3 = new RouteSegment("Dnipro","Kharkiv",220, RouteSegment.RouteType.AIR_AND_GROUND,0.1),
                r4 = new RouteSegment("Kharkiv","Dnipro",220, RouteSegment.RouteType.AIR_AND_GROUND,0.1),
                r5 = new RouteSegment("Dnipro","Odesa",450, RouteSegment.RouteType.GROUND,0.3),
                r6 = new RouteSegment("Odesa","Dnipro",450, RouteSegment.RouteType.GROUND,0.3),
                r7 = new RouteSegment("Odesa","Istanbul",600, RouteSegment.RouteType.WATER_AND_AIR,0.1),
                r8 = new RouteSegment("Istanbul","Odesa",600, RouteSegment.RouteType.WATER_AND_AIR,0.1),
                r9 = new RouteSegment("Kharkiv","Poltava",130, RouteSegment.RouteType.GROUND,0.1),
                r10 = new RouteSegment("Poltava","Kharkiv",130, RouteSegment.RouteType.GROUND,0.1),
                r11 = new RouteSegment("Dnipro","Kyiv",400, RouteSegment.RouteType.GROUND_AND_WATER_AND_AIR,0.2),
                r12 = new RouteSegment("Kyiv","Dnipro",400, RouteSegment.RouteType.GROUND_AND_WATER_AND_AIR,0.2),
                r13 = new RouteSegment("Kyiv","Odesa",500, RouteSegment.RouteType.AIR_AND_GROUND,0.1),
                r14 = new RouteSegment("Odesa","Kyiv",500, RouteSegment.RouteType.AIR_AND_GROUND,0.1),
                r15 = new RouteSegment("Poltava","Kyiv",300, RouteSegment.RouteType.GROUND,0.1),
                r16 = new RouteSegment("Kyiv","Poltava",300, RouteSegment.RouteType.GROUND,0.1),
                r17 = new RouteSegment("Kyiv","Kharkiv",410, RouteSegment.RouteType.AIR,0.15),
                r18 = new RouteSegment("Kharkiv","Kyiv",410, RouteSegment.RouteType.AIR,0.15),
                r19 = new RouteSegment("Kyiv","Lviv",470, RouteSegment.RouteType.AIR_AND_GROUND,0.1),
                r22 = new RouteSegment("Lviv","Kyiv",470, RouteSegment.RouteType.AIR_AND_GROUND,0.1),
                r20 = new RouteSegment("Zaporizhzhia","Odesa",380, RouteSegment.RouteType.AIR_AND_GROUND,0.3),
                r21 = new RouteSegment("Odesa","Zaporizhzhia",380, RouteSegment.RouteType.AIR_AND_GROUND,0.3);
        ArrayList<RouteSegment> rt1 = new ArrayList<>(2); //Zaporizhzhia
        rt1.add(r1); rt1.add(r20);
        ArrayList<RouteSegment> rt2 = new ArrayList<>(4); //Dnipro
        rt2.add(r2); rt2.add(r3); rt2.add(r5); rt2.add(r11);
        ArrayList<RouteSegment> rt3 = new ArrayList<>(3); //Kharkiv
        rt3.add(r4); rt3.add(r9); rt3.add(r18);
        ArrayList<RouteSegment> rt4 = new ArrayList<>(2); //Poltava
        rt4.add(r10); rt4.add(r15);
        ArrayList<RouteSegment> rt5 = new ArrayList<>(5); //Kyiv
        rt5.add(r12); rt5.add(r13); rt5.add(r16); rt5.add(r17); rt5.add(r19);
        ArrayList<RouteSegment> rt6 = new ArrayList<>(4); //Odesa
        rt6.add(r6); rt6.add(r7); rt6.add(r14); rt6.add(r21);
        ArrayList<RouteSegment> rt7 = new ArrayList<>(1); //Istanbul
        rt7.add(r8);
        ArrayList<RouteSegment> rt8 = new ArrayList<>(1); //Lviv
        rt8.add(r22);

        routes.put("Zaporizhzhia", rt1);
        routes.put("Dnipro", rt2);
        routes.put("Kharkiv", rt3);
        routes.put("Poltava", rt4);
        routes.put("Kyiv", rt5);
        routes.put("Odesa", rt6);
        routes.put("Istanbul", rt7);
        routes.put("Lviv", rt8);

        ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream("Company Data/Routes.dat"));
        oos.writeObject(routes);
        oos.close();
    }
    public void writeDemoTransport() throws IOException {
        ArrayList<TransportUnit> tr= new ArrayList<TransportUnit>();
        tr.add(new Truck(123,true,"Zaporizhzhia",20000,false,70, 50));
        tr.add(new Truck(125,true,"Lviv",16000,true,90,70));
        tr.add(new Ferry(201,true,"Odesa",1000000,true,50,1000));
        tr.add(new Plane(330,true,"Kyiv",50000,true,400,1100));
        tr.add(new Plane(344,true,"Kharkiv",60000,false,370,1100));
        tr.add(new Drone(612,true,"Dnipro",20,false,100,10,40));

        ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream("Company Data/Transport.dat"));
        oos.writeObject(tr);
        oos.close();
    }
    public void writeDemoOrder(int orderID, String cargo_type, double cargo_weight, boolean cargo_is_perishable,
                               boolean cargo_is_fragile, String customer_name, String customer_contacts, String start,
                               String destination) throws IOException{
        Cargo cargo = new Cargo(cargo_type,cargo_weight,cargo_is_perishable,cargo_is_fragile);
        Order order = new Order(orderID,customer_name,customer_contacts,cargo,start,destination);
        ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream("Order.dat"));
        oos.writeObject(order);
        oos.close();
    }
}