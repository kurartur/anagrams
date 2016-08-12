package kland.anagrams.wordprovider;

import java.io.*;

public abstract class FileWordProvider implements WordProvider {

    protected File file;

    public FileWordProvider(File file) throws IOException {
        if (!file.exists() || !file.canRead()) {
            throw new IOException("Error: file doesn't exist or is not readable");
        }
        this.file = file;
    }

}
