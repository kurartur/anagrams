package kland.anagrams;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class CharacterSetTest {

    @Test
    public void testEquals() throws Exception {
        CharacterSet characterSet1 = new CharacterSet("care");
        CharacterSet characterSet2 = new CharacterSet("acre");
        CharacterSet characterSet3 = new CharacterSet("bird");
        CharacterSet characterSet4 = new CharacterSet("ridb");
        assertTrue(characterSet1.equals(characterSet2));
        assertFalse(characterSet1.equals(characterSet3));
        assertTrue(characterSet3.equals(characterSet4));
    }

    @Test
    public void testInMapBehaviour() throws Exception {
        Map<CharacterSet, String> testMap = new HashMap<>();
        testMap.put(new CharacterSet("care"), "string");
        assertTrue(testMap.containsKey(new CharacterSet("care")));
        testMap.put(new CharacterSet("care"), "string2");
        assertEquals(1, testMap.size());
        testMap.put(new CharacterSet("acre"), "string3");
        assertEquals(1, testMap.size());
    }
}