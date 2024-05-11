package ece651.sp22.grp8.risk;

import java.util.HashMap;

public class UnitUpgradeChecker extends ActionRuleChecker{
    public UnitUpgradeChecker(ActionRuleChecker next) {
        super(next);
    }

    public static String checkUpgradeUnit(Map map, Player player, String location, int oriLevel, int num, int destLevel, HashMap<Integer, Integer> costMap){
        Territory t = map.getTerritoryByName(location);
        if(!t.getOwner().getColor().equals(player.getColor()))return GamePrompt.START_NOT_BELONG;
        if(!t.getUnitMap().containsKey(oriLevel)||oriLevel<0||destLevel<0||oriLevel>6||destLevel>6){
            return GamePrompt.UNIT_NOT_EXIST;
        }
        int n=t.getUnitMap().getOrDefault(oriLevel,-1);
        if(n<num||n==-1) return GamePrompt.UNIT_NOT_ENOUGH;
        if(destLevel> player.techLevel||oriLevel>t.getOwner().techLevel) return GamePrompt.TECH_LEVEL_NOT_ENOUGH;

        if(destLevel<=oriLevel){
            return GamePrompt.DIFFERENCE_WRONG;
        }

        int cost=(costMap.get(destLevel)-costMap.get(oriLevel))*num;
        if(cost> player.techAmount) return GamePrompt.TECH_NOT_ENOUGH;

        return GamePrompt.OK;
    }
}
