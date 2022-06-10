import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaxTest {
    //test class Tax

    @Test
    //test stepOn() does decrease player money correctly
    void stepOnTest1() {
        Player A = new Player("Anson");
        Tax tax = new Tax();
        tax.stepOn(A);
        assertEquals(A.getMoney(),1350);
    }

    @Test
    //test stepOn() does decrease player money correctly (with round down to multiple of 10 needed)
    void stepOnTest2() {
        Player A = new Player("Anson");
        Tax tax = new Tax();
        A.setMoney(-170);
        tax.stepOn(A);
        assertEquals(A.getMoney(),1200);
    }
}