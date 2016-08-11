package kland.anagrams;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Consumer;

import static org.junit.Assert.*;

public class ConsoleAnagramConsumerTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUp() throws Exception {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void tearDown() {
        System.setOut(null);
    }

    @Test
    public void testAccept() throws Exception {
        Set<String> anagrams1 = new LinkedHashSet<>(Arrays.asList("acre", "care", "race"));
        Set<String> anagrams2 = new LinkedHashSet<>(Arrays.asList("cat", "act"));
        Consumer<Set<String>> consumer = new ConsoleAnagramConsumer();
        consumer.accept(anagrams1);
        consumer.accept(anagrams2);
        assertEquals("acre care race\ncat act\n", outContent.toString());
    }
}