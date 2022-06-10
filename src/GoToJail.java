/**
 * GoToJail is a subclass of Square that gets activated when stepped on by the player
 *
 * @author Juson Leung
 * @author Maisy Wong
 * @version 2.6
 */

public class GoToJail extends Square{

    /**
     * Constructs a <code>GoToJail</code> object by invoking the superclass' with specified name.
     */
    GoToJail (){
        super("GoToJail");
    }

    /**
     * Overrides <code>stepOn</code> of <code>Square</code>
     * Notifies the player's destination's effect, and jails them
     *
     * @param player This is the player that steps on the Jail square
     */
    @Override
    public void stepOn(Player player){
        System.out.println("You have stepped on \""+ getName() +"\", you will be sent to jail now");
        player.setInJail();
    }
}
