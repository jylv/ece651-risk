package ece651.sp22.grp8.risk;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class Map implements Serializable {
    private int initialUnits; //total units the player can use ast beginning
    public int initialFood; // total food the player holds at the beginning
    public  int initialTech; // total tech the player holds at the beginning
    private HashMap<String,ArrayList<Territory>> groups; //store the territories for one color
    private HashMap<Long,String> assign = new HashMap<>(); //<id,color>
    private HashMap<String,ArrayList<String>> territoriesMap; //store the territory and its neighbors
    private Set<Territory> territorySet; //store the whole territoeries of map
    public HashMap<String,Long> attackResult=new HashMap<>(); //store the attackresult of one turn

    /**
     * the setter functions
     */
    public void setInitialUnits(int initialUnits) {
        this.initialUnits = initialUnits;
    }

    public void setTerritoriesMap(HashMap<String, ArrayList<String>> territoriesMap) {
        this.territoriesMap = territoriesMap;
    }

    public void setGroups(HashMap<String, ArrayList<Territory>> groups) {
        this.groups = groups;
    }

    public void setTerritorySet(Set<Territory> territorySet) {
        this.territorySet = territorySet;
    }

    public int getTerritorySize() {return this.territorySet.size();}

    public void setAssign(Collection<Player> players){
        HashMap<Long,String> assign = new HashMap<>();
        List<String> arr = groups.keySet().stream().collect(Collectors.toList());
        ArrayList<String> colors=new ArrayList<>();
        colors.addAll(arr);
        int count=0;
        for(Player p:players){
            String colorForPlayer = colors.get(count++);
            assign.put(p.getPlayerID(),colorForPlayer);
            p.setColor(colorForPlayer);
            ArrayList<Territory> territories = groups.get(colorForPlayer);
            for (Territory t: territories){
                t.setOwner(p);
            }
            groups.put(colorForPlayer,territories);
        }
        this.assign=assign;
    }

    /**
     * the getter functions
     */
    public int getInitialUnits() {
        return initialUnits;
    }

    public HashMap<String, ArrayList<String>> getTerritoriesMap() {
        return territoriesMap;
    }

    public String getAssign(long playerID) {
        return assign.get(playerID);
    }

    public HashMap<String, ArrayList<Territory>> getGroups() {
        return groups;
    }

    public Set<Territory> getTerritorySet() {
        return territorySet;
    }

    public Territory getTerritoryByName(String name){
        for(Territory t: territorySet){
            if(t.getName().equals(name)){
                return t;
            }
        }
        return null;
    }

    public ArrayList<Territory> getTerritoriesByOwnerId(long pleyerId){
        ArrayList<Territory> terrs = new ArrayList<>();
        Set<String> set=territoriesMap.keySet();
        for(String name : set){
            Territory t= getTerritoryByName(name);
            if(t.getOwner()==null)continue;
            if(t.getOwner().getPlayerID()==pleyerId){
                terrs.add(t);
            }
        }
        return terrs;
    }


    /**
     * determine whether two class are the same
     * @param other
     * @return
     */
    @Override
    public boolean equals(Object other){
        if(other != null && other.getClass().equals(getClass())){
            Map otherMap = (Map)other;
            boolean ans = (initialUnits==otherMap.initialUnits)
                    &&(assign.equals(otherMap.assign))
                    &&(territoriesMap.equals(otherMap.territoriesMap));
            if(ans&&groups.keySet().equals(otherMap.groups.keySet())){
                for(String color : groups.keySet()){
                    if(!compareSetTerritory(groups.get(color),otherMap.groups.get(color))){
                        return false;
                    }
                }
                return true;
            }else{
                return false;
            }
        }
        return false;
    }

    public boolean compareSetTerritory(ArrayList<Territory> s1,ArrayList<Territory> s2){
        if(s1.size()!=s2.size()){
            return false;
        }
        int sameNum = 0;
        for(Territory t1 : s1){
            for(Territory t2 : s2){
                if(t1.equals(t2)){
                    ++sameNum;
                    continue;
                }
            }
        }
        return sameNum == s1.size();
    }


    public synchronized void initTerritoryUnits(InitializeUnit iu, String color){
        for(Territory t : groups.get(color)){
            t.getUnitMap().replace(0,0,iu.getUnitByName(t.getName()));
        }
    }

    /**
     * do initialUnitsPhase and genetage InitializeUnit
     * @return
     */
    public InitializeUnit doDefaultInitializeUnits(long playerID){
        //do the initialUnitPhase
        generateUnits(playerID);
        //generate InitializeUnit and InitializeUnitPacket
        HashMap<String, Integer> territoryByUnit = new HashMap<>();
        for(Territory t: getTerritoriesByOwnerId(playerID)){
            territoryByUnit.put(t.getName(),t.getUnitMap().get(0));
        }
        InitializeUnit iU= new InitializeUnit(playerID,territoryByUnit);
        return iU;
    }

    /**
     * set default unit for all territories
     */
    private void generateUnits(long playerID){
        List<Territory> arr = getTerritoriesByOwnerId(playerID).stream().toList();
        ArrayList<Territory> territories=new ArrayList<>();
        territories.addAll(arr);
        int flag=0;
        for(Territory t: territories){
            if(flag==0){
                t.getUnitMap().replace(0,0,initialUnits);
                flag=1;
            }
        }
        getGroups().put(getAssign(playerID),territories);
    }

}
