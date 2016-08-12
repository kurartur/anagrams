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

    private Application application = new Application();
    private AnagramFinder anagramFinder = mock(AnagramFinder.class);
    private Consumer<Set<String>> anagramConsumer = mock(Consumer.class);
    private WordProvider wordProvider = mock(WordProvider.class);

    @Before
    public void setUp() throws Exception {
        System.setOut(new PrintStream(outContent));
        testFile = File.createTempFile("test", null);
        testFile.deleteOnExit();
        application.setWordProvider(wordProvider);
        application.setAnagramConsumer(anagramConsumer);
        application.setAnagramFinder(anagramFinder);
    }

    @After
    public void tearDown() {
        System.setOut(null);
    }

    @Test
    public void testMain_noArgs() throws Exception {
        Application.main(new String[]{});
        assertEquals("Error: invalid number of arguments\nUsage: java -jar <app>.jar [af][md] pathToFile\n", outContent.toString());
    }

    @Test
    public void testMain_invalidFlagCount() throws Exception {
        Application.main(new String[]{"file", "aff"});
        assertEquals("Error: invalid flags\nUsage: java -jar <app>.jar [af][md] pathToFile\n", outContent.toString());
    }

    @Test
    public void testMain_invalidFlags() throws Exception {
        Application.main(new String[]{"file", "cf"});
        assertEquals("Error: invalid flags\nUsage: java -jar <app>.jar [af][md] pathToFile\n", outContent.toString());
    }
    
    @Test
    public void testMain_fileDoesNotExist() throws Exception {
        Application.main(new String[]{"file"});
        assertEquals("Error: file doesn't exist or is not readable\nUsage: java -jar <app>.jar [af][md] pathToFile\n", outContent.toString());
    }

    @Test
    public void testMain_fileNotReadable() throws Exception {
        testFile.setReadable(false);
        Application.main(new String[]{testFile.getAbsolutePath()});
        assertEquals("Error: file doesn't exist or is not readable\nUsage: java -jar <app>.jar [af][md] pathToFile\n", outContent.toString());
    }

    @Test
    public void testRun_exception() throws Exception {
        doThrow(new RuntimeException("any error")).when(anagramFinder).find(wordProvider, anagramConsumer);
        application.run();
        assertEquals("Error: any error\n", outContent.toString());
    }

    @Test
    public void testRun() throws Exception {
        application.run();
        verify(anagramFinder, times(1)).find(wordProvider, anagramConsumer);
    }

}