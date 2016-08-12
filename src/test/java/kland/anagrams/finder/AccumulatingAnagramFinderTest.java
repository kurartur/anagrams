package kland.anagrams.finder;

import kland.anagrams.consumer.TestAnagramConsumer;
import kland.anagrams.wordprovider.TestWordProvider;
import org.junit.Test;

import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class AccumulatingAnagramFinderTest extends BaseAnagramFinderTest {

    @Override
    protected AnagramFinder getAnagramFinder() {
        return new AccumulatingAnagramFinder();

    }
}