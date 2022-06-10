import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * View is a class that defines viewing elements of the Monopoly game
 *
 * @author Juson Leung
 * @author Max Cheung
 * @author Raphael Lee
 * @version 2.8
 */
public class View {

    private static Scanner scan = new Scanner(System.in);

    /**
     * Directs and asks the choice of a player
     *
     * @param print the question to be asked
     * @param player the player that to receive the question
     * @return true if player affirms; false otherwise
     */
    public static boolean askYesOrNo(String print, Player player){
        String input;
        while (true){
            System.out.println(print);
            input = readStr(player);
            if (input.equals("yes") || input.equals("no")){
                break;
            }
            System.out.println("Invalid input, please enter again");
        }
        return input.equals("yes");
    }

    /**
     * Asks and checks whether the player would like to
     * continue their turn, save, or load a game
     *
     * @param controller the game logic controller
     * @param player the active player that is to be asked if they would like to continue the game, or save/load
     * @return true if player continues or saves the game; false if the player chooses to load a game and it exists
     * @exception IOException On input error
     * @exception ClassNotFoundException On missing class to the class path
     */

    public static boolean askYesOrSL(Controller controller,Player player) throws IOException, ClassNotFoundException { //return false if player load a game
        String input;
        while (true){
            System.out.println("Please enter \"yes\" to start your turn or enter \"save\"/\"load\" to save/load the game");
            input = View.readStr(player);
            if (input.equals("yes")) {
                break;
            }
            else if (input.equals("save") || input.equals("load")){
                if (input.equals("save")){
                    int num = View.readInt("Which save slot do you want to save the game? (1-6): ",1,6);
                    controller.save(num);
                }
                else{
                    int num = View.readInt("Which slot of save do you want to load? (1-6): ",1,6);
                    if (!controller.load(num)){
                        System.out.println("Save slot "+num+" is empty");
                    }else
                        return false;
                }
            }
            else{
                System.out.println("Invalid input, please enter again");
            }
        }
        return true;
    }

    /**
     * Specifies a player, receives and passes through an inputted token
     *
     * @param player the target player
     * @return the inputted token
     */
    public static String readStr(Player player){
        System.out.print(player.getName()+" > ");
        return getScan().next();
    }

    /**
     * Receives and passes through an inputted token
     *
     * @return the inputted token
     */
    public static String readStr(){
        return getScan().next();
    }

    /**
     * Receives, checks, and passes through an inputted integer
     *
     * @param print the system prompt to the user/player to enter an integer
     * @param floor the lower bound of the integer
     * @param ceil  the upper bound of the integer
     * @return      the accepted integer
     */
    public static int readInt(String print,int floor, int ceil){
        int number;
        while (true) {
            try {
                System.out.print(print);
                number = getScan().nextInt();
                if( number >= floor && number <= ceil){
                    break;
                }
                System.out.println("Out of range.");

            } catch (InputMismatchException e) {
                System.out.println("You did not enter an integer");
                getScan().nextLine();
            }
        }
        return number;
    }

    /**
     * Prints out the board of the monopoly game
     */
    public static void printBoard(){
        System.out.println("-------------------------------------------------------------------------------------------------");
        System.out.println("|     Free      |     Shatin    |       ?       |    Tuen Mun   |    Tai po     |   Go to jail  |");
        System.out.println("|    Parking    |      $700     |     Chance    |      $400     |      $500     |               |");
        System.out.println("|      11       |       12      |       13      |       14      |       15      |       16      |");
        System.out.println("-------------------------------------------------------------------------------------------------");
        System.out.println("|   Tsing Yi    |                                                               |    Sai Kung   |");
        System.out.println("|     $400      |                                                               |      $400     |");
        System.out.println("|      10       |                                                               |       17      |");
        System.out.println("|----------------                                                               ----------------|");
        System.out.println("|       ?       |                                                               |   Yuen Long   |");
        System.out.println("|     Chance    |                                                               |      $400     |");
        System.out.println("|       9       |                                                               |       18      |");
        System.out.println("|----------------                                                               ----------------|");
        System.out.println("|   Mong Kok    |                                                               |       ?       |");
        System.out.println("|     $500      |                                                               |     Chance    |");
        System.out.println("|       8       |                                                               |       19      |");
        System.out.println("|----------------                                                               ----------------|");
        System.out.println("|    Shek O     |                                                               |     Tai O     |");
        System.out.println("|     $400      |                                                               |      $600     |");
        System.out.println("|       7       |                                                               |       20      |");
        System.out.println("-------------------------------------------------------------------------------------------------");
        System.out.println("|    In jail    |    Stanley    |   Income Tax  |    Wan Chai   |    Central    |       GO      |");
        System.out.println("| Just visiting |      $600     |    Pay 10%    |      $700     |      $800     |    <-------   |");
        System.out.println("|       6       |       5       |       4       |       3       |       2       |       1       |");
        System.out.println("-------------------------------------------------------------------------------------------------");
    }

    /** @return Scanner object to accept user input in the CLI */
    public static Scanner getScan() {
        return scan;
    }

    /** @param scan a scanner to substitute current one? */
    public static void setScan(Scanner scan) {
        View.scan = scan;
    }
}
