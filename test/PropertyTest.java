import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PropertyTest {
    //test class Property

    @Test
    //test setOwner() does set the owner and decrease the player's money correctly
    void setOwner() {
        Property prop = new Property("prop",500,50);
        Player A = new Player("Anson");
        prop.setOwner(A);
        assertEquals(prop.getOwner(),A);
        assertEquals(A.getMoney(),1000);
    }

    @Test
    //test stepOn() does not let player buy the property if he has not enough money
    void stepOntest1() {
        Property prop = new Property("prop",1600,50);
        Player A = new Player("Anson");
        prop.stepOn(A);
        assertEquals(prop.getOwner(),null);
        assertEquals(A.getMoney(),1500);
    }

    @Test
    //test stepOn() does nothing if the owner step on his own property
    void stepOntest2() {
        Property prop = new Property("prop",500,50);
        Player A = new Player("Anson");
        prop.setOwner(A);
        prop.stepOn(A);
        assertEquals(prop.getOwner(),A);
        assertEquals(A.getMoney(),1000);
    }

    @Test
    //test stepOn() does reset the owner when the owner is retired
    void stepOntest3() {
        Property prop = new Property("prop",1600,50);
        Player A = new Player("Anson");
        Player B = new Player("Ben");
        prop.setOwner(B);
        prop.stepOn(A);
        assertEquals(prop.getOwner(),null);
        assertEquals(A.getMoney(),1500);
    }

    @Test
    //test payRent() does increase and decrease the money of owner and the player correctly
    void payRent() {
        Property prop = new Property("prop",500,50);
        Player A = new Player("Anson");
        Player B = new Player("Ben");
        prop.setOwner(A);
        prop.payRent(B);
        assertEquals(A.getMoney(),1050);
        assertEquals(B.getMoney(),1450);
    }

    @Test
    //test clearOwner() does set the owner to null
    void clearOwner() {
        Property prop = new Property("prop",500,50);
        Player A = new Player("Anson");
        prop.setOwner(A);
        prop.clearOwner();
        assertEquals(prop.getOwner(),null);
    }


}