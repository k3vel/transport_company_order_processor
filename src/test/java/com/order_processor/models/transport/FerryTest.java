package com.order_processor.models.transport;

import com.order_processor.models.Cargo;
import com.order_processor.models.RouteSegment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class FerryTest{
    Cargo cargo1, cargo2;
    RouteSegment route1,route2,route3;

    @BeforeEach
    void setUp() {
        cargo1 = new Cargo("Type",12345,true,false);
        cargo2 = new Cargo("Type",23456,false,true);
        route1 = new RouteSegment("A","B",500, RouteSegment.RouteType.WATER,0.5);
        route2 = new RouteSegment("A","B",500, RouteSegment.RouteType.AIR,0.1);
    }
    @Test
    public void testCan_Deliver(){
        Ferry ferry1 = new Ferry(1,false,"X",100000,true,40,1000);
        Assertions.assertFalse(ferry1.Can_Deliver(cargo1,route1)); //is_free
        Ferry ferry2 = new Ferry(2,true,"Z",123,true,40,1000);
        Assertions.assertFalse(ferry2.Can_Deliver(cargo1,route1)); //capacity
        Ferry ferry3 = new Ferry(3,true,"X",200000,true,40,1000);
        Assertions.assertTrue(ferry3.Can_Deliver(cargo1,route1)); //normal
        Assertions.assertFalse(ferry2.Can_Deliver(cargo1,route2)); //route_type
        Ferry ferry4 = new Ferry(4,true,"Z",200000,false,40,1);
        Assertions.assertFalse(ferry4.Can_Deliver(cargo1,route1)); //has_refregerator -
        Assertions.assertTrue(ferry4.Can_Deliver(cargo2,route1)); //has_refregerator +
    }
    @Test
    public void testDelivery_Price(){
        Ferry ferry1 = new Ferry(1,false,"z",200000,true,40,1);
        Assertions.assertThrows(RuntimeException.class, () -> ferry1.Delivery_Price(cargo1,route1)); //can_deliver -
        Ferry ferry2 = new Ferry(2,true,"z",200000,true,40,1);
        Assertions.assertEquals(9172.5,ferry2.Delivery_Price(cargo1,route1)); //normal
    }
    @Test
    public void testDelivery_Time(){
        Ferry ferry1 = new Ferry(1,false,"z",200000,true,100,1);
        Assertions.assertThrows(RuntimeException.class, () -> ferry1.Delivery_Time(cargo1,route1)); // can_deliver -
        Ferry ferry2 = new Ferry(2,true,"z",200000,true,100,1);
        Assertions.assertEquals(7.5,ferry2.Delivery_Time(cargo1,route1)); //normal
    }
}