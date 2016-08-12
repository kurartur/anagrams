package kland.anagrams.finder;

import kland.anagrams.wordprovider.WordProvider;

import java.util.Set;
import java.util.function.Consumer;

public interface AnagramFinder {

    /**
     * Find anagrams in provider and send them to consumer
     * @param wordProvider word provider
     * @param consumer anagrams consumer. Should accept groups of anagrams
     */
    void find(WordProvider wordProvider, Consumer<Set<String>> consumer);

}
