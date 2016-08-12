package kland.anagrams;

import kland.anagrams.consumer.ConsoleAnagramConsumer;
import kland.anagrams.finder.AccumulatingAnagramFinder;
import kland.anagrams.finder.AnagramFinder;
import kland.anagrams.finder.FlushingAnagramFinder;
import kland.anagrams.wordprovider.DirectFileWordProvider;
import kland.anagrams.wordprovider.MemoryFileWordProvider;
import kland.anagrams.wordprovider.WordProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Set;
import java.util.function.Consumer;

public class Application {

    private WordProvider wordProvider;
    private AnagramFinder anagramFinder;
    private Consumer<Set<String>> anagramConsumer;

    public static void main(String[] args) {
        if (args.length == 0 || args.length > 2) {
            userError("Error: invalid number of arguments");
            return;
        }
        String flags = "";
        if (args.length == 2) {
            if (args[0].length() > 2 || !args[0].matches("^([af]?[md]?)|([md]?[af]?)&")) {
                userError("Error: invalid flags");
                return;
            }
            flags = args[0];
        }

        File file = new File(args.length == 1 ? args[0] : args[1]);
        WordProvider wordProvider;
        try {
            wordProvider = flags.contains("d") ? new DirectFileWordProvider(file) : new MemoryFileWordProvider(file);
        } catch (IOException ioe) {
            userError(ioe.getMessage());
            return;
        }

        AnagramFinder anagramFinder = flags.contains("f") ? new FlushingAnagramFinder() : new AccumulatingAnagramFinder();

        Application application = new Application();
        application.setWordProvider(wordProvider);
        application.setAnagramFinder(anagramFinder);
        application.setAnagramConsumer(new ConsoleAnagramConsumer());
        application.run();
    }

    public void run() {
        try {
            anagramFinder.find(wordProvider, anagramConsumer);
        } catch (Exception e) {
            internalError("Error: " + e.getMessage());
        }
    }

    public void setWordProvider(WordProvider wordProvider) {
        this.wordProvider = wordProvider;
    }

    public void setAnagramFinder(AnagramFinder anagramFinder) {
        this.anagramFinder = anagramFinder;
    }

    public void setAnagramConsumer(Consumer<Set<String>> anagramConsumer) {
        this.anagramConsumer = anagramConsumer;
    }

    private static void internalError(String error) {
        System.out.println(error);
    }

    private static void userError(String error) {
        System.out.println(error);
        System.out.println("Usage: java -jar <file> [<word/line buffer>]");
    }

}
