import java.io.IOException;

/**
 * The Monopoly class initializes the Controller of a new game
 *
 * @author Juson Leung
 * @author Raphael Lee
 * @version 2.8
 */

public class Monopoly {

    /** The minimum allowed players in a game */
    public static final int MIN_PLAYERS = 2;
    /** The maximum allowed players in a game */
    public static final int MAX_PLAYERS = 6;
    /** The smallest indexed save slot offered */
    public static final int MIN_SAVE_SLOT = 1;
    /** The largest indexed save slot offered */
    public static final int MAX_SAVE_SLOT = 6;

    /**
     * Checks whether a player name preexists
     *
     * @param name the inputted name to be checked for duplicates
     * @param players the array of existing players
     * @return true if repeated; false otherwise
     */
    public static boolean isRepeatName(String name, Player[] players) {
        for (Player player : players) {
            if (player == null)
                return false;
            if (name.equals(player.getName())) {
                System.out.println("Repeated name, please enter again");
                return true;
            }
        }
        return false;
    }

    /**
     * Initializes the game or loads an existing save file,
     * calls to construct the game Controller and Players if a new game is made.
     *
     * @param args Unused.
     * @exception IOException On input error.
     * @exception InterruptedException On sleep method
     * @exception ClassNotFoundException On missing class to the class path
     */
    public static void main(String[] args) throws InterruptedException, IOException, ClassNotFoundException {
        System.out.println("Welcome to Monopoly");
        while (true){
            System.out.print("Please enter \"start\" to start a new game or \"load\" to load a saved game: ");
            String input = View.readStr();
            if (input.equals("start")){
                int num = View.readInt("Please enter the number of players (2-6) to start the game: ", MIN_PLAYERS, MAX_PLAYERS);
                Player[] players = new Player[num];
                for (int i = 0; i < num; i++){
                    do {
                        System.out.print("Player "+ (i+1) + " please enter your name: ");
                        input = View.readStr();
                    }while (isRepeatName(input,players));
                    players[i] = new Player(input);
                }
                Controller controller = new Controller(players);
                controller.startingGame();
            }else if (input.equals("load")){
                Controller controller = new Controller(null);
                int num = View.readInt("Which slot of save do you want to load? (1-6): ", MIN_SAVE_SLOT, MAX_SAVE_SLOT);
                if (controller.load(num))
                    controller.startingGame();
                else
                    System.out.println("Save slot "+num+" is empty");
            }else {
                System.out.println("Wrong input, please enter again");
            }
        }
    }
}
