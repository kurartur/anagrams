package kland.anagrams;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class AnagramFinderTest {

    private AnagramFinder anagramFinder = new AnagramFinder();

    @Test
    public void testFindAnagrams() throws Exception {
        List<String> words = Arrays.asList("care", "acre", "act", "cat", "tac");
        Map<CharacterSet, Set<String>> anagrams = anagramFinder.findAnagrams(words);
        assertEquals(2, anagrams.size());
        Set<String> careAnagrams = anagrams.get(new CharacterSet("care"));
        assertEquals(2, careAnagrams.size());
        assertEquals(new HashSet<>(Arrays.asList("care", "acre")), careAnagrams);
        Set<String> catAnagrams = anagrams.get(new CharacterSet("act"));
        assertEquals(3, catAnagrams.size());
        assertEquals(new HashSet<>(Arrays.asList("act", "cat", "tac")), catAnagrams);
    }
}