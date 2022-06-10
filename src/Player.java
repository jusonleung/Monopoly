import java.io.Serializable;

/**
 * The Player class defines the player elements of the monopoly game
 *
 * @author Juson Leung
 * @author Max Cheung
 * @author Maisy Wong
 * @author Raphael Lee
 * @version 2.8
 */
public class Player implements Serializable {
    /** The starting money amount each player has */
    public static final int STARTING_MONEY = 1500;

    private String name;
    private int money;
    private int location;
    private boolean isRetired;
    private boolean isInJail;
    private int inJailTurn;

    /**
     * Constructs a <code>Player</code> object.
     * The money balance, location index, jailed status and turns can be updated directly by provided methods, while
     * the retired status is controlled by <code>setMoney</code>
     *
     * @param name the name of the player
     */
    public Player(String name){
        this.setName(name);
        this.setMoney(STARTING_MONEY);
        this.setLocation(1);
        this.setRetired(false);
        this.setInJail(false);
        this.setInJailTurn(0);
    }

    /** @return the name of this player */
    public String getName() {return name;}

    /** @param name of this player */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return <code>money</code> the money balance of this player
     */
    public int getMoney() {return money;}

    /**
     * This method changes the money balance of the player, and
     * retires the player if there is insufficient balance to be deducted
     *
     * @param amount the amount of money to be added/deducted from the player
     */
    public void setMoney(int amount){
        if (amount > 0){
            System.out.print(getName() +" gains $"+amount);
        }else {
            System.out.print(getName() +" loses $"+Math.abs(amount));
        }
        money += amount;
        System.out.println(", and you now have $"+this.getMoney());
        if (this.getMoney() < 0 ){
            System.out.println(this.getName()+", you are now broke and have been retired from the game");
            this.setRetired(true);
        }
    }

    /** @return the status of if the player is in jail or not */
    public boolean isInJail() {
        return isInJail;
    }

    /**
     * Moves this player to jail, updates their status accordingly, and announces it.
     *
     * @see "Map related magic numbers (Square specific)"
     */
    public void setInJail(){
        setInJail(true);
        setInJailTurn(0);
        setLocation(6);
        System.out.println(getName() +" is now in jail");
    }

    /**
     * Overloading method to override jail status without in-game effects
     *
     * @param inJail status of player
     */
    public void setInJail(boolean inJail) {
        isInJail = inJail;
    }

    /**
     * Removes the player's jailed status, and announces it.
     */
    public void setOutJail(){
        setInJail(false);
        setInJailTurn(0);
        System.out.println(getName() +" has been released from jail");
    }

    /**
     * @return the status of if the player has been retired or not
     */
    public boolean isRetired() {
        return isRetired;
    }

    /** @param retired state of this player */
    public void setRetired(boolean retired) {
        isRetired = retired;
    }

    /** @return <code>location</code> the location index of this player */
    public int getLocation(){
        return location;
    }

    /**
     * Moves the player to a specified location
     *
     * @param location the destination index of the player
     */
    public void setLocation(int location){
        this.location = location;
    }

    /** @return the number of turns that this player have stayed in jail */
    public int getInJailTurn(){
        return inJailTurn;
    }

    /** Increments the turn count that this player have stayed in jail */
    public void addInJailTurn(){
        setInJailTurn(getInJailTurn() + 1);
    }

    /**
     * Sets jailed turns to arbitruary specified amount
     *
     * @param inJailTurn the turns spent in jail if this player is currently in one
     */
    public void setInJailTurn(int inJailTurn) {
        this.inJailTurn = inJailTurn;
    }
}
