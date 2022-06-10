import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    //test class Player

    @Test
    //test getName() does return player's name
    void getName() {
        Player A = new Player("Anson");
        assertEquals(A.getName(),"Anson");
    }

    @Test
    //test getName() does return player's amount of money
    void getMoney() {
        Player A = new Player("Anson");
        assertEquals(A.getMoney(),1500);
    }

    @Test
    //test setMoney() does increase player's money correctly with positive int argument, also the amount will not set player to retired
    void setMoneyTest1() {
        Player A = new Player("Anson");
        A.setMoney(100);
        assertEquals(A.getMoney(),1600);
        assertFalse(A.isRetired());
    }

    @Test
    //test setMoney() does decrease player's money correctly with negative int argument, also the amount will not set player to retired
    void setMoneyTest2() {
        Player A = new Player("Anson");
        A.setMoney(-100);
        assertEquals(A.getMoney(),1400);
        assertFalse(A.isRetired());
    }

    @Test
    //test setMoney() does set player to retired if the decreased amount > player's money
    void setMoneyTest3() {
        Player A = new Player("Anson");
        A.setMoney(-1600);
        assertTrue(A.isRetired());
    }

    @Test
    //test setInJail() does set player ti jail correctly
    void setInJail() {
        Player A = new Player("Anson");
        A.setInJail();
        assertTrue(A.isInJail());
        assertEquals(A.getLocation(),6);
        assertEquals(A.getInJailTurn(),0);
    }

    @Test
    //test setLocation() does set player location same as the argument
    void setLocation() {
        Player A = new Player("Anson");
        A.setLocation(15);
        assertEquals(A.getLocation(),15);
    }

    @Test
    //test setOutJail() does rest all jail stat of the player
    void setOutJail() {
        Player A = new Player("Anson");
        A.setInJail();
        A.addInJailTurn();
        A.setOutJail();
        assertFalse(A.isInJail());
        assertEquals(A.getInJailTurn(),0);
    }

    @Test
    //test addInJailTurn() does increase player turns in jail correctly
    void addInJailTurn() {
        Player A = new Player("Anson");
        A.setInJail();
        A.addInJailTurn();
        assertEquals(A.getInJailTurn(),1);
        A.addInJailTurn();
        assertEquals(A.getInJailTurn(),2);
    }
}