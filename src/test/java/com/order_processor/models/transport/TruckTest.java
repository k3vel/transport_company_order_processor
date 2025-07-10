package com.order_processor.models.transport;

import com.order_processor.models.Cargo;
import com.order_processor.models.RouteSegment;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TruckTest {
    Cargo cargo1, cargo2;
    RouteSegment route1,route2,route3;

    @BeforeEach
    void setUp() {
        cargo1 = new Cargo("Type",1234,true,false);
        cargo2 = new Cargo("Type",2345,false,true);
        route1 = new RouteSegment("A","B",500, RouteSegment.RouteType.GROUND,0.5);
        route2 = new RouteSegment("A","B",500, RouteSegment.RouteType.AIR,0.1);
        route3 = new RouteSegment("A","B",500, RouteSegment.RouteType.GROUND,0.8);
    }
    @Test
    public void testCan_Deliver(){
        Truck truck1 = new Truck(1,false,"X",1000,true,100,1);
        Assertions.assertFalse(truck1.Can_Deliver(cargo1,route1)); //is_free
        Truck truck2 = new Truck(2,true,"Z",123,true,100,1);
        Assertions.assertFalse(truck2.Can_Deliver(cargo1,route1)); //capacity
        Truck truck3 = new Truck(3,true,"X",1234,true,100,1);
        Assertions.assertTrue(truck3.Can_Deliver(cargo1,route1)); //normal
        Assertions.assertFalse(truck2.Can_Deliver(cargo1,route2)); //route_type
        Assertions.assertFalse(truck2.Can_Deliver(cargo1,route3)); //difficulty_index
        Truck truck4 = new Truck(4,true,"Z",12345,false,100,1);
        Assertions.assertFalse(truck4.Can_Deliver(cargo1,route1)); //has_refregerator -
        Assertions.assertTrue(truck4.Can_Deliver(cargo2,route1)); //has_refregerator +
    }
    @Test
    public void testDelivery_Price(){
        Truck truck1 = new Truck(1,false,"z",1235,true,100,1);
        Assertions.assertThrows(RuntimeException.class, () -> truck1.Delivery_Price(cargo1,route1)); //can_deliver -
        Truck truck2 = new Truck(2,true,"z",12345,true,100,1);
        Assertions.assertEquals(3617.0,truck2.Delivery_Price(cargo1,route1)); //normal
    }
    @Test
    public void testDelivery_Time(){
        Truck truck1 = new Truck(1,false,"z",1235,true,100,1);
        Assertions.assertThrows(RuntimeException.class, () -> truck1.Delivery_Time(cargo1,route1));
        Truck truck2 = new Truck(2,true,"z",12345,true,100,1);
        Assertions.assertEquals(7.5,truck2.Delivery_Time(cargo1,route1));
    }
}