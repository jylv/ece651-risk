package ece651.sp22.grp8.risk;

import java.util.HashMap;

public class TechUpgradeChecker extends ActionRuleChecker{

    public TechUpgradeChecker(ActionRuleChecker next) {
        super(next);
    }

    public String techLevelChecker(Player player,HashMap<Integer,Integer> upgradeCost){
        if(player.hasUpgrade) return GamePrompt.HAS_UPDATE;
        if(player.techLevel==6) return GamePrompt.TECH_LEVEL_MAX;
        int cost=upgradeCost.get(player.techLevel);
        if(player.techAmount<cost)  return GamePrompt.TECH_NOT_ENOUGH;
        return GamePrompt.OK;
    }
}

