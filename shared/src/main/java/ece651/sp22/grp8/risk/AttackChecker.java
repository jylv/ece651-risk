package ece651.sp22.grp8.risk;

import java.util.List;
import java.util.Set;

public class AttackChecker extends ActionRuleChecker{
    public AttackChecker(ActionRuleChecker next) {
        super(next);
    }


    /**
     * To check whether the attack territory is valid
     * @param map
     * @param begin
     * @param end
     * @param player
     * @return
     */
    public String checkAttack(Map map,String begin,String end,Player player,int level, int toAttack) {
        if(!checkOwner(map,begin,player)){
            return GamePrompt.NOT_BELONG;
        }else if(checkOwner(map,end,player)){
            return GamePrompt.DEST_IS_BELONG;
        }else if(!checkLevelExist(map,level)){
            return GamePrompt.LEVEL_NOT_EXIST;
        }else if(!checkUnit(map,toAttack)){
            return GamePrompt.UNIT_SHOULD_POSITIVE;
        }else if(!checkInit(map,begin,level,toAttack)){
            return GamePrompt.UNIT_NOT_ENOUGH_ATTACK;
        }else if(!checkFood(toAttack,player.getFoodAmount())){
            return GamePrompt.FOOD_NOT_ENOUGH;
        }
        List<String> neighbor = map.getTerritoriesMap().get(begin);
        return neighbor.contains(end)?GamePrompt.OK:GamePrompt.CANNOT_ATTACK;
    }
}
