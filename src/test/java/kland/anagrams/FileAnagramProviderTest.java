package kland.anagrams;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

import static org.junit.Assert.*;

public class FileAnagramProviderTest {

    @Test
    public void testFindAnagrams() throws Exception {
        File file = createFile(Arrays.asList("acre", "care", "race", "cat", "act", "single", "another"));
        TestableFileAnagramProvider provider = new TestableFileAnagramProvider(file, 3);
        AnagramGroups anagramGroups = provider.provide();
        assertEquals(3, provider.getMergeWords().size());
        assertEquals(Arrays.asList("acre", "care", "race"), provider.getMergeWords().get(0));
        assertEquals(Arrays.asList("cat", "act", "single"), provider.getMergeWords().get(1));
        assertEquals(Arrays.asList("another"), provider.getMergeWords().get(2));
        assertEquals(2, anagramGroups.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidConstructorArguments_nullFile() throws Exception {
        new FileAnagramProvider(null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidConstructorArguments_nullBuffer() throws Exception {
        File file = File.createTempFile("tmp", null);
        file.deleteOnExit();
        new FileAnagramProvider(file, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidConstructorArguments_zeroBuffer() throws Exception {
        File file = File.createTempFile("tmp", null);
        file.deleteOnExit();
        new FileAnagramProvider(file, 0);
    }

    private File createFile(List<String> words) throws Exception {
        File file = File.createTempFile("tmp", null);
        file.deleteOnExit();
        PrintWriter writer = new PrintWriter(file);
        words.forEach(word -> writer.write(word + System.getProperty("line.separator")));
        writer.close();
        return file;
    }

    private static class TestableFileAnagramProvider extends FileAnagramProvider {

        public List<List<String>> mergeWords = new ArrayList<>();

        public TestableFileAnagramProvider(File file, Integer wordBufferSize) throws FileNotFoundException {
            super(file, wordBufferSize);
        }

        @Override
        protected void groupAndMerge(List<String> words, Map<CharacterSet, Set<String>> groups) {
            mergeWords.add(new ArrayList<>(words));
            super.groupAndMerge(words, groups);
        }

        public List<List<String>> getMergeWords() {
            return mergeWords;
        }
    }
}