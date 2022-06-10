import org.junit.jupiter.api.Test;


import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    //test class Controller

    @Test
    //test forward() does change the location of player correctly
    void forwardTest1() {
        Player[] players ={new Player("Anson"), new Player("Ben")} ;
        Controller controller = new Controller(players);
        controller.forward(players[0],6);
        assertEquals(players[0].getLocation(),7);
    }

    @Test
    //test forward() does change the location of player and increase player's money correctly if player across "Go"
    void forwardTest2() {
        Player[] players ={new Player("Anson"), new Player("Ben")} ;
        Controller controller = new Controller(players);
        players[0].setLocation(18);
        controller.forward(players[0],4);
        assertEquals(players[0].getLocation(),2);
        assertEquals(players[0].getMoney(),3000);
    }

    @Test
    void testforwardAndStepOnAndCountTurn(){
        Player[] players ={new Player("Anson"), new Player("Ben")} ;
        Controller controller = new Controller(players);
        controller.forwardAndStepOnAndCountTurn(players[0],5);
        controller.forwardAndStepOnAndCountTurn(players[1],5);
        assertEquals(players[0].getLocation(), 6);
        assertEquals(players[1].getLocation(), 6);
        assertEquals(controller.getRoundsCount(), 2);
    }

    @Test
    //test rollDie() does return an int in range 1 to 4
    void rollDie() {
        Player[] players ={new Player("Anson"), new Player("Ben")} ;
        Controller controller = new Controller(players);
        int result;
        for (int i = 0; i<1000; i++){
            result = controller.rollDie();
            assertEquals(result>=1 && result<=4,true);
        }
    }

    @Test
    //test checkWinCondition() does return false if nothing happen
    void checkWinConditionTest1() {
        Player[] players ={new Player("Anson"), new Player("Ben"), new Player("Cathy")} ;
        Controller controller = new Controller(players);
        assertEquals(controller.checkWinCondition(),false);
    }

    @Test
    //test checkWinCondition() does return true if there is only one player left (others are retired)
    void checkWinConditionTest2() {
        Player[] players ={new Player("Anson"), new Player("Ben"), new Player("Cathy")} ;
        Controller controller = new Controller(players);
        players[0].setMoney(-2000);
        players[1].setMoney(-2000);
        assertEquals(controller.checkWinCondition(),true);
    }

    @Test
    //test checkWinCondition() does return false if there is more than one player left
    void checkWinConditionTest3() {
        Player[] players ={new Player("Anson"), new Player("Ben"), new Player("Cathy")} ;
        Controller controller = new Controller(players);
        players[0].setMoney(-2000);
        assertEquals(controller.checkWinCondition(),false);
    }

    @Test
    //test checkWinCondition() does return false in the middle of round 100
    void checkWinConditionTest4() {
        Player[] players ={new Player("Anson"), new Player("Ben"), new Player("Cathy")} ;
        Controller controller = new Controller(players);
        controller.setRoundsCount(100);
        assertEquals(controller.checkWinCondition(),false);
    }

    @Test
    //test checkWinCondition() does return true in round 101
    void checkWinConditionTest5() {
        Player[] players ={new Player("Anson"), new Player("Ben"), new Player("Cathy")} ;
        Controller controller = new Controller(players);
        controller.setRoundsCount(101);
        assertEquals(controller.checkWinCondition(),true);
    }

    //test showWinner() and winnerArray() does show 3 winners if all 3 players have the same amount of money
    @Test
    void testwinnerArray1(){
        Player[] players ={new Player("Anson"), new Player("Ben"), new Player("Cathy")} ;
        Controller controller = new Controller(players);
        Player[] winners = controller.winnerArray();
        controller.showWinner(winners);
        assertArrayEquals(winners,players);
    }

    //test showWinner() and winnerArray() does show 1 winner if only 1 player have most amount of money
    @Test
    void testwinnerArray2(){
        Player[] players ={new Player("Anson"), new Player("Ben"), new Player("Cathy")} ;
        Controller controller = new Controller(players);
        players[1].setMoney(-100);
        players[2].setMoney(-100);
        Player[] expect = {players[0]};
        Player[] winners = controller.winnerArray();
        controller.showWinner(winners);
        assertArrayEquals(winners,expect);
    }

    //test showWinner() and winnerArray() does show 2 winners if 2 out of 4 players have the highest same amount of money
    @Test
    void testwinnerArray3(){
        Player[] players ={new Player("Anson"), new Player("Ben"), new Player("Cathy"), new Player("David")} ;
        Controller controller = new Controller(players);
        players[0].setMoney(-1600);
        players[2].setMoney(-100);
        Player[] expect = {players[1],players[3]};
        Player[] winners = controller.winnerArray();
        controller.showWinner(winners);
        assertArrayEquals(winners,expect);
    }

    //test save() and load() does work correctly
    @Test
    void testsaveload() throws IOException, ClassNotFoundException {
        Player[] players ={new Player("Anson"), new Player("Ben"), new Player("Cathy")} ;
        Controller controller = new Controller(players);
        players[0].setMoney(-1600);
        players[2].setMoney(-100);
        Square[] map = controller.getMap();
        ((Property) map[2]).setOwner(players[1]);
        controller.save(1);
        controller.load(1);
        players = controller.getPlayers();
        map = controller.getMap();
        map[2].stepOn(players[2]);
        assertEquals(players[0].isRetired(), true);
        assertEquals(players[2].getMoney(), 1310);
        assertEquals( ((Property) map[2]).getOwner(), players[1]);
        assertEquals(players[1].getMoney(),790);
    }

    //test does load() return false if file not found
    @Test
    void testErrorload() throws IOException, ClassNotFoundException {
        Player[] players ={new Player("Anson"), new Player("Ben"), new Player("Cathy")} ;
        Controller controller = new Controller(players);
        assertEquals(controller.load(7), false);
    }


}