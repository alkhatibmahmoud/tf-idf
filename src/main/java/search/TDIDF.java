package main.java.search;

import main.java.model.DocumentData;

import java.util.List;
import java.util.Map;

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

    public static double getInverseDocumentFrequency(String searchTerm, Map<String, DocumentData> documentSearchResults){
        double numberOfDocumentsWithSearchTerm = 0.0;
        for(String document: documentSearchResults.keySet()){
            DocumentData documentData = documentSearchResults.get(document);
            if(documentData.getTermFrequency(searchTerm) > 0.0) numberOfDocumentsWithSearchTerm++;
        }
        return numberOfDocumentsWithSearchTerm == 0 ? 0 : Math.log10(documentSearchResults.size() / numberOfDocumentsWithSearchTerm);
    }
}
