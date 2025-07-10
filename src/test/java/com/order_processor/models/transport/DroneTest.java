package com.order_processor.models.transport;

import com.order_processor.models.Cargo;
import com.order_processor.models.RouteSegment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class DroneTest{
    Cargo cargo1, cargo2;
    RouteSegment route1,route2,route3;

    @BeforeEach
    void setUp() {
        cargo1 = new Cargo("Type",2,false,false);
        cargo2 = new Cargo("Type",5,true,true);
        route1 = new RouteSegment("A","B",10, RouteSegment.RouteType.AIR,0.5);
        route2 = new RouteSegment("A","B",10, RouteSegment.RouteType.WATER,0.1);
        route3 = new RouteSegment("A","B",100, RouteSegment.RouteType.AIR,0.1);
    }
    @Test
    public void testCan_Deliver(){
        Drone drone1 = new Drone(1,false,"X",5,80,10,15);
        Assertions.assertFalse(drone1.Can_Deliver(cargo1,route1)); //is_free
        Drone drone2 = new Drone(2,true,"Z",1,80,10,15);
        Assertions.assertFalse(drone2.Can_Deliver(cargo1,route1)); //capacity
        Drone drone3 = new Drone(3,true,"X",5,80,10,15);
        Assertions.assertTrue(drone3.Can_Deliver(cargo1,route1)); //normal
        Assertions.assertFalse(drone2.Can_Deliver(cargo1,route2)); //route_type
        Drone drone4 = new Drone(4,true,"Z",5,80,10,15);
        Assertions.assertFalse(drone4.Can_Deliver(cargo2,route1)); //perishable cargo
        Assertions.assertFalse(drone3.Can_Deliver(cargo1,route3)); //max_distance
    }
    @Test
    public void testDelivery_Price(){
        Drone drone1 = new Drone(1,false,"z",5,80,10,15);
        Assertions.assertThrows(RuntimeException.class, () -> drone1.Delivery_Price(cargo1,route1)); //can_deliver -
        Drone drone2 = new Drone(2,true,"z",5,80,1,15);
        Assertions.assertEquals(61.0,drone2.Delivery_Price(cargo1,route1)); //normal
    }
    @Test
    public void testDelivery_Time(){
        Drone drone1 = new Drone(1,false,"z",5,80,10,15);
        Assertions.assertThrows(RuntimeException.class, () -> drone1.Delivery_Time(cargo1,route1)); // can_deliver -
        Drone drone2 = new Drone(2,true,"z",5,80,10,15);
        Assertions.assertEquals(0.1875,drone2.Delivery_Time(cargo1,route1)); //normal
    }
}