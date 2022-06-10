import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class ViewTest {

    /*@Test
    void readStr() throws InterruptedException {
        Player A = new Player("Anson");
        InputStream sysInBackup = System.in; // backup System.in to restore it later
        ByteArrayInputStream in = new ByteArrayInputStream("string".getBytes());
        System.setIn(in);
        assertEquals(View.readStr(A), "string");
        System.setIn(sysInBackup);
    }*/

    @Test
    //test printBoard()
    void printBoard() {
        View.printBoard();
    }
}