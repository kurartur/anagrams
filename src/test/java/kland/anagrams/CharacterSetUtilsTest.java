package kland.anagrams;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class CharacterSetUtilsTest {

    @Test
    public void testGroupByCharacterSet() throws Exception {
        Map<CharacterSet, Set<String>> anagrams = CharacterSetUtils.groupByCharacterSet(createWordList());
        assertGroups(anagrams);
    }

    @Test
    public void testGroupByCharacterSetParallel() throws Exception {
        assertGroups(CharacterSetUtils.groupByCharacterSetParallel(createWordList()));
    }

    private void assertGroups(Map<CharacterSet, Set<String>> groups) throws Exception {
        assertEquals(4, groups.size());
        Set<String> careAnagrams = groups.get(new CharacterSet("care"));
        assertEquals(2, careAnagrams.size());
        assertEquals(new HashSet<>(Arrays.asList("care", "acre")), careAnagrams);
        Set<String> catAnagrams = groups.get(new CharacterSet("act"));
        assertEquals(3, catAnagrams.size());
        assertEquals(new HashSet<>(Arrays.asList("act", "cat", "tac")), catAnagrams);
    }

    private List<String> createWordList() {
        return Arrays.asList("care", "acre", "act", "cat", "tac", "listen", "Silent");
    }

}