import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GoToJailTest {
    //test class GoToJail

    @Test
    //test stepOn() does set player's location to 6 ("In Jail") and player.isInJail to true
    void stepOn() {
        Player A = new Player("Anson");
        GoToJail goToJail = new GoToJail();
        goToJail.stepOn(A);
        assertEquals(A.isInJail(),true);
        assertEquals(A.getLocation(),6);
    }
}