package com.order_processor.models.transport;

import com.order_processor.models.Cargo;
import com.order_processor.models.RouteSegment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class PlaneTest{
    Cargo cargo1, cargo2;
    RouteSegment route1,route2,route3;

    @BeforeEach
    void setUp() {
        cargo1 = new Cargo("Type",1234,true,false);
        cargo2 = new Cargo("Type",2345,false,true);
        route1 = new RouteSegment("A","B",5000, RouteSegment.RouteType.AIR,0.5);
        route2 = new RouteSegment("A","B",5000, RouteSegment.RouteType.GROUND,0.1);
    }
    @Test
    public void testCan_Deliver(){
        Plane plane1 = new Plane(1,false,"X",3000,false,555,10000);
        Assertions.assertFalse(plane1.Can_Deliver(cargo2,route1)); //is_free
        Plane plane2 = new Plane(2,true,"Z",300,true,555,10000);
        Assertions.assertFalse(plane2.Can_Deliver(cargo1,route1)); //capacity
        Plane plane3 = new Plane(3,true,"X",3000,true,555,10000);
        Assertions.assertTrue(plane3.Can_Deliver(cargo1,route1)); //normal
        Assertions.assertFalse(plane2.Can_Deliver(cargo1,route2)); //route_type
        Plane plane4 = new Plane(4,true,"Z",3000,false,555,10000);
        Assertions.assertFalse(plane4.Can_Deliver(cargo1,route1)); //has_refregerator -
        Assertions.assertTrue(plane4.Can_Deliver(cargo2,route1)); //has_refregerator +
    }
    @Test
    public void testDelivery_Price(){
        Plane plane1 = new Plane(1,false,"z",3000,true,555,1);
        Assertions.assertThrows(RuntimeException.class, () -> plane1.Delivery_Price(cargo1,route1)); //can_deliver -
        Plane plane2 = new Plane(2,true,"z",3000,true,555,1);
        Assertions.assertEquals(30617.0,plane2.Delivery_Price(cargo1,route1)); //normal
    }
    @Test
    public void testDelivery_Time(){
        Plane plane1 = new Plane(1,false,"z",3000,true,555,1);
        Assertions.assertThrows(RuntimeException.class, () -> plane1.Delivery_Time(cargo1,route1)); // can_deliver -
        Plane plane2 = new Plane(2,true,"z",3000,true,500,1);
        Assertions.assertEquals(15.0,plane2.Delivery_Time(cargo1,route1)); //normal
    }
}