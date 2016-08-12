package kland.anagrams.finder;

import kland.anagrams.CharacterSet;
import kland.anagrams.wordprovider.WordProvider;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Iterates over words grouping them by charset
 * After words are grouped, filters groups that have only one word
 * Iterates words only one time. Fast for large amount of words, but might use a lot of memory for storing groups
 */
public class AccumulatingAnagramFinder implements AnagramFinder {

    Map<CharacterSet, Set<String>> wordGroups = new HashMap<>();

    @Override
    public void find(WordProvider wordProvider, Consumer<Set<String>> consumer) {
        Iterator<String> wordIterator = wordProvider.wordsIterator();
        String word;
        while (wordIterator.hasNext()) {
            word = wordIterator.next();
            CharacterSet wordCharset = new CharacterSet(word);
            if (wordGroups.containsKey(wordCharset)) {
                Set<String> words = wordGroups.get(wordCharset);
                words.add(word);
            } else {
                wordGroups.put(wordCharset, new HashSet<>(Arrays.asList(word)));
            }
        }
        wordGroups.entrySet().stream()
                .filter(entry -> entry.getValue().size() > 1)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
                .values().forEach(consumer);
    }
}
