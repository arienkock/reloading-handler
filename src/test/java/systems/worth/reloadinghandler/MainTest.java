package systems.worth.reloadinghandler;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class MainTest {
    @Test
    public void testParseArgsOne() {
        String[] args = {"--hey", "a", "b"};
        List<String> hey = Main.parseArg(args, "hey", 1);
        assertEquals(1, hey.size());
        assertEquals("a", hey.get(0));
    }

    @Test
    public void testParseArgsMany() {
        String[] args = {"--hey", "a", "b"};
        List<String> hey = Main.parseArg(args, "hey", 1000);
        assertEquals(2, hey.size());
        assertEquals("a", hey.get(0));
        assertEquals("b", hey.get(1));
    }

    @Test
    public void testParseArgsMissing() {
        String[] args = {"--hey", "--a", "b"};
        List<String> hey = Main.parseArg(args, "hey", 1000);
        assertEquals(0, hey.size());
    }
}