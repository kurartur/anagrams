package kland.anagrams;

import java.util.*;

public class AnagramFinder {

    public Map<CharacterSet, Set<String>> findAnagrams(List<String> words) {
        Map<CharacterSet, Set<String>> anagrams = new HashMap<>(words.size());
        for (String word : words) {
            CharacterSet characterSet = new CharacterSet(word);
            if (!anagrams.containsKey(characterSet)) {
                anagrams.put(characterSet, new HashSet<>(Arrays.asList(word)));
            } else {
                Set<String> w = anagrams.get(characterSet);
                w.add(word);
            }
        }
        return anagrams;
    }

}
