package main.java.search;

import main.java.model.DocumentData;

import java.util.List;

public class TDIDF {
    public static double calculateTermFrequency(List<String> document, String searchTerm){
        long count = 0;

        if(document.size() == 0){
            return 0.0;
        }

        for (String word: document){
            if(searchTerm.equalsIgnoreCase(word)) count++;
        }
        return (double) count / document.size();
    }

    public static DocumentData createDocumentData(List<String> document, List<String> searchTerms){
        DocumentData documentData = new DocumentData();
        for(String searchTerm: searchTerms){
            double searchTermFrequency = calculateTermFrequency(document, searchTerm);
            documentData.putTermFrequency(searchTerm, searchTermFrequency);
        }
        return documentData;
    }
}
