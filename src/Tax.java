/**
 * Tax is a subclass of Square that gets activated when stepped on by the player
 *
 * @author Raphael Lee
 * @version 2.6
 */
public class Tax extends Square {

    /** The taxation rate is {@value}% */
    public static final int TAX_RATE = 10;
    /** The tax is rounded down to the {@value}s */
    public static final int ROUND_DOWN_CONST = 10;

    /**
     * Constructs a <code>Tax</code> object by invoking the superclass' with specified name.
     */
    Tax(){
        super("Income Tax");
    }

    /**
     * Overrides <code>stepOn</code> of <code>Square</code>
     * Notifies the player's destination's effect, and
     * deducts the players money balance by 10% rounded down to the tenth
     *
     * @param player This is the player that steps on the tax square
     */
    @Override
    public void stepOn(Player player){
        System.out.println("You have stepped on \""+ getName() +"\", and need to pay 10% of your money as tax");
        int tax = player.getMoney()/ TAX_RATE;
        tax = tax - (tax % ROUND_DOWN_CONST);
        player.setMoney(-tax);
    }
}
