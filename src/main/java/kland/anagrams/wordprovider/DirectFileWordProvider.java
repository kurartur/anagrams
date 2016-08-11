package kland.anagrams.wordprovider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

public class DirectFileWordProvider implements WordProvider {

    private File file;

    public DirectFileWordProvider(File file) throws FileNotFoundException {
        this.file = file;
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
