package kland.anagrams.finder;

import org.junit.Test;

import static org.junit.Assert.*;

public class FlushingAnagramFinderTest extends BaseAnagramFinderTest {

    @Override
    protected AnagramFinder getAnagramFinder() {
        return new FlushingAnagramFinder();
    }
}