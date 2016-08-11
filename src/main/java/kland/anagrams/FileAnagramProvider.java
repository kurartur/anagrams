package kland.anagrams;

import java.io.*;
import java.util.*;

/**
 * Consumes a file with words (each on separate line), searches for anagram words
 */
public class FileAnagramProvider implements AnagramProvider {

    public static final Integer DEFAULT_WORD_BUFFER_SIZE = 5000;
    private FileReader fileReader;
    private Integer wordBufferSize = DEFAULT_WORD_BUFFER_SIZE;

    /**
     *
     * @param file file with words
     * @throws FileNotFoundException
     */
    public FileAnagramProvider(File file) throws FileNotFoundException {
        if (file == null)
            throw new IllegalArgumentException("File can't be null");
        this.fileReader = new FileReader(file);
    }

    /**
     *
     * @param file file with words
     * @param wordBufferSize max word count used for searching anagrams during one iteration (useful for large word counts)
     * @throws FileNotFoundException
     */
    public FileAnagramProvider(File file, Integer wordBufferSize) throws FileNotFoundException {
        this(file);
        if (wordBufferSize == null || wordBufferSize < 1)
            throw new IllegalArgumentException("Invalid buffer size");
        this.wordBufferSize = wordBufferSize;
    }

    @Override
    public AnagramGroups provide() throws AnagramProviderException {
        try (BufferedReader br = new BufferedReader(fileReader)) {
            Map<CharacterSet, Set<String>> groups = new HashMap<>();
            List<String> words = new ArrayList<>(wordBufferSize);
            String line;
            while ((line = br.readLine()) != null) {
                if (!"".equals(line.trim())) {
                    words.add(line);
                }
                if (words.size() >= wordBufferSize) {
                    groupAndMerge(words, groups);
                    words.clear();
                }
            }
            if (words.size() > 0) {
                groupAndMerge(words, groups);
                words.clear();
            }
            return new AnagramGroups(groups);
        } catch (IOException ioe) {
            throw new AnagramProviderException("Error: unable read content from file. " + ioe.getMessage(), ioe);
        }
    }

    protected void groupAndMerge(List<String> words, Map<CharacterSet, Set<String>> anagrams) {
        CharacterSetUtils.groupByCharacterSetParallel(words).forEach((k, v) -> anagrams.merge(k, v, (words1, words2) -> {
            HashSet<String> merged = new HashSet<>();
            merged.addAll(words1);
            merged.addAll(words2);
            return merged;
        }));
    }

    public Integer getWordBufferSize() {
        return wordBufferSize;
    }
}
