package kland.anagrams.wordprovider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

/**
 * Provides iterator over words directly from file.
 * Slow, but doesn't consume memory.
 */
public class DirectFileWordProvider extends FileWordProvider {

    public DirectFileWordProvider(File file) throws IOException {
        super(file);
    }

    @Override
    public Iterator<String> wordsIterator() {
        try {
            return new FileLineIterator(file);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return null;
        }
    }

}
