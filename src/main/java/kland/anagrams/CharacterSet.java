package kland.anagrams;

import java.util.HashMap;
import java.util.Map;

public class CharacterSet {

    Map<Character, Integer> characters;

    public CharacterSet(String word) {
        if (word == null || word.length() < 1)
            throw new IllegalArgumentException("Invalid word");

        // create map which length equals to word's length for worst case scenario
        Map<Character, Integer> tmpCharacters = new HashMap<>(word.length());

        // count each character
        for (Character character : word.toCharArray()) {
            if (!tmpCharacters.containsKey(character)) {
                tmpCharacters.put(character, 0);
            } else {
                tmpCharacters.put(character, tmpCharacters.get(character) + 1);
            }
        }

        // occupy only required memory
        characters = new HashMap<>(tmpCharacters);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CharacterSet that = (CharacterSet) o;

        return characters.equals(that.characters);

    }

    @Override
    public int hashCode() {
        return characters.hashCode();
    }
}
