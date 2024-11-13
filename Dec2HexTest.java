import org.junit.Test;
import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class Dec2HexTest {
    
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    
    @Test
    public void testValidInput() {
        System.setOut(new PrintStream(outContent));
        Dec2Hex.main(new String[]{"15"});
        assertTrue(outContent.toString().contains("Hexadecimal representation is: F"));
        System.setOut(originalOut);
    }
    
    @Test
    public void testLargeNumber() {
        System.setOut(new PrintStream(outContent));
        Dec2Hex.main(new String[]{"255"});
        assertTrue(outContent.toString().contains("Hexadecimal representation is: FF"));
        System.setOut(originalOut);
    }
}
