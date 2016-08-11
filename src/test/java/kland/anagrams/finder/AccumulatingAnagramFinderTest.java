package kland.anagrams.finder;

import kland.anagrams.consumer.TestAnagramConsumer;
import kland.anagrams.wordprovider.TestWordProvider;
import org.junit.Test;

import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class AccumulatingAnagramFinderTest {

    @Test
    public void testFind() throws Exception {
        Set<String> words = new LinkedHashSet<>(Arrays.asList("acre", "care", "race", "cat", "act", "single", "another"));
        AnagramFinder finder = new AccumulatingAnagramFinder();
        TestAnagramConsumer testConsumer = new TestAnagramConsumer();
        finder.find(new TestWordProvider(words), testConsumer);
        assertEquals(2, testConsumer.getAnagrams().size());
        List<String> acreAnagrams = Arrays.asList("acre", "care", "race");
        List<String> catAnagrams = Arrays.asList("cat", "act");
        assertAnagrams(testConsumer.getAnagrams("acre"), acreAnagrams);
        assertAnagrams(testConsumer.getAnagrams("act"), catAnagrams);
    }

    private void assertAnagrams(Set<String> resultAnagrams, List<String> expectedAnagrams) throws Exception {
        assertNotNull(resultAnagrams);
        assertFalse(resultAnagrams.isEmpty());
        for (String word : expectedAnagrams) {
            assertTrue(resultAnagrams.contains(word));
        }
    }

    private File createFile(List<String> words) throws Exception {
        File file = File.createTempFile("tmp", null);
        file.deleteOnExit();
        PrintWriter writer = new PrintWriter(file);
        words.forEach(word -> writer.write(word + System.getProperty("line.separator")));
        writer.close();
        return file;
    }
}