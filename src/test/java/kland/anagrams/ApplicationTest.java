package kland.anagrams;

import kland.anagrams.finder.AnagramFinder;
import kland.anagrams.wordprovider.WordProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.Set;
import java.util.function.Consumer;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ApplicationTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private File testFile;

    private TestableApplication application = new TestableApplication();
    private AnagramFinder anagramFinder = mock(AnagramFinder.class);
    private Consumer<Set<String>> anagramConsumer = mock(Consumer.class);
    private WordProvider wordProvider = mock(WordProvider.class);

    @Before
    public void setUp() throws Exception {
        System.setOut(new PrintStream(outContent));
        testFile = File.createTempFile("test", null);
        testFile.deleteOnExit();
    }

    @After
    public void tearDown() {
        System.setOut(null);
    }

    @Test
    public void testRun_noArgs() throws Exception {
        application.run(new String[]{});
        assertEquals("Error: invalid number of arguments\nUsage: java -jar <file> [<word/line buffer>]\n", outContent.toString());
    }

    @Test
    public void testRun_fileDoesNotExist() throws Exception {
        application.run(new String[]{"file"});
        assertEquals("Error: file doesn't exist or is not readable\nUsage: java -jar <file> [<word/line buffer>]\n", outContent.toString());
    }

    @Test
    public void testRun_fileNotReadable() throws Exception {
        testFile.setReadable(false);
        application.run(new String[]{testFile.getAbsolutePath()});
        assertEquals("Error: file doesn't exist or is not readable\nUsage: java -jar <file> [<word/line buffer>]\n", outContent.toString());
    }

    @Test
    public void testRun_fileNotFound() throws Exception {
        new TestableApplication() {
            @Override
            protected WordProvider getWordProvider(File file) throws FileNotFoundException {
                throw new FileNotFoundException("File not found");
            }
        }.run(new String[]{testFile.getAbsolutePath(), "10"});
        assertEquals("Error: file not found\nUsage: java -jar <file> [<word/line buffer>]\n", outContent.toString());
    }

    @Test
    public void testRun_exception() throws Exception {
        doThrow(new RuntimeException("any error")).when(anagramFinder).find(wordProvider, anagramConsumer);
        application.run(new String[]{testFile.getAbsolutePath(), "10"});
        assertEquals("Error: any error\n", outContent.toString());
    }

    @Test
    public void testRun() throws Exception {
        application.run(new String[]{testFile.getAbsolutePath(), "3"});
        verify(anagramFinder, times(1)).find(wordProvider, anagramConsumer);
    }

    private class TestableApplication extends Application {

        @Override
        protected WordProvider getWordProvider(File file) throws IOException {
            return wordProvider;
        }

        @Override
        protected AnagramFinder getAnagramFinder() throws FileNotFoundException {
            return anagramFinder;
        }

        @Override
        protected Consumer<Set<String>> getAnagramConsumer() {
            return anagramConsumer;
        }
    }
}