package kland.anagrams.wordprovider;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Reads words from file into memory, provides iterator over Set that is stored in memory
 * Fast, but can consume a lot of memory.
 */
public class MemoryFileWordProvider extends FileWordProvider {

    protected Set<String> words = new HashSet<>();

    public MemoryFileWordProvider(File file) throws IOException {
        super(file);
        readWordsIntoMemory(file);
    }

    private void readWordsIntoMemory(File file) throws IOException {
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            String word;
            while ((word = br.readLine()) != null) {
                words.add(word);
            }
        }
    }

    @Override
    public Iterator<String> wordsIterator() {
        return words.iterator();
    }
}
