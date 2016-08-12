#anagrams

Building:
---------

    ./gradlew build

jar will be created in *build/libs* directory

Running:
--------

    java -jar anagrams-1.0.jar [af][md] pathToFile

 - a - use accumulating anagrams finder (default)
 - f - use flushing anagrams finder
 - m - read words from file into memory (default)
 - d - always read words directly from file

example:

    java -jar anagrams-1.0.jar fd /home/user/words.txt
    (use flushing finder and direct reader)
