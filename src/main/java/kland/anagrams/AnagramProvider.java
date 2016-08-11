package kland.anagrams;

public interface AnagramProvider {

    AnagramGroups provide() throws AnagramProviderException;

}
