package ece651.sp22.grp8.risk;

import java.util.*;
import java.util.stream.Collectors;

public class MoveChecker extends ActionRuleChecker{

    public MoveChecker(ActionRuleChecker next) {
        super(next);
    }


    /**
     * to check whether the path is exit,
     * whether the two territories belong to the player
     * whether the move init is correct
     * @param map
     * @param begin
     * @param end
     * @param player
     * @param toMove
     * @return
     */
    public String checkMove(Map map,String begin,String end,Player player,int level,int toMove){
        Territory start=map.getTerritoryByName(begin);
        //begin's neighbor
        List<String> neighbor = map.getTerritoriesMap().get(start.getName()).stream().toList();
        //check valid
        if(!checkOwner(map,begin,player)){
            return GamePrompt.START_NOT_BELONG;
        }else if(!checkOwner(map,end,player)){
            return GamePrompt.DEST_NOT_BELONG;
        }else if(!checkLevelExist(map,level)){
            return GamePrompt.LEVEL_NOT_EXIST;
        }else if(!checkUnit(map,toMove)){
            return GamePrompt.UNIT_SHOULD_POSITIVE;
        }else if(!checkInit(map,begin,level,toMove)){
            return GamePrompt.UNIT_NOT_ENOUGH_MOVE;
        }else if(!checkMoveHelper(neighbor,end,map,new LinkedList<>(),player)){
            return GamePrompt.CANNOT_REACH_DEST;
        }else if(!checkFood(miniConsume(map,begin,end,player,toMove),player.getFoodAmount())){
            return GamePrompt.FOOD_NOT_ENOUGH;
        }
        return GamePrompt.OK;
    }

    public String checkFoodFunc(Map map,String end,Player player,int cur,int toMove){
        Territory dest =map.getTerritoryByName(end);
        if((player.getFoodAmount()-cur)>=dest.size*toMove){
            return GamePrompt.OK;
        }
        return GamePrompt.FOOD_NOT_ENOUGH;
    }

    /**
     * help function to check whether the moving path is valid
     * @param neighbor
     * @param dest
     * @param map
     * @param visited
     * @return
     */
    public boolean checkMoveHelper(List<String> neighbor,String dest,Map map,List<String> visited,Player p){
        for(int i=0;i<neighbor.size();i++){
            String cur=neighbor.get(i);
            if(visited.contains(cur)||!checkOwner(map,dest,map.getTerritoryByName(cur).getOwner())) continue;
            if(cur.equals(dest)){
                return true;
            }
            visited.add(cur);
            if(checkMoveHelper(map.getTerritoriesMap().get(cur).stream().toList(),dest,map,visited,p)) return true;
        }
        return false;
    }


    /**
     * find the path with minimum food consume
     */
    public int miniConsume(Map map,String begin,String end,Player p,int toMove){
        return miniConsumeHelper(map,begin,end,0,new ArrayList<>(),p,toMove);
    }

    /**
     * helper function to find the path with minimum food consume
     * @param map
     * @param origin
     * @param dest
     * @param cur
     * @param visited
     * @return
     */
    public int miniConsumeHelper(Map map,String origin,String dest,int cur,List<String> visited,Player p,int toMove){
        if(origin.equals(dest)){
            Territory t=map.getTerritoryByName(dest);
            return cur;
        }
        //if(visited.size()==map.getTerritorySet().size()) return -1;
        List<String> neigh=map.getTerritoriesMap().get(origin).stream().toList();
        int count=Integer.MAX_VALUE;
        for(String str:neigh){
            if(visited.contains(str)) continue;
            if(!checkOwner(map,str,p)) {
                visited.add(str);
                continue;
            }
            if(!checkFoodFunc(map,str,p,cur,toMove).equals(GamePrompt.OK)) {
                visited.add(str);
                continue;
            }
            Territory t=map.getTerritoryByName(str);
            visited.add(str);
            int cost = miniConsumeHelper(map,str,dest,cur+t.size*toMove,visited,p,toMove);
            count = Math.min(cost,count);
            visited.remove(str);

        }
        return cur;
    }

}
