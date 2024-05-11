package ece651.sp22.grp8.risk;

import java.util.List;
import java.util.stream.Collectors;

public abstract class ActionRuleChecker {

    protected ActionRuleChecker next;

    public ActionRuleChecker(ActionRuleChecker next) {
        this.next = next;
    }

    public boolean checkOwner(Map map,String begin,Player p){
        List<Territory> terris = map.getGroups().get(p.getColor()).stream().toList();
        List<String> l = terris.stream().map(Territory::getName).collect(Collectors.toList());
        return l.contains(begin);
    }
    /**
     * To check the unit to move/attack is enough or not
     */
    public boolean checkInit(Map map,String begin,int level,int toMove){
        Territory start=map.getTerritoryByName(begin);
        return start.getUnitMap().getOrDefault(level,-1) >= toMove;
    }

    /**
     * check whether the level exist;
     * @param map
     * @param level
     * @return
     */
    public boolean checkLevelExist(Map map,int level){
        return level>=0&&level<=6;
    }

    /**
     * to check whether the unit to move or attack is a positive number
     * @param map
     * @param toMove
     * @return
     */
    public boolean checkUnit(Map map,int toMove){
        return toMove>0;
    }

    /**
     * To check the food to move/attack is enough or not
     */
    public boolean checkFood(int need, int remain){
        return remain>=need;
    }


}
