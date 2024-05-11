package ece651.sp22.grp8.risk;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

public class InitializeUnitTest {
  @Test
  public void test_const() {
    String Terro1 = "Duke";
    String Terro2 = "UNC";
    String Terro3 = "UW";
    HashMap<String, Integer> TerriorityByUnit = new HashMap<String, Integer>();
    TerriorityByUnit.put(Terro3, 10);
    TerriorityByUnit.put(Terro2, 1);
    TerriorityByUnit.put(Terro1, 9);

    InitializeUnit it = new InitializeUnit(10, TerriorityByUnit);
    assertEquals(it.toString(), it.toString());
    assertEquals(9,it.getUnitByName("Duke"));
  }

  @Test
  public void test_get() {
    String Terro1 = "Duke";
    String Terro2 = "UNC";
    String Terro3 = "UW";
    HashMap<String, Integer> TerriorityByUnit = new HashMap<String, Integer>();
    TerriorityByUnit.put(Terro3, 10);
    TerriorityByUnit.put(Terro2, 1);
    TerriorityByUnit.put(Terro1, 9);

    InitializeUnit it = new InitializeUnit(10, TerriorityByUnit);
    assertEquals(9, it.getUnitByName("Duke"));
    assertEquals(1, it.getUnitByName("UNC"));
    assertEquals(10, it.getUnitByName("UW"));
  }

  @Test
  public void test_equal() {
    String Terro1 = "Duke";
    String Terro2 = "UNC";
    String Terro3 = "UW";
    HashMap<String, Integer> TerriorityByUnit = new HashMap<String, Integer>();
    TerriorityByUnit.put(Terro3, 10);
    TerriorityByUnit.put(Terro2, 1);
    TerriorityByUnit.put(Terro1, 9);

    InitializeUnit it1 = new InitializeUnit(10, TerriorityByUnit);
    InitializeUnit it2 = new InitializeUnit(10, TerriorityByUnit);
    InitializeUnit it3 = new InitializeUnit(9, TerriorityByUnit);
    InitializeUnit it4 = new InitializeUnit(10, new HashMap<>());

    assertEquals(it1,it2);
    assertEquals(it1.hashCode(), it2.hashCode());
    assertNotEquals(it1,it3);
    assertNotEquals(it1,it4);
    assertNotEquals(it1.hashCode(),it4.hashCode());
    assertNotEquals(it1,1);
  }

}
