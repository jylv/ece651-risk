package ece651.sp22.grp8.risk;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class MapTextViewTest {
  @Test
  public void test_for_two_and_four() {
    MapFactory mf = new MapFactory();
    Map m1 = mf.makeMapForTwo();
    Map m2 = mf.makeMapForTwo();
    MapTextView mtv1 = new MapTextView(m1);
    MapTextView mtv2 = new MapTextView(m2);
    String s1=mtv1.printMapByPlayer(m1);
    String s2=mtv2.printMapByPlayer(m2);
    String expected="**************\n" +
            "Red player :\n" +
            "**************\n" +
            "0 unites in Elantris(size:3 food prod:2 tech prod:3)\n" +
            "Neighbors:(Narnia,Midkemia,Scadrial,Roshar):\n" +
            "--------------\n" +
            "0 unites in Hogwarts(size:3 food prod:4 tech prod:1)\n" +
            "Neighbors:(Mordor,Scadrial,Roshar):\n" +
            "--------------\n" +
            "0 unites in Mordor(size:1 food prod:4 tech prod:7)\n" +
            "Neighbors:(Scadrial,Hogwarts):\n" +
            "--------------\n" +
            "0 unites in Narnia(size:1 food prod:6 tech prod:5)\n" +
            "Neighbors:(Elantris,Midkemia):\n" +
            "--------------\n" +
            "**************\n" +
            "Green player :\n" +
            "**************\n" +
            "0 unites in Midkemia(size:1 food prod:3 tech prod:5)\n" +
            "Neighbors:(Narnia,Elantris,Scadrial,Oz):\n" +
            "--------------\n" +
            "0 unites in Oz(size:1 food prod:8 tech prod:6)\n" +
            "Neighbors:(Midkemia,Scadrial):\n" +
            "--------------\n" +
            "0 unites in Roshar(size:2 food prod:3 tech prod:3)\n" +
            "Neighbors:(Elantris,Scadrial,Hogwarts):\n" +
            "--------------\n" +
            "0 unites in Scadrial(size:4 food prod:2 tech prod:2)\n" +
            "Neighbors:(Elantris,Midkemia,Oz,Mordor,Hogwarts,Roshar):\n" +
            "--------------\n";
    assertEquals(expected,s1);
    assertEquals(s1,s2);

    m1.getTerritoryByName("Scadrial").increaseUnit(0,1);
    String expected2 = "**************\n" +
            "Red player :\n" +
            "**************\n" +
            "0 unites in Elantris(size:3 food prod:2 tech prod:3)\n" +
            "Neighbors:(Narnia,Midkemia,Scadrial,Roshar):\n" +
            "--------------\n" +
            "0 unites in Hogwarts(size:3 food prod:4 tech prod:1)\n" +
            "Neighbors:(Mordor,Scadrial,Roshar):\n" +
            "--------------\n" +
            "0 unites in Mordor(size:1 food prod:4 tech prod:7)\n" +
            "Neighbors:(Scadrial,Hogwarts):\n" +
            "--------------\n" +
            "0 unites in Narnia(size:1 food prod:6 tech prod:5)\n" +
            "Neighbors:(Elantris,Midkemia):\n" +
            "--------------\n" +
            "**************\n" +
            "Green player :\n" +
            "**************\n" +
            "0 unites in Midkemia(size:1 food prod:3 tech prod:5)\n" +
            "Neighbors:(Narnia,Elantris,Scadrial,Oz):\n" +
            "--------------\n" +
            "0 unites in Oz(size:1 food prod:8 tech prod:6)\n" +
            "Neighbors:(Midkemia,Scadrial):\n" +
            "--------------\n" +
            "0 unites in Roshar(size:2 food prod:3 tech prod:3)\n" +
            "Neighbors:(Elantris,Scadrial,Hogwarts):\n" +
            "--------------\n" +
            "1 unites in Scadrial(size:4 food prod:2 tech prod:2)\n" +
            "Neighbors:(Elantris,Midkemia,Oz,Mordor,Hogwarts,Roshar):\n" +
            "1 units in level 0\n" +
            "--------------\n";
    assertEquals(expected2,mtv1.printMapByPlayer(m1));


    HashMap<String, ArrayList<Territory>> aaa = m2.getGroups();
    HashMap newH = new HashMap<>();
    for(String t:aaa.keySet()){
        newH.put(t,new ArrayList<>());
    }
    m2.setGroups(newH);
    MapTextView mtv22 = new MapTextView(m2);
    mtv22.printMapByPlayer(m2);

  }


  @Test
  public void test_for_three() {
    MapFactory mf = new MapFactory();
    Map m = mf.makeMapForThree();
    MapTextView mtv = new MapTextView(m);
    String s=mtv.printMapByPlayer(m);
    String expected="**************\n" +
            "Red player :\n" +
            "**************\n" +
            "0 unites in Elantris(size:4 food prod:6 tech prod:4)\n" +
            "Neighbors:(Narnia,Midkemia,Scadrial,Roshar):\n" +
            "--------------\n" +
            "0 unites in Mordor(size:3 food prod:4 tech prod:6)\n" +
            "Neighbors:(Oz,Gondor,Scadrial,Hogwarts):\n" +
            "--------------\n" +
            "0 unites in Narnia(size:2 food prod:8 tech prod:8)\n" +
            "Neighbors:(Elantris,Midkemia):\n" +
            "--------------\n" +
            "**************\n" +
            "Blue player :\n" +
            "**************\n" +
            "0 unites in Gondor(size:2 food prod:10 tech prod:10)\n" +
            "Neighbors:(Oz,Mordor):\n" +
            "--------------\n" +
            "0 unites in Oz(size:3 food prod:4 tech prod:4)\n" +
            "Neighbors:(Scadrial,Midkemia,Mordor,Gondor):\n" +
            "--------------\n" +
            "0 unites in Roshar(size:4 food prod:4 tech prod:4)\n" +
            "Neighbors:(Elantris,Scadrial,Hogwarts):\n" +
            "--------------\n" +
            "**************\n" +
            "Green player :\n" +
            "**************\n" +
            "0 unites in Hogwarts(size:2 food prod:8 tech prod:8)\n" +
            "Neighbors:(Mordor,Scadrial,Roshar):\n" +
            "--------------\n" +
            "0 unites in Midkemia(size:2 food prod:8 tech prod:6)\n" +
            "Neighbors:(Narnia,Elantris,Scadrial,Oz):\n" +
            "--------------\n" +
            "0 unites in Scadrial(size:5 food prod:2 tech prod:4)\n" +
            "Neighbors:(Elantris,Midkemia,Oz,Mordor,Hogwarts,Roshar):\n" +
            "--------------\n";
    assertEquals(expected,s);
  }


  @Test
  public void test_for_five() {
    MapFactory mf = new MapFactory();
    Map m = mf.generateMapWithPlayer(5);
    MapTextView mtv = new MapTextView(m);
    String s=mtv.printMapByPlayer(m);
    String expected="**************\n" +
            "Red player :\n" +
            "**************\n" +
            "0 unites in Elantris(size:3 food prod:6 tech prod:5)\n" +
            "Neighbors:(Narnia,Midkemia,Scadrial,Roshar):\n" +
            "--------------\n" +
            "0 unites in Mordor(size:1 food prod:10 tech prod:11)\n" +
            "Neighbors:(Oz,Gondor,Scadrial,Hogwarts):\n" +
            "--------------\n" +
            "**************\n" +
            "White player :\n" +
            "**************\n" +
            "0 unites in Oz(size:1 food prod:12 tech prod:11)\n" +
            "Neighbors:(Scadrial,Midkemia,Mordor,Gondor):\n" +
            "--------------\n" +
            "0 unites in Roshar(size:2 food prod:9 tech prod:7)\n" +
            "Neighbors:(Elantris,Scadrial,Hogwarts):\n" +
            "--------------\n" +
            "**************\n" +
            "Blue player :\n" +
            "**************\n" +
            "0 unites in Midkemia(size:2 food prod:7 tech prod:9)\n" +
            "Neighbors:(Narnia,Elantris,Scadrial,Oz):\n" +
            "--------------\n" +
            "0 unites in Scadrial(size:3 food prod:4 tech prod:5)\n" +
            "Neighbors:(Elantris,Midkemia,Oz,Mordor,Hogwarts,Roshar):\n" +
            "--------------\n" +
            "**************\n" +
            "Yellow player :\n" +
            "**************\n" +
            "0 unites in Oz(size:1 food prod:12 tech prod:11)\n" +
            "Neighbors:(Scadrial,Midkemia,Mordor,Gondor):\n" +
            "--------------\n" +
            "0 unites in Roshar(size:2 food prod:9 tech prod:7)\n" +
            "Neighbors:(Elantris,Scadrial,Hogwarts):\n" +
            "--------------\n" +
            "**************\n" +
            "Green player :\n" +
            "**************\n" +
            "0 unites in Hogwarts(size:2 food prod:7 tech prod:9)\n" +
            "Neighbors:(Mordor,Scadrial,Roshar,Lasagna):\n" +
            "--------------\n" +
            "0 unites in Narnia(size:2 food prod:9 tech prod:7)\n" +
            "Neighbors:(Elantris,Midkemia,Lasagna):\n" +
            "--------------\n";
    assertEquals(expected,s);

    m.getGroups().replace("Green",m.getGroups().get("Green"),new ArrayList<>());
    String ex = "**************\n" +
            "Red player :\n" +
            "**************\n" +
            "0 unites in Elantris(size:3 food prod:6 tech prod:5)\n" +
            "Neighbors:(Narnia,Midkemia,Scadrial,Roshar):\n" +
            "--------------\n" +
            "0 unites in Mordor(size:1 food prod:10 tech prod:11)\n" +
            "Neighbors:(Oz,Gondor,Scadrial,Hogwarts):\n" +
            "--------------\n" +
            "**************\n" +
            "White player :\n" +
            "**************\n" +
            "0 unites in Oz(size:1 food prod:12 tech prod:11)\n" +
            "Neighbors:(Scadrial,Midkemia,Mordor,Gondor):\n" +
            "--------------\n" +
            "0 unites in Roshar(size:2 food prod:9 tech prod:7)\n" +
            "Neighbors:(Elantris,Scadrial,Hogwarts):\n" +
            "--------------\n" +
            "**************\n" +
            "Blue player :\n" +
            "**************\n" +
            "0 unites in Midkemia(size:2 food prod:7 tech prod:9)\n" +
            "Neighbors:(Narnia,Elantris,Scadrial,Oz):\n" +
            "--------------\n" +
            "0 unites in Scadrial(size:3 food prod:4 tech prod:5)\n" +
            "Neighbors:(Elantris,Midkemia,Oz,Mordor,Hogwarts,Roshar):\n" +
            "--------------\n" +
            "**************\n" +
            "Yellow player :\n" +
            "**************\n" +
            "0 unites in Oz(size:1 food prod:12 tech prod:11)\n" +
            "Neighbors:(Scadrial,Midkemia,Mordor,Gondor):\n" +
            "--------------\n" +
            "0 unites in Roshar(size:2 food prod:9 tech prod:7)\n" +
            "Neighbors:(Elantris,Scadrial,Hogwarts):\n" +
            "--------------\n";
    assertEquals(ex,mtv.printMapByPlayer(m));
  }

}
