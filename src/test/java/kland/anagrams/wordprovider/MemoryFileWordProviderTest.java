package kland.anagrams.wordprovider;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class MemoryFileWordProviderTest extends BaseFileWordProviderTest {

    @Override
    WordProvider getWordProvider() throws IOException {
        return new MemoryFileWordProvider(testFile);
    }
}