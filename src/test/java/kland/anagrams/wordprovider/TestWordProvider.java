package kland.anagrams.wordprovider;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class TestWordProvider implements WordProvider {

    private Set<String> words = new HashSet<>();

    public TestWordProvider(Set<String> words) {
        this.words = words;
    }

    @Override
    public Iterator<String> wordsIterator() {
        return words.iterator();
    }
}
