package kland.anagrams.consumer;

import java.util.Set;
import java.util.function.Consumer;

public class ConsoleAnagramConsumer implements Consumer<Set<String>> {

    @Override
    public void accept(Set<String> anagrams) {
        String delimiter = "";
        for (String anagram : anagrams) {
            System.out.print(delimiter);
            delimiter = " ";
            System.out.print(anagram);
        }
        System.out.println();
    }
}
