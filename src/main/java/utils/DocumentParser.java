package main.java.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DocumentParser {
    public static List<String> getWordsFromALine(String line){
        return Arrays.asList(line.split("(\\.)+|(,)+|( )+|(-)+|(\\?)+|(!)+|(;)+|(:)+|(/d)+|(/n)+"));
    }
    public static List<String> getWordsFromLines(List<String> lines){
        List<String> words  = new ArrayList<>();
        for(String line : lines){
            words.addAll(getWordsFromALine(line));
        }
        return words;
    }
}
