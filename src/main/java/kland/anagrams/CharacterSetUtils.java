package kland.anagrams;

import java.util.*;
import java.util.stream.Collectors;

public class CharacterSetUtils {

    /**
     * Groups words by their character sets
     * @param words list of words
     * @return map of grouped words
     */
    public static Map<CharacterSet, Set<String>> groupByCharacterSet(final List<String> words) {
        Map<CharacterSet, Set<String>> groups = new HashMap<>(words.size());
        for (String word : words) {
            CharacterSet characterSet = new CharacterSet(word);
            if (!groups.containsKey(characterSet)) {
                groups.put(characterSet, new HashSet<>(Arrays.asList(word)));
            } else {
                Set<String> w = groups.get(characterSet);
                w.add(word);
            }
        }
        return new HashMap<>(groups);
    }

    /**
     * Groups words by their character sets using parallel streaming
     * @param words list of words
     * @return map of grouped words
     */
    public static Map<CharacterSet, Set<String>> groupByCharacterSetParallel(final List<String> words) {
        return words.parallelStream()
                .flatMap(s -> groupByCharacterSet(words).entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (Set<String> words1, Set<String> words2) -> {
                    HashSet<String> merged = new HashSet<>();
                    merged.addAll(words1);
                    merged.addAll(words2);
                    return merged;
                }));
    }

}
