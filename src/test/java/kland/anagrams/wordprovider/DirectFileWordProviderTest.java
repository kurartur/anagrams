package kland.anagrams.wordprovider;

import java.io.IOException;

public class DirectFileWordProviderTest extends BaseFileWordProviderTest {

    @Override
    WordProvider getWordProvider() throws IOException {
        return new DirectFileWordProvider(testFile);
    }
}