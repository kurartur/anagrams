package kland.anagrams;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;
import java.util.function.Consumer;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ApplicationTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private File testFile;

    private TestableApplication application = new TestableApplication();
    private AnagramProvider anagramProvider = mock(AnagramProvider.class);
    private Consumer<Set<String>> anagramConsumer = mock(Consumer.class);

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
    public void testRun_notIntegerBuffer() throws Exception {
        application.run(new String[]{testFile.getAbsolutePath(), "asd"});
        assertEquals("Error: word/line buffer is not an integer\nUsage: java -jar <file> [<word/line buffer>]\n", outContent.toString());
    }

    @Test
    public void testRun_invalidBuffer() throws Exception {
        application.run(new String[]{testFile.getAbsolutePath(), "0"});
        assertEquals("Error: invalid buffer value\nUsage: java -jar <file> [<word/line buffer>]\n", outContent.toString());
    }

    @Test
    public void testGetAnagramProvider() throws Exception {
        Application application = new Application();
        FileAnagramProvider provider = (FileAnagramProvider)application.getAnagramProvider(testFile, 10);
        assertEquals((Integer)10, provider.getWordBufferSize());
        provider = (FileAnagramProvider)application.getAnagramProvider(testFile, null);
        assertEquals(FileAnagramProvider.DEFAULT_WORD_BUFFER_SIZE, provider.getWordBufferSize());
    }

    @Test
    public void testRun_fileNotFound() throws Exception {
        new TestableApplication() {
            @Override
            protected AnagramProvider getAnagramProvider(File file, Integer buffer) throws FileNotFoundException {
                throw new FileNotFoundException("File not found");
            }
        }.run(new String[]{testFile.getAbsolutePath(), "10"});
        assertEquals("Error: file not found\nUsage: java -jar <file> [<word/line buffer>]\n", outContent.toString());
    }

    @Test
    public void testRun_exception() throws Exception {
        when(anagramProvider.provide()).thenThrow(new RuntimeException("any error"));
        application.run(new String[]{testFile.getAbsolutePath(), "10"});
        assertEquals("Error: any error\n", outContent.toString());
    }

    @Test
    public void testRun() throws Exception {
        Map<CharacterSet, Set<String>> groups = new HashMap<>();
        Set<String> anagrams = new HashSet<>(Arrays.asList("care", "acre"));
        groups.put(new CharacterSet("care"), anagrams);
        when(anagramProvider.provide()).thenReturn(new AnagramGroups(groups));
        application.run(new String[]{testFile.getAbsolutePath(), "3"});
        verify(anagramConsumer, times(1)).accept(anagrams);
    }

    private class TestableApplication extends Application {

        @Override
        protected AnagramProvider getAnagramProvider(File file, Integer buffer) throws FileNotFoundException {
            return anagramProvider;
        }

        @Override
        protected Consumer<Set<String>> getAnagramConsumer() {
            return anagramConsumer;
        }
    }
}