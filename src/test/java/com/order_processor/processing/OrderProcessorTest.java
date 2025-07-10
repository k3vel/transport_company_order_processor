package com.order_processor.processing;

import com.order_processor.DemoDataCreator;
import com.order_processor.models.Cargo;
import com.order_processor.models.RouteSegment;
import com.order_processor.models.transport.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Map;

public class OrderProcessorTest {
    @Test
    public void TestProcess(){
        DemoDataCreator d = new DemoDataCreator();
        ArrayList<TransportUnit> transportUnits = d.createDemoTransport();
        Map<String, ArrayList<RouteSegment>> routes = d.createDemoRoutes();
        CompanyOrderProcessor cop = new CompanyOrderProcessor(transportUnits, routes);

        Cargo cargo = new Cargo("0",1000,false,true);
        Order order = new Order(0,"A","Z",cargo,"Dnipro","Istanbul");

        TransportUnit tu1 = new Truck(123,true,"Zaporizhzhia",20000,false,70,50),
                tu2 = new Ferry(201,true,"Odesa",1000000,true,50,1000);
        RouteSegment seg1 = new RouteSegment("Dnipro","Odesa",450, RouteSegment.RouteType.GROUND,0.3),
                seg2 = new RouteSegment("Odesa","Istanbul",600, RouteSegment.RouteType.WATER_AND_AIR,0.1);
        Assignment as1 = new Assignment(seg1,tu1), as2 = new Assignment(seg2,tu2);
        Response response = cop.Process(order);
        Assertions.assertEquals(0, response.getStatus());
        Assertions.assertEquals(2,response.getDelivery_plan().size());
        Assertions.assertEquals(as1,response.getDelivery_plan().get(0));
        Assertions.assertEquals(as2,response.getDelivery_plan().get(1));
        Assertions.assertEquals(754075.0,response.getPrice());
        Assertions.assertEquals(22.60714285714286,response.getTime());
    }
}