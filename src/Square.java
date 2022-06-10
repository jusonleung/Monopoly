import java.io.Serializable;

/**
 * The Square class defines a tile on the Monopoly board
 * It is usually extended by other classes, but in the case where no effects are needed, this can be used instead.
 *
 * @author Juson Leung
 * @author Raphael Lee
 * @version 2.8
 */

public class Square implements Serializable {

    private String name;

    /**
     * Constructs a <code>Chance</code> object with specified name.
     *
     * @param name the name of a square for human identification
     */
    Square(String name){
        this.setName(name);
    }

    /**
     * Notifies the player's destination effect, which is nothing.
     *
     * @param player the player that steps on the square
     */
    public void stepOn(Player player){
        System.out.println("You step on \""+ this.getName() +"\", nothing happened");
    }

    /** @return the given name of the square */
    public String getName() {
        return name;
    }

    /** @param name of the Square to be set */
    public void setName(String name) {
        this.name = name;
    }
}