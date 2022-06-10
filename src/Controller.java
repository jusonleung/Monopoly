import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Controller is a class that dictates the logistics within the Monopoly game
 *
 * @author Juson Leung
 * @author Raphael Lee
 * @version 2.8
 */

public class Controller {

    /** Required amount to pay and get out of jail */
    public static final int JAIL_BAIL = 150;
    /** Maximum allowed turns in jail */
    public static final int MAX_JAIL_TURN = 3;
    /** Amount of money given to players whenever GO is crossed */
    public static final int GO_SALARY = 1500;
    /** Amount of time (ms) to pause the system */
    public static final int TIMEOUT = 500;
    /** Number of squares on the board **/
    public static final int boardSize = 20;

    private Player[] players;
    private Square[] map;
    private int roundsCount;
    private int turnplayerIndex;
    private Random rand = new Random();

    /**
     * Constructs a <code>Controller</code> object.
     *
     * @param players This is the array that store all players who will participate in the game
     * @see "Map related magic numbers (map length)"
     */
    Controller(Player[] players){
        //set number of current round to 1
        this.setRoundsCount(1);
        //create array of Squares to represent the board (first element is empty)
        this.setMap(new Square[boardSize+1]);
        //initialize the board
        initMap();
        //store all player in array
        this.setPlayers(players);
        //set index of current turn player to 0 (the first turn is players[0]'s turn)
        this.setTurnplayerIndex(0);
    }

    /**
     * Initializes the board of the monopoly game
     * The board layout / map is stored here
     * @see "Map related magic numbers galore"
     */
    private void initMap(){
        getMap()[1] = new Square("Go");
        getMap()[2] = new Property("Central",800,90);
        getMap()[3] = new Property("Wan Chai",700,65);
        getMap()[4] = new Tax();
        getMap()[5] = new Property("Stanley",600,60);
        getMap()[6] = new Square("Just visiting");
        getMap()[7] = new Property("Shek O",400,10);
        getMap()[8] = new Property("Mong Kok",500,40);
        getMap()[9] = getMap()[13] = getMap()[19] = new Chance();
        getMap()[10] = new Property("Tsing Yi",400,15);
        getMap()[11] = new Square("Free Parking");
        getMap()[12] = new Property("Shatin",700,75);
        getMap()[14] = new Property("Tuen Mun",400,20);
        getMap()[15] = new Property("Tai Po",500,25);
        getMap()[16] = new GoToJail();
        getMap()[17] = new Property("Sai Kung",400,10);
        getMap()[18] = new Property("Yuen Long",400,25);
        getMap()[20] = new Property("Tai O",600,25);
    }

    /**
     * Starts the monopoly game and runs it until the winners are determined.
     *
     * @exception IOException On input error
     * @exception InterruptedException On sleep method
     * @exception ClassNotFoundException On missing class to the class path
     * @see "Map related magic numbers (map length)"
     */
    //starting the game
    public void startingGame() throws InterruptedException, IOException, ClassNotFoundException {
        // check if there are any winners determined by the system
        // while there aren't any yet, continue with the game
        while (!checkWinCondition()){
            // sleep for 0.5 second
            TimeUnit.MILLISECONDS.sleep(TIMEOUT);
            //if the player has retired, skip his turn
            Player turnplayer = getPlayers()[getTurnplayerIndex()];
            if (turnplayer.isRetired()){
                countTurn();
                continue;
            }
            System.out.println("------------------------------------------------------------------------------------------------------------------------------");
            //print the board
            View.printBoard();
            System.out.println("Round "+ getRoundsCount() +", " +turnplayer.getName()+"'s turn");
            System.out.println("You are on square "+turnplayer.getLocation()+"-\""+ getMap()[turnplayer.getLocation()].getName()+ "\" and have $"+turnplayer.getMoney());
            //ask turn player to enter "yes" to continue the game, "save" or "load" to save/load the game
            if (!View.askYesOrSL(this,turnplayer)) //if user load game(return false), rerun the turn
                continue;
            if (turnplayer.isInJail()){
                int jailResult = inJailTurn(turnplayer);
                if (jailResult == 0) {
                    countTurn();
                    continue;
                }
                else if (jailResult >= 2){
                    forwardAndStepOnAndCountTurn(turnplayer, jailResult);
                    continue;
                }
            }
            System.out.println("First die:");
            int first = rollDie();
            TimeUnit.MILLISECONDS.sleep(TIMEOUT);
            System.out.println("Second die:");
            int second = rollDie();
            int steps = first+second;
            TimeUnit.MILLISECONDS.sleep(TIMEOUT);
            System.out.println("Therefore, you move forward by "+steps+" steps to square "+(turnplayer.getLocation() + steps > 20 ? turnplayer.getLocation() + steps - 20 : turnplayer.getLocation() + steps));
            TimeUnit.MILLISECONDS.sleep(TIMEOUT);
            forwardAndStepOnAndCountTurn(turnplayer,steps);
            TimeUnit.MILLISECONDS.sleep(TIMEOUT);
        }
        // display the winners as the game has concluded.
        showWinner(winnerArray());
        System.out.println("The game is over, thank you for playing");
        System.out.println("You can start a new game");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------");
    }

    /**
     * Shows (and congratulates) the winners in the game
     *
     * @param winners the array that stores all winners in the game
     */

    public void showWinner(Player[] winners){
        System.out.print("Congratulations to");
        for (Player winner : winners) {
            System.out.print(" " + winner.getName());
        }
        System.out.println();
        System.out.print("You are the winner");
        if (winners.length > 1)
            System.out.print("s");
        System.out.println("!!!");
    }

    /**
     * Checks the winners of the game
     * They are determined by having the most or equally most money
     *
     * @return an array that stores all winners in the game
     */

    public Player[] winnerArray(){
        ArrayList<Player> winners = new ArrayList<>();
        int max = 0;
        for (Player player : getPlayers()) {
            if (player.getMoney() > max) {
                max = player.getMoney();
                winners.clear();
                winners.add(player);
            } else if (player.getMoney() == max) {
                winners.add(player);
            }
        }
        Player[] result = new Player[winners.size()];
        winners.toArray(result);
        return result;
    }

    /**
     * Moves players according to the rolled dice steps,
     * activates the square effects, and counts their turn.
     *
     * @param player the active player who threw the dice and will move accordingly
     * @param steps the steps that the player will move in the map
     */

    public void forwardAndStepOnAndCountTurn(Player player, int steps){
        forward(player,steps);
        getMap()[player.getLocation()].stepOn(player);
        countTurn();
    }

    /**
     * Checks the players' turn in one round, and move to the second if all player have played their turn
     */
    private void countTurn(){
        setTurnplayerIndex(getTurnplayerIndex() + 1);
        if (getTurnplayerIndex() >= getPlayers().length){
            setTurnplayerIndex(getTurnplayerIndex() - getPlayers().length);
            setRoundsCount(getRoundsCount() + 1);
            System.out.println("Round "+(getRoundsCount() -1)+" is over, round "+ getRoundsCount() +" starts now");
        }
    }

    /**
     * Increments the count for a player's turns spent in jail,
     * depending on the player's money balance, offers the player to pay and get out of jail,
     * rolls the dice to see if they get a double to leave, and
     * if the player has been jailed for 3 turns, forces them to pay and get out. (This may cause player bankruptcy)
     *
     * @param player the player in jail.
     * @return  2-8 if get out of jail by rolling dice;
     *          1 if get out of jail by paying;
     *          0 if staying in jail or getting retired
     */

    public int inJailTurn(Player player){
        player.addInJailTurn();
        System.out.println(player.getName()+", you are in jail now, and have been for "+ player.getInJailTurn()+" turn(s)");
        if (player.getInJailTurn() < MAX_JAIL_TURN && player.getMoney() >= JAIL_BAIL){
            System.out.println("You have $"+player.getMoney()+", and can pay $"+JAIL_BAIL+" to get out of jail, otherwise you need to throw a double");
            if (View.askYesOrNo("Enter \"yes\" to pay the fee or \"no\" to choose not to",player)){
                player.setMoney(-JAIL_BAIL);
                player.setOutJail();
                return 1;
            }
        }
        System.out.println("You have to throw a double to get out of jail");
        System.out.print("First, ");
        int first = rollDie();
        System.out.print("Second, ");
        int second = rollDie();
        if (first == second){
            System.out.println("You have successfully thrown a double, you can now get out of jail");
            player.setOutJail();
            return first+second;
        }
        System.out.println("You did not throw a double");
        if (player.getInJailTurn()>= MAX_JAIL_TURN){
            System.out.println("Since you have been in jail for "+MAX_JAIL_TURN+" turns, you must now pay $"+JAIL_BAIL+" to get out");
            player.setMoney(-JAIL_BAIL);
            player.setOutJail();
            if (player.isRetired())
                return 0;
            else
                return first+second;
        }
        System.out.println("Unfortunately, you stay locked up.");
        return 0;
    }

    /**
     * Checks whether the player would cross "GO" and loops the player to the correct location index and gives them their salary
     *
     * @param player the player that is ready to move
     * @param step the number of steps the player should move
     * @see "Map related magic numbers (map len)"
     */
    public void forward(Player player, int step){
        int loc = player.getLocation()+step;
        if (loc > 20){
            System.out.println("You have crossed \"Go\" so you get $"+GO_SALARY+" salary");
            player.setMoney(GO_SALARY);
            loc -= 20;
        }
        player.setLocation(loc);
    }

    /**
     * Rolls 1 4-sided die in the game
     *
     * @return This returns the steps that player should move in her turn
     */
    public int rollDie(){
        int result = getRand().nextInt(4)+1;
        System.out.println("You roll a "+result);
        return result;
    }

    /**
     * Checks whether the game have a winner determined or not
     * @return true if the number of rounds exceed 100 or when only 1 player remains in the game; false otherwise
     */

    public boolean checkWinCondition(){
        if (getRoundsCount() > 100)
            return true;
        int retireCount = 0;
        for (Player player : this.getPlayers()) {
            if (player.isRetired())
                retireCount++;
        }
        return retireCount >= getPlayers().length - 1;
    }

    /**
     * Saves the ongoing game by transferring it into a file
     *
     * @param num the save slot chosen by the player for saving
     * @exception IOException On input error
     */
    public void save(int num) throws IOException {
        SaveFile save = new SaveFile(this);
        String saveName = "save_" + num + ".txt";
        FileOutputStream outputStream = new FileOutputStream(saveName);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(save);
        objectOutputStream.close();
    }

    /**
     * Loads a previous game from a specifed save file
     *
     * @param num the save slot chosen by the player for loading
     * @return true if the game is loaded; false if the save slot is empty (no such file)
     * @exception IOException On input error
     * @exception ClassNotFoundException On missing class to the class path
     */
    public boolean load(int num) throws IOException, ClassNotFoundException {
        String saveName = "save_" + num + ".txt";
        SaveFile loadedFile;
        try {
            FileInputStream inputStream = new FileInputStream(saveName);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            loadedFile = (SaveFile) objectInputStream.readObject();
        }catch (FileNotFoundException e){
            return false;
        }
        this.setPlayers(loadedFile.getPlayers());
        this.setMap(loadedFile.getMap());
        this.setTurnplayerIndex(loadedFile.getTurnplayerIndex());
        this.setRoundsCount(loadedFile.getRoundsCount());
        return true;
    }

    /** @return the array of all players who are participating in this game */
    public Player[] getPlayers(){
        return this.players;
    }

    /** @param players Array of all participating players */
    public void setPlayers(Player[] players) {
        this.players = players;
    }

    /** @return the number of rounds that have elapsed */
    public int getRoundsCount() {
        return roundsCount;
    }

    /** @param roundsCount number of rounds elapsed */
    public void setRoundsCount(int roundsCount) {
        this.roundsCount = roundsCount;
    }

    /** @return the ongoing round's player index who is supposed to play */
    public int getTurnplayerIndex() {
        return turnplayerIndex;
    }

    /** @param turnplayerIndex the current-to-play player's index */
    public void setTurnplayerIndex(int turnplayerIndex) {
        this.turnplayerIndex = turnplayerIndex;
    }

    /** @return the map in this game */
    public Square[] getMap(){
        return map;
    }

    /** @param map the board layout */
    public void setMap(Square[] map) {
        this.map = map;
    }

    /** @return the random number generator */
    public Random getRand() {
        return rand;
    }

    /** @param rand a RNG to substitute the original one? */
    public void setRand(Random rand) {
        this.rand = rand;
    }
}



