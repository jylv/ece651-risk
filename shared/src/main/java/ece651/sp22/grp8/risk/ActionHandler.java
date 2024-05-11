package ece651.sp22.grp8.risk;

import org.checkerframework.checker.units.qual.A;
import java.util.Comparator;

import java.util.*;

public class ActionHandler {
    public HashMap<Integer,Integer> bonusMap; //<unit level,bonus>
    public HashMap<Integer,Integer> costMap; //<unit level,total tech cost>
    public HashMap<Integer,Integer> upgradeCost; //<curr tech level, tech cost>
    public MoveChecker moveChecker;
    public AttackChecker attackChecker;
    public TechUpgradeChecker techUpgradeChecker;
    public UnitUpgradeChecker unitUpgradeChecker;


    public ActionHandler(){
        setBonus();
        setCost();
        setUpgradeCost();
        this.moveChecker = new MoveChecker(null);
        this.attackChecker = new AttackChecker(null);
        this.unitUpgradeChecker = new UnitUpgradeChecker(null);
        this.techUpgradeChecker = new TechUpgradeChecker(null);
    }

    public void setBonus(){
        this.bonusMap = new HashMap<>();
        bonusMap.put(0,0);
        bonusMap.put(1,1);
        bonusMap.put(2,3);
        bonusMap.put(3,5);
        bonusMap.put(4,8);
        bonusMap.put(5,11);
        bonusMap.put(6,15);
    }

    public void setCost(){
        this.costMap = new HashMap<>();
        costMap.put(0,0);
        costMap.put(1,3);
        costMap.put(2,11);
        costMap.put(3,30);
        costMap.put(4,55);
        costMap.put(5,90);
        costMap.put(6,140);
    }

    public void setUpgradeCost(){
        this.upgradeCost = new HashMap<>();
        upgradeCost.put(1,50);
        upgradeCost.put(2,75);
        upgradeCost.put(3,125);
        upgradeCost.put(4,200);
        upgradeCost.put(5,300);
    }


    //for move
    /**
     * This method deal with a move request
     * @param move is a Move request
     */
    public HashMap<String,Integer> dealWithMove(Map map, Player player, Move move) {
        HashMap<String,Integer> moveResult = new HashMap<>();
        String moveProblem = moveChecker.checkMove(map,move.getOriTerri(),move.getDestTerri(),player,move.getLevel(),move.getMoveUnits());
        if(moveProblem.equals(GamePrompt.OK)){ //valid request
            Territory origin=map.getTerritoryByName(move.getOriTerri());
            Territory dest=map.getTerritoryByName(move.getDestTerri());
            origin.decreaseUnit(move.getLevel(),move.getMoveUnits());
            dest.increaseUnit(move.getLevel(),move.getMoveUnits());
            int cost=moveChecker.miniConsume(map,move.getOriTerri(),move.getDestTerri(),player,move.getMoveUnits());
            player.decFood(cost);
            moveResult.put(moveProblem,cost);
        }
        else {
            moveResult.put(moveProblem, -1);
        }
        return moveResult;
    }


    // for attack
    /**
     * add attackInfo
     * @param playerID
     * @param units
     */
    public void addAttack(Territory t, long playerID, int level, int units){
        //if the player has attacked this territory, add unit
        if(t.attackInfo.containsKey(playerID)){
            // if unit of this level has attacked this territory, add unit
            if(t.attackInfo.get(playerID).containsKey(level)){
                int oldUnit = t.attackInfo.get(playerID).get(level);
                t.attackInfo.get(playerID).replace(level,oldUnit,oldUnit+units);
            }
            //else, put attack info
            else {
                t.attackInfo.get(playerID).put(level,units);
            }
        }
        //else, put attack info
        else{
            HashMap<Integer,Integer> newAttack = new HashMap<>();
            newAttack.put(level,units);
            t.attackInfo.put(playerID,newAttack);
        }
    }

    /**
     * update player attack territory info
     * delete the unit level if the corresponding num is 0
     */
    public void updateAttack(Territory t, long playerID){
        //delete the unit level if the corresponding num is 0
        HashMap<Integer,Integer> info = t.attackInfo.get(playerID);
        HashSet<Integer> del = new HashSet<>();
        for(int level: info.keySet()){
            if(info.get(level)==0){
                del.add(level);
            }
        }
        for(int l: del){
            info.remove(l);
        }
    }

    /**
     * get the attack unit level
     * @param t the attacked territory
     * @param playerID attacker
     * @param low true-get lowest level/ false-get highest level
     */
    public int getAttackLevel(Territory t, long playerID, boolean low){
        Set<Integer> levelSet = t.attackInfo.get(playerID).keySet();
        ArrayList<Integer> levelList = new ArrayList<>(levelSet);
        levelList.sort(Comparator.naturalOrder());
        if(low){
            return levelList.get(0);
        }
        else{
            return levelList.get(levelList.size()-1);
        }
    }


    /**
     * handle attack info when all player commits
     */
    public void doAttack(Territory t, HashMap<Long, Player> players) {
        if(t.attackInfo.size()!=0){
            // for each of the attacker
            for(Long id: t.attackInfo.keySet()){
                //combat until one win
                int order = 1;  //odd order: A-high vs D-low   even order: A-low vs D-high
                while(t.getUnitsSum()!=0 && t.attackInfo.get(id).size()!=0){
                    //roll dice
                    int dice1 = rollDice(); //defender
                    int dice2 = rollDice(); //attacker
                    //odd order: A-high vs D-low
                    //even order: A-low vs D-high
                    dice1 += bonusMap.get(t.getLevel(order%2 == 1));
                    dice2 += bonusMap.get(getAttackLevel(t,id,order%2 != 1));


                    //handle dice result
                    if(dice1>=dice2){
                        //defender success
                        int level = getAttackLevel(t,id,order%2 != 1);
                        int oldUnit = t.attackInfo.get(id).get(level);
                        t.attackInfo.get(id).replace(level,oldUnit,oldUnit-1);
                    }
                    else{
                        //attacker success
                        int level = t.getLevel(order%2 == 1);
                        int oldUnit = t.getUnitMap().get(level);
                        t.getUnitMap().replace(level,oldUnit,oldUnit-1);
                    }

                    // update order and attackInfo
                    order++;
                    updateAttack(t,id);
                }

                //if attacker win
                if(t.getUnitsSum()==0){
                    //update owner
                    t.setOwner(players.get(id));
                    //update units
                    t.updateUnitMap(t.attackInfo.get(id));
                }
            }
            //clear attackInfo for this turn
            t.attackInfo.clear();
        }

    }

    public int rollDice(){
        return (new Random().nextInt(20)) + 1;
    }

    /**
     * handle attack info when all player commits
     */
    public void handleAttack(Map map, HashMap<Long, Player> players) {
        for(Territory t:map.getTerritorySet()){
            long oldOwner  = t.getOwner().getPlayerID();
            doAttack(t,players);
            long newOwner = t.getOwner().getPlayerID();
            if(newOwner != oldOwner){
                map.attackResult.put(t.getName(),oldOwner);
            }
        }
        updateGroups(map);
    }

    /**
     * This method deal with a attack request
     * @param attack is a Attack request
     */
    public String dealWithAttack(Map map, Player player,Attack attack) {
        String attackProblem = attackChecker.checkAttack(map,attack.getOriginTerritory(),attack.getDestTerritory(),player,attack.getLevel(),attack.getAttackUnits());
        if(attackProblem.equals(GamePrompt.OK)){ //valid request
            Territory origin=map.getTerritoryByName(attack.getOriginTerritory());
            Territory dest=map.getTerritoryByName(attack.getDestTerritory());
            origin.decreaseUnit(attack.getLevel(),attack.getAttackUnits());
            addAttack(dest,player.getPlayerID(),attack.getLevel(),attack.getAttackUnits());
            player.decFood(attack.getAttackUnits());
        }
        return attackProblem;
    }

    public void updateGroups(Map map){
        //clear the old info
        for (String c:map.getGroups().keySet()){
            map.getGroups().get(c).clear();
        }
        //update new info
        for(Territory t:map.getTerritorySet()){
            map.getGroups().get(t.getOwner().getColor()).add(t);
        }
    }


    //for update at the end of each turn
    /**
     * add one basic unit in each territory as the end of each turn
     */
    public void updateOneTurn(Map map){
        for(Territory t: map.getTerritorySet()){
            t.increaseUnit(0,1);
        }
    }

    /**
     * update the resource of player at the end of the turn
     */
    public void updateResource(Map map, HashMap<Long, Player> players){
        for(long id: players.keySet()){
            for(Territory t:map.getTerritoriesByOwnerId(id)){
                players.get(id).addTech(t.techProd);
                players.get(id).addFood(t.foodProd);
            }
        }
    }

    /**
     * upgrade the tech level of players if orders are valid
     */
    public void updateLevel(HashMap<Long, Player> players){
        for(long id:players.keySet()){
            if(players.get(id).isHasUpgrade()){
                //upgrade tech level
                players.get(id).addTechLevel();
                //clear the upgrade info of this turn
                players.get(id).setHasUpgrade(false);
            }
        }
    }



    //for upgrade
    /**
     * upgrade the unit
     */
    public int upUnitCost(int oriLevel,int num, int destLevel){
        return num*(costMap.get(destLevel)-costMap.get(oriLevel));
    }

    public String dealWithUpUnit(Map map, Player player, UpUnit uu){
        String uuProblem = unitUpgradeChecker.checkUpgradeUnit(map,player,uu.getTerritory(),uu.getOriLevel(),uu.getNum(),uu.getDestLevel(),costMap);
        //valid request
        if(uuProblem.equals(GamePrompt.OK)){
            Territory t = map.getTerritoryByName(uu.getTerritory());
            t.decreaseUnit(uu.getOriLevel(),uu.getNum());
            t.increaseUnit(uu.getDestLevel(),uu.getNum());
            player.decTech(upUnitCost(uu.getOriLevel(),uu.getNum(),uu.getDestLevel()));
        }
        return uuProblem;
    }

    public String dealWithUpTech(Player player, UpTech ut){
        String utProblem = techUpgradeChecker.techLevelChecker(player,upgradeCost);
        if(utProblem.equals(GamePrompt.OK)){
            player.decTech(upgradeCost.get(player.getTechLevel()));
            player.setHasUpgrade(true);
        }
        return utProblem;
    }

}
