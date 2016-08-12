package kland.anagrams.wordprovider;

import java.util.Iterator;

public interface WordProvider {

    /**
     * Provides iterator interface for iterating over words
     * @return word iterator
     */
    Iterator<String> wordsIterator();

}
