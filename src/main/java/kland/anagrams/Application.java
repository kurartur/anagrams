package kland.anagrams;

import java.io.*;
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
        Integer buffer = null;
        if (args.length == 2) {
            try{
                buffer = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                userError("Error: word/line buffer is not an integer");
                return;
            }
            if (buffer < 1) {
                userError("Error: invalid buffer value");
                return;
            }
        }

        try {
            AnagramProvider anagramProvider = getAnagramProvider(file, buffer);
            Consumer consumer = getAnagramConsumer();
            anagramProvider.provide().values().forEach(consumer);
        } catch (FileNotFoundException fnfe) {
            userError("Error: file not found");
        } catch (Exception e) {
            internalError("Error: " + e.getMessage());
        }
    }

    protected AnagramProvider getAnagramProvider(File file, Integer buffer) throws FileNotFoundException {
        if (buffer != null)
            return new FileAnagramProvider(file, buffer);
        else
            return new FileAnagramProvider(file);
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
