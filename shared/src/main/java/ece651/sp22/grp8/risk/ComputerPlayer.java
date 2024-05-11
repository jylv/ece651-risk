package ece651.sp22.grp8.risk;

public class ComputerPlayer extends Player {
    /**
     * constructs a HumanPlayer with the specified id
     * @param Id is the ID of the newly constructed Humanplayer
     */
    public ComputerPlayer(long Id){
        super(Id);
    }

    @Override
    public boolean equals(Object other){
        if(super.equals(other)&&other.getClass().equals(getClass())) {
            if(color==null){
                return playerID == ((Player) other).playerID  &&
                        gameID == ((Player) other).gameID &&
                        ((Player) other).color==null &&
                        techLevel == ((Player) other).getTechLevel() &&
                        foodAmount == ((Player) other).getFoodAmount() &&
                        techAmount == ((Player) other).getTechAmount();
            }
            else {
                return playerID == ((Player) other).playerID &&
                        gameID == ((Player) other).gameID &&
                        color.equals(((Player) other).color) &&
                        techLevel == ((Player) other).getTechLevel() &&
                        foodAmount == ((Player) other).getFoodAmount() &&
                        techAmount == ((Player) other).getTechAmount();
            }
        }
        return false;
    }

}
