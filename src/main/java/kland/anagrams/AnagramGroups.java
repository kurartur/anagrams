package kland.anagrams;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Wrapper used to extract actual anagrams from words that are grouped by character set
 */
public class AnagramGroups {

    private Map<CharacterSet, Set<String>> anagramGroups;

    public AnagramGroups(Map<CharacterSet, Set<String>> groups) {
        this.anagramGroups = groups
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().size() > 1)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Collection<Set<String>> values() {
        return anagramGroups.values();
    }

    public int size() {
        return anagramGroups.size();
    }

    public Set<String> anagramsFor(String word) {
        return anagramGroups.get(new CharacterSet(word));
    }
}
