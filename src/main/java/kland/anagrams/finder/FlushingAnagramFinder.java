package kland.anagrams.finder;

import kland.anagrams.CharacterSet;
import kland.anagrams.wordprovider.WordProvider;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Takes one not checked charset and iterates over words searching for words with same charset
 * After anagrams for one particular charset was found, sends them to consumer
 * Iterates words multiple times, might be slow for large amount of words but does not use a lot of memory
 */
public class FlushingAnagramFinder implements AnagramFinder {

    Set<CharacterSet> usedCharsets = new HashSet<>();

    @Override
    public void find(WordProvider wordProvider, Consumer<Set<String>> consumer) {
        Iterator<String> subjectIterator = wordProvider.wordsIterator();
        while (subjectIterator.hasNext()) {
            HashSet<String> anagrams = new HashSet<>();
            String subject = subjectIterator.next();
            CharacterSet subjectCharset = new CharacterSet(subject);
            if (usedCharsets.contains(subjectCharset)) {
                continue;
            }
            anagrams.add(subject);
            Iterator<String> wordIterator = wordProvider.wordsIterator();
            while(wordIterator.hasNext()) {
                String word = wordIterator.next();
                CharacterSet wordCharset = new CharacterSet(word);
                if (usedCharsets.contains(wordCharset)) {
                    continue;
                }
                if (subjectCharset.equals(wordCharset)) {
                    anagrams.add(word);
                }
            }
            if (anagrams.size() > 1) {
                consumer.accept(anagrams);
            }
            usedCharsets.add(subjectCharset);
        }
    }
}
