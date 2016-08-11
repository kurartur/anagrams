package kland.anagrams.wordprovider;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public abstract class FileWordProvider implements WordProvider {

    protected File file;
    protected FileReader fileReader;

    public FileWordProvider(File file) throws FileNotFoundException {
        this.file = file;
        this.fileReader = new FileReader(file);
    }

    protected BufferedReader getReader() {
        return new BufferedReader(fileReader);
    }

}
