package ece651.sp22.grp8.risk;

import ece651.sp22.grp8.risk.Map;
import ece651.sp22.grp8.risk.Player;
import ece651.sp22.grp8.risk.Territory;

import java.util.*;

public class MapTextView {
    private Map map;

    public MapTextView(Map map) {
        this.map = map;
    }

    /**
     * print the map by player, shown different unit in territory
     * @param map
     * @return
     */
    public String printMapByPlayer(Map map){
        StringBuffer sb = new StringBuffer();
        Set<String> players = map.getGroups().keySet();
        for(String p:players){
            if(map.getGroups().get(p).size()==0){continue;}
            sb.append("**************\n");
            sb.append(p+" player :\n");
            sb.append("**************\n");
            ArrayList<Territory> tSet = map.getGroups().get(p);
            for(Territory t:tSet){
                ArrayList<String> tt = map.getTerritoriesMap().getOrDefault(t.getName(),new ArrayList<>());
                sb.append(t.getUnitsSum()+" unites in "+t.getName());
                sb.append( "(size:"+t.size+" food prod:"+t.foodProd+" tech prod:"+t.techProd+")\n");
                //print neighbor
                int count =0;
                sb.append("Neighbors:(");
                for(String n:tt){
                    sb.append(n);
                    if(count!=map.getTerritoriesMap().get(t.getName()).size()-1) sb.append(",");
                    count++;
                }
                sb.append("):\n");
                //print units with level
                if(t.getUnitsSum()!=0){
                    for(int i:t.getUnitMap().keySet()){
                        if(t.getUnitMap().get(i)==0){
                            continue;
                        }
                        else{
                            sb.append(t.getUnitMap().get(i)+" units in level "+i+"\n");
                        }
                    }
                }

                sb.append("--------------\n");
            }

        }
        return sb.toString();
    }

}
