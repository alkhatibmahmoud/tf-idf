package main.java.model;

import java.util.HashMap;
import java.util.Map;

public class DocumentData {
    private Map<String, Double> termToFrequency = new HashMap<>();

    public void putTermFrequency(String term, double frequency){
        termToFrequency.put(term, frequency);
    }

    public double getTermFrequency(String term){
        return Double.isNaN(termToFrequency.get(term)) ? 0.0 : termToFrequency.get(term);
    }
}
