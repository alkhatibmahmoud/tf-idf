package main.java.search;

import main.java.model.DocumentData;

import java.util.*;

public class TFIDF {
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

    private static Map<String, Double> getTermToInverseDocumentFrequencyMap(List<String> searchTerms, Map<String, DocumentData> documentSearchResults){
        Map<String, Double> searchTermToIDF = new HashMap<>();
        for(String searchTerm : searchTerms) {
            double idf = getInverseDocumentFrequency(searchTerm, documentSearchResults);
            searchTermToIDF.put(searchTerm, idf);
        }
        return searchTermToIDF;
    }

    public static double calculateDocumentScore(List<String> searchTerms, DocumentData documentData, Map<String, Double> searchTermToInverseDocumentFrequency){
        double score = 0;
        for(String searchTerm : searchTerms){
            double searchTermFrequency = documentData.getTermFrequency(searchTerm);
            double inverseTermFrequency = searchTermToInverseDocumentFrequency.get(searchTerm);
            score += searchTermFrequency * inverseTermFrequency;
        }
        return score;
    }

    public static Map<Double, List<String>> getDocumentSortedByScore(List<String> searchTerms, Map<String, DocumentData> documentResult){
        // we have multiple documents with the same store
        TreeMap<Double, List<String>> scoreToDocuments = new TreeMap<>();
        Map<String, Double> searchTermToInverseDocFrequency = getTermToInverseDocumentFrequencyMap(searchTerms, documentResult);
        for(String document: documentResult.keySet()){
            DocumentData documentData = documentResult.get(document);
            double score = calculateDocumentScore(searchTerms, documentData, searchTermToInverseDocFrequency);
            addDocumentScoreToTreeMap(scoreToDocuments, score, document);
        }
        return scoreToDocuments.descendingMap();


    }

    private static void addDocumentScoreToTreeMap(TreeMap<Double, List<String>> scoreToDocuments, double score, String document) {
        List<String> documentsWithCurrentScore = scoreToDocuments.get(score);
        if(documentsWithCurrentScore == null){
            documentsWithCurrentScore = new ArrayList<>();
        }
        documentsWithCurrentScore.add(document);
        scoreToDocuments.put(score, documentsWithCurrentScore);
    }
}
