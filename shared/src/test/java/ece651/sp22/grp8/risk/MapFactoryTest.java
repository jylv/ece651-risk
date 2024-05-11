package ece651.sp22.grp8.risk;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Set;

public class MapFactoryTest {
    @Test
    public void test_basic(){
        MapFactory mf = new MapFactory();
        Map m1 =mf.generateMapWithPlayer(2);
        Map m2 =mf.generateMapWithPlayer(3);
        Map m3 =mf.generateMapWithPlayer(4);
        Map m4 =mf.generateMapWithPlayer(5);
        assertEquals(m1.getGroups().get("Red").size(),m1.getGroups().get("Green").size());
        assertEquals(m2.getGroups().get("Red").size(),m2.getGroups().get("Green").size());
        assertEquals(m3.getGroups().get("Red").size(),m3.getGroups().get("Green").size());
        assertEquals(m4.getGroups().get("Red").size(),m4.getGroups().get("Green").size());
        int[] food = new int[2];
        int[] tech = new int[2];
        int[] size = new int[2];
        for(Territory t:m4.getGroups().get("Red")){
            food[0] += t.foodProd;
            tech[0] += t.techProd;
            size[0] += t.size;
        }
        for(Territory t:m4.getGroups().get("Green")){
            food[1] += t.foodProd;
            tech[1] += t.techProd;
            size[1] += t.size;
        }
        assertEquals(food[0],food[1]);
        assertEquals(tech[0],tech[1]);
        assertEquals(size[0],size[1]);
    }


}
