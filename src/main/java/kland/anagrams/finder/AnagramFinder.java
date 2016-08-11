package kland.anagrams.finder;

import kland.anagrams.wordprovider.WordProvider;

import java.util.Set;
import java.util.function.Consumer;

public interface AnagramFinder {

    void find(WordProvider wordProvider, Consumer<Set<String>> consumer);

}
