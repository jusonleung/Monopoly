import java.util.Random;

/**
 * Chance is a subclass of Square that gets activated when stepped on by the player
 *
 * @author Juson Leung
 * @author Max Cheung
 * @author Raphael Lee
 * @version 2.8
 */

public class Chance extends Square {


    /** Limits the gain from stepping on a chance square by {@value} */
    public static final int GAIN_LIMIT = 200;
    /** Limits the loss from stepping on a chance square by {@value} */
    public static final int LOSS_LIMIT = 300;
    /** Defines the interval between possible gain/loss {@value}, which needs to be able to divide the limits*/
    public static final int INTERVAL = 10;

    /**
     * Constructs a <code>Chance</code> object by invoking the superclass' with specified name.
     */
     Chance(){
        super("Chance");
    }

    /**
     * Overrides <code>stepOn</code> of <code>Square</code>
     * Notifies the player's destination's effect, and
     * changes the player's money balance randomly by -$300 to +$200 with $10 intervals.
     *
     * @param player the player that steps on the chance square
     */
    @Override
    public void stepOn(Player player){
        int bound = (GAIN_LIMIT + LOSS_LIMIT) / INTERVAL + 1;
        System.out.println("You have stepped on \""+ getName() +"\", you will either gain up to $"+GAIN_LIMIT+" or lose up to $"+LOSS_LIMIT);
        Random rand = new Random();
        player.setMoney(rand.nextInt(bound)* INTERVAL - LOSS_LIMIT);
    }
}
