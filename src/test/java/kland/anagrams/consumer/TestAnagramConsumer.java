package kland.anagrams.consumer;

import java.util.ArrayList;
import java.util.Set;
import java.util.function.Consumer;

public class TestAnagramConsumer implements Consumer<Set<String>> {

    private ArrayList<Set<String>> anagrams = new ArrayList<>();

    @Override
    public void accept(Set<String> a) {
        anagrams.add(a);
    }

    public ArrayList<Set<String>> getAnagrams() {
        return anagrams;
    }

    public Set<String> getAnagrams(String word) {
        for (Set<String> set : anagrams) {
            if (set.contains(word))
                return set;
        }
        return null;
    }

}
