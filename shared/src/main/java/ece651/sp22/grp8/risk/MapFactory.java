package ece651.sp22.grp8.risk;
import ece651.sp22.grp8.risk.Map;
import ece651.sp22.grp8.risk.Player;
import ece651.sp22.grp8.risk.Territory;

import javax.print.attribute.HashAttributeSet;
import java.util.*;
import java.util.stream.Collectors;

public class MapFactory{
    public Map generateMapWithPlayer(int num){
        Map map;
        if(num==2) map = makeMapForTwo();
        else if(num==3) map = makeMapForThree();
        else if(num==4) map = makeMapForFour();
        else map = makeMapForFive();
        return map;
    }


    /**
     * create the territory for three players map.
     * @return Map
     */
    public Map makeMapForThree(){
        Map map = new Map();
        // set initial units
        map.setInitialUnits(9);
        map.initialTech = 18;
        map.initialFood= 18;
        // generate territories
        Territory Narnia = new Territory("Narnia",2,8,8);
        Territory Elantris = new Territory("Elantris",4,6,4);
        Territory Mordor = new Territory("Mordor",3,4,6);

        Territory Midkemia = new Territory("Midkemia",2,8,6);
        Territory Hogwarts = new Territory("Hogwarts",2,8,8);
        Territory Scadrial = new Territory("Scadrial",5,2,4);

        Territory Gondor = new Territory("Gondor",2,10,10);
        Territory Oz = new Territory("Oz",3,4,4);
        Territory Roshar = new Territory("Roshar",4,4,4);

        map.setTerritorySet(new HashSet<>(Arrays.asList(Narnia,Midkemia,Oz,Elantris,Scadrial,Roshar,Mordor,Hogwarts,Gondor)));
        // generate neighbors
        HashMap<String,ArrayList<String>> territoriesMap = new HashMap<>();
        territoriesMap.put("Mordor",new ArrayList<>(Arrays.asList("Oz","Gondor","Scadrial","Hogwarts")));
        territoriesMap.put("Narnia",new ArrayList<>(Arrays.asList("Elantris","Midkemia")));
        territoriesMap.put("Midkemia",new ArrayList<>(Arrays.asList("Narnia","Elantris","Scadrial","Oz")));
        territoriesMap.put("Oz",new ArrayList<>(Arrays.asList("Scadrial","Midkemia","Mordor","Gondor")));
        territoriesMap.put("Gondor",new ArrayList<>(Arrays.asList("Oz","Mordor")));
        territoriesMap.put("Elantris",new ArrayList<>(Arrays.asList("Narnia","Midkemia","Scadrial","Roshar")));
        territoriesMap.put("Scadrial",new ArrayList<>(Arrays.asList("Elantris","Midkemia","Oz","Mordor","Hogwarts","Roshar")));
        territoriesMap.put("Roshar",new ArrayList<>(Arrays.asList("Elantris","Scadrial","Hogwarts")));
        territoriesMap.put("Hogwarts",new ArrayList<>(Arrays.asList("Mordor","Scadrial","Roshar")));
        map.setTerritoriesMap(territoriesMap);
        // generate groups
        ArrayList<Territory> red = new ArrayList<>();
        red.add(Elantris);
        red.add(Mordor);
        red.add(Narnia);
        ArrayList<Territory> green = new ArrayList<>();
        green.add(Hogwarts);
        green.add(Midkemia);
        green.add(Scadrial);
        ArrayList<Territory> blue = new ArrayList<>();
        blue.add(Gondor);
        blue.add(Oz);
        blue.add(Roshar);
        HashMap<String,ArrayList<Territory>> group = new HashMap<>();
        group.put("Red",red);
        group.put("Green",green);
        group.put("Blue",blue);
        map.setGroups(group);
        return map;
    }

    /**
     * create the territory for two players map.
     * @return Map
     */
    public Map makeMapForTwo(){
        Map map = new Map();
        // set initial units
        map.setInitialUnits(8);
        map.initialTech=16;
        map.initialFood=16;
        // generate territories
        Territory Elantris = new Territory("Elantris",3,2,3);
        Territory Hogwarts = new Territory("Hogwarts",3,4,1);
        Territory Mordor = new Territory("Mordor",1,4,7);
        Territory Narnia = new Territory("Narnia",1,6,5);

        Territory Midkemia = new Territory("Midkemia",1,3,5);
        Territory Oz = new Territory("Oz",1,8,6);
        Territory Roshar = new Territory("Roshar",2,3,3);
        Territory Scadrial = new Territory("Scadrial",4,2,2);

        map.setTerritorySet(new HashSet<>(Arrays.asList(Narnia,Midkemia,Oz,Elantris,Scadrial,Roshar,Mordor,Hogwarts)));
        // generate neighbors
        HashMap<String,ArrayList<String>> territoriesMap = new HashMap<>();
        territoriesMap.put("Oz",new ArrayList<>(Arrays.asList("Midkemia","Scadrial")));
        territoriesMap.put("Mordor",new ArrayList<>(Arrays.asList("Scadrial","Hogwarts")));
        territoriesMap.put("Narnia",new ArrayList<>(Arrays.asList("Elantris","Midkemia")));
        territoriesMap.put("Midkemia",new ArrayList<>(Arrays.asList("Narnia","Elantris","Scadrial","Oz")));
        territoriesMap.put("Elantris",new ArrayList<>(Arrays.asList("Narnia","Midkemia","Scadrial","Roshar")));
        territoriesMap.put("Scadrial",new ArrayList<>(Arrays.asList("Elantris","Midkemia","Oz","Mordor","Hogwarts","Roshar")));
        territoriesMap.put("Roshar",new ArrayList<>(Arrays.asList("Elantris","Scadrial","Hogwarts")));
        territoriesMap.put("Hogwarts",new ArrayList<>(Arrays.asList("Mordor","Scadrial","Roshar")));
        map.setTerritoriesMap(territoriesMap);
        // generate groups
        ArrayList<Territory> red = new ArrayList<>();
        red.add(Elantris);
        red.add(Hogwarts);
        red.add(Mordor);
        red.add(Narnia);
        ArrayList<Territory> green = new ArrayList<>();
        green.add(Midkemia);
        green.add(Oz);
        green.add(Roshar);
        green.add(Scadrial);
        HashMap<String,ArrayList<Territory>> group = new HashMap<>();
        group.put("Red",red);
        group.put("Green",green);
        map.setGroups(group);
        return map;
    }

    /**
     * create the territory for four players map.
     * @return Map
     */
    public Map makeMapForFour(){
        Map map = new Map();
        // set initial units
        map.setInitialUnits(8);
        map.initialTech=16;
        map.initialFood=16;
        // generate territories
        Territory Elantris = new Territory("Elantris",3,6,5);
        Territory Mordor = new Territory("Mordor",1,10,11);

        Territory Hogwarts = new Territory("Hogwarts",2,7,9);
        Territory Narnia = new Territory("Narnia",2,9,7);

        Territory Oz = new Territory("Oz",1,12,11);
        Territory Scadrial = new Territory("Scadrial",3,4,5);

        Territory Midkemia = new Territory("Midkemia",2,7,9);
        Territory Roshar = new Territory("Roshar",2,9,7);
        map.setTerritorySet(new HashSet<>(Arrays.asList(Narnia,Midkemia,Oz,Elantris,Scadrial,Roshar,Mordor,Hogwarts)));
        // generate neighbors
        HashMap<String,ArrayList<String>> territoriesMap = new HashMap<>();
        territoriesMap.put("Oz",new ArrayList<>(Arrays.asList("Midkemia","Scadrial")));
        territoriesMap.put("Mordor",new ArrayList<>(Arrays.asList("Scadrial","Hogwarts")));
        territoriesMap.put("Narnia",new ArrayList<>(Arrays.asList("Elantris","Midkemia")));
        territoriesMap.put("Midkemia",new ArrayList<>(Arrays.asList("Narnia","Elantris","Scadrial","Oz")));
        territoriesMap.put("Elantris",new ArrayList<>(Arrays.asList("Narnia","Midkemia","Scadrial","Roshar")));
        territoriesMap.put("Scadrial",new ArrayList<>(Arrays.asList("Elantris","Midkemia","Oz","Mordor","Hogwarts","Roshar")));
        territoriesMap.put("Roshar",new ArrayList<>(Arrays.asList("Elantris","Scadrial","Hogwarts")));
        territoriesMap.put("Hogwarts",new ArrayList<>(Arrays.asList("Mordor","Scadrial","Roshar")));
        map.setTerritoriesMap(territoriesMap);
        // generate groups
        ArrayList<Territory> red = new ArrayList<>();
        red.add(Elantris);
        red.add(Mordor);
        ArrayList<Territory> green = new ArrayList<>();
        green.add(Hogwarts);
        green.add(Narnia);
        ArrayList<Territory> blue = new ArrayList<>();
        blue.add(Oz);
        blue.add(Scadrial);
        ArrayList<Territory> yellow = new ArrayList<>();
        yellow.add(Midkemia);
        yellow.add(Roshar);
        HashMap<String,ArrayList<Territory>> group = new HashMap<>();
        group.put("Red",red);
        group.put("Green",green);
        group.put("Blue",blue);
        group.put("Yellow",yellow);
        map.setGroups(group);
        return map;
    }

    /**
     * create the territory for five players map.
     * @return Map
     */
    public Map makeMapForFive(){
        Map map = new Map();
        // set initial units
        map.setInitialUnits(8);
        map.initialTech=16;
        map.initialFood=16;
        // generate territories
        Territory Elantris = new Territory("Elantris",3,6,5);
        Territory Mordor = new Territory("Mordor",1,10,11);

        Territory Hogwarts = new Territory("Hogwarts",2,7,9);
        Territory Narnia = new Territory("Narnia",2,9,7);

        Territory Oz = new Territory("Oz",1,12,11);
        Territory Scadrial = new Territory("Scadrial",3,4,5);

        Territory Midkemia = new Territory("Midkemia",2,7,9);
        Territory Roshar = new Territory("Roshar",2,9,7);

        Territory Gondor = new Territory("Gondor",2,6,10);
        Territory Lasagna = new Territory("Lasagna",2,10,6);

        map.setTerritorySet(new HashSet<>(Arrays.asList(Narnia,Midkemia,Gondor,Oz,Elantris,Scadrial,Roshar,Mordor,Hogwarts,Lasagna)));
        // generate neighbors
        HashMap<String,ArrayList<String>> territoriesMap = new HashMap<>();
        territoriesMap.put("Narnia",new ArrayList<>(Arrays.asList("Elantris","Midkemia","Lasagna")));
        territoriesMap.put("Midkemia",new ArrayList<>(Arrays.asList("Narnia","Elantris","Scadrial","Oz")));
        territoriesMap.put("Oz",new ArrayList<>(Arrays.asList("Scadrial","Midkemia","Mordor","Gondor")));
        territoriesMap.put("Gondor",new ArrayList<>(Arrays.asList("Oz","Mordor","Lasagna")));
        territoriesMap.put("Elantris",new ArrayList<>(Arrays.asList("Narnia","Midkemia","Scadrial","Roshar")));
        territoriesMap.put("Scadrial",new ArrayList<>(Arrays.asList("Elantris","Midkemia","Oz","Mordor","Hogwarts","Roshar")));
        territoriesMap.put("Roshar",new ArrayList<>(Arrays.asList("Elantris","Scadrial","Hogwarts")));
        territoriesMap.put("Hogwarts",new ArrayList<>(Arrays.asList("Mordor","Scadrial","Roshar","Lasagna")));
        territoriesMap.put("Lasagna",new ArrayList<>(Arrays.asList("Gondor","Narnia","Hogwarts")));
        territoriesMap.put("Mordor",new ArrayList<>(Arrays.asList("Oz","Gondor","Scadrial","Hogwarts")));
        map.setTerritoriesMap(territoriesMap);
        // generate groups
        ArrayList<Territory> red = new ArrayList<>();
        red.add(Elantris);
        red.add(Mordor);
        ArrayList<Territory> green = new ArrayList<>();
        green.add(Hogwarts);
        green.add(Narnia);
        ArrayList<Territory> blue = new ArrayList<>();
        blue.add(Oz);
        blue.add(Scadrial);
        ArrayList<Territory> yellow = new ArrayList<>();
        yellow.add(Midkemia);
        yellow.add(Roshar);
        ArrayList<Territory> white = new ArrayList<>();
        white.add(Gondor);
        white.add(Lasagna);
        HashMap<String,ArrayList<Territory>> group = new HashMap<>();
        group.put("Red",red);
        group.put("Green",green);
        group.put("Blue",blue);
        group.put("Yellow",yellow);
        group.put("White",white);
        map.setGroups(group);
        return map;
    }

}
