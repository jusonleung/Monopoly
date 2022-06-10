import java.io.Serializable;

/**
 * The SaveFile class defines the saving elements of the monopoly game
 *
 * @author Max Cheung
 * @author Raphael Lee
 * @version 2.8
 */

public class SaveFile implements Serializable {

    private Player[] players;
    private Square[] map;
    private int turnplayerIndex;
    private int roundsCount;

    /**
     * Constructs a <code>SaveFile</code> object, which fetches all <Code>Controller</Code> fields except the random number generator object
     *
     * @param controller the controller that manipulates the ongoing game
     */
    SaveFile(Controller controller){
        this.setPlayers(controller.getPlayers());
        this.setTurnplayerIndex(controller.getTurnplayerIndex());
        this.setRoundsCount(controller.getRoundsCount());
        setMap(controller.getMap());
    }

    /** @return the array of all players who are participating in this game */
    public Player[] getPlayers(){
        return players;
    }

    /** @param players Array of all participating players */
    public void setPlayers(Player[] players) {
        this.players = players;
    }
    /** @return the map in this game */
    public Square[] getMap() {
        return map;
    }

    /** @param map the board layout */
    public void setMap(Square[] map) {
        this.map = map;
    }
    /** @return the ongoing round's player index who is supposed to play */
    public int getTurnplayerIndex() {
        return turnplayerIndex;
    }

    /** @param turnplayerIndex the index of player to play */
    public void setTurnplayerIndex(int turnplayerIndex) {
        this.turnplayerIndex = turnplayerIndex;
    }
    /** @return the number of rounds that have elapsed */
    public int getRoundsCount() {
        return roundsCount;
    }

    /** @param roundsCount the number of rounds that have elapsed */
    public void setRoundsCount(int roundsCount) {
        this.roundsCount = roundsCount;
    }
}
