package kland.anagrams.wordprovider;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import static org.junit.Assert.*;

public abstract class BaseFileWordProviderTest {

    protected File testFile;

    @Before
    public void setUp() throws Exception {
        testFile = File.createTempFile("test", null);
        testFile.deleteOnExit();
        PrintWriter writer = new PrintWriter(testFile);
        Arrays.asList("acre", "care", "race").forEach(word -> writer.write(word + System.getProperty("line.separator")));
        writer.close();
    }

    @Test
    public void testWordsIterator() throws Exception {
        Iterator<String> wordsIterator = getWordProvider().wordsIterator();
        Set<String> words = new HashSet<>();
        while (wordsIterator.hasNext()) {
            words.add(wordsIterator.next());
        }
        assertEquals(3, words.size());
        assertTrue(words.contains("acre"));
        assertTrue(words.contains("care"));
        assertTrue(words.contains("race"));
    }

    abstract WordProvider getWordProvider() throws IOException;
}
