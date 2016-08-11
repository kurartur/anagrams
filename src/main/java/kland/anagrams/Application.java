package kland.anagrams;

import kland.anagrams.consumer.ConsoleAnagramConsumer;
import kland.anagrams.finder.AnagramFinder;
import kland.anagrams.finder.FlushingAnagramFinder;
import kland.anagrams.wordprovider.DirectFileWordProvider;
import kland.anagrams.wordprovider.WordProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Set;
import java.util.function.Consumer;

public class Application {

    public static void main(String[] args) {
        new Application().run(args);
    }

    public void run(String[] args) {
        if (args.length == 0 || args.length > 2) {
            userError("Error: invalid number of arguments");
            return;
        }
        File file = new File(args[0]);
        if (!file.exists() || !file.canRead()) {
            userError("Error: file doesn't exist or is not readable");
            return;
        }

        try {
            Long startTime = System.currentTimeMillis();
            WordProvider wordProvider = getWordProvider(file);
            AnagramFinder anagramFinder = getAnagramFinder();
            Consumer<Set<String>> consumer = getAnagramConsumer();
            anagramFinder.find(wordProvider, consumer);
            System.out.println(System.currentTimeMillis() - startTime);
        } catch (FileNotFoundException fnfe) {
            userError("Error: file not found");
        } catch (Exception e) {
            internalError("Error: " + e.getMessage());
        }
    }

    protected WordProvider getWordProvider(File file) throws IOException {
        return new DirectFileWordProvider(file);
    }

    protected AnagramFinder getAnagramFinder() throws FileNotFoundException {
        return new FlushingAnagramFinder();
    }

    protected Consumer<Set<String>> getAnagramConsumer() {
        return new ConsoleAnagramConsumer();
    }

    private void internalError(String error) {
        System.out.println(error);
    }

    private void userError(String error) {
        System.out.println(error);
        System.out.println("Usage: java -jar <file> [<word/line buffer>]");
    }

}
