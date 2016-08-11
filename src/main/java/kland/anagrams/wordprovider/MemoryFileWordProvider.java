package kland.anagrams.wordprovider;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MemoryFileWordProvider implements WordProvider{

    protected Set<String> words = new HashSet<>();

    public MemoryFileWordProvider(File file) throws IOException {
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
