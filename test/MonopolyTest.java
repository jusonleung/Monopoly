import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MonopolyTest {

    @Test
    //test isRepeatName() does return false if all player's name are not repeated with the input String
    void isRepeatNametest1() {
        Player[] players ={new Player("Anson"), new Player("Ben")} ;
        assertEquals(Monopoly.isRepeatName("A", players), false);
    }

    @Test
    //test isRepeatName() does return false if all player's name are not repeated with the input String and players have null inside
    void isRepeatNametest2() {
        Player[] players ={new Player("Anson"), new Player("Ben"), null} ;
        assertEquals(Monopoly.isRepeatName("A", players), false);
    }

    @Test
        //test isRepeatName() does return true if name repeated
    void isRepeatNametest3() {
        Player[] players ={new Player("Anson"), new Player("Ben"), null} ;
        assertEquals(Monopoly.isRepeatName("Ben", players), true);
    }
}