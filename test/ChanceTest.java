import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChanceTest {
    //test class Chance

    @Test
    //test stepOn() does increase or decrease player's money in range -300 to 200
    void stepOn() {
        Player A = new Player("Anson");
        Chance chance = new Chance();
        for (int i = 0; i<1000; i++){
            chance.stepOn(A);
            assertTrue(A.getMoney() >= 1200);
            assertTrue(A.getMoney() <= 1700);
            A.setMoney(Player.STARTING_MONEY -A.getMoney());
        }
    }
}