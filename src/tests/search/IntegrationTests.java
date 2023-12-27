package tests.search;

import main.java.model.DocumentData;
import main.java.search.TFIDF;
import main.java.utils.DocumentParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

public class IntegrationTests {
    public static final String BOOKS_DIRECTORY = "./src/tests/search/resources/books";
    public static final String QUERY_1 = "The best detective that catches many criminals using his deductive methods";
    public static final String QUERY_2 = "The girl that falls through a rabbit whole to a fantasy wonderland";
    public static final String QUERY_3 = "A war between russia and french in cold winter";

    public static void main(String[] args) throws FileNotFoundException {
        File documentDirectory = new File(BOOKS_DIRECTORY);
        List<String> documents = Arrays.asList(documentDirectory.list())
                .stream()
                .map(documentName -> BOOKS_DIRECTORY + "/" + documentName)
                .collect(Collectors.toList());
        List<String> searchTerms = DocumentParser.getWordsFromALine(QUERY_1);
        findMostRelevantDocuments(documents, searchTerms);


    }

    private static void findMostRelevantDocuments(List<String> documents, List<String> searchTerms) throws FileNotFoundException {
        Map<String, DocumentData> documentDataMap = new HashMap<>();
        for (String document : documents) {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(document));
            List<String> lines = bufferedReader.lines().collect(Collectors.toList());
            List<String> words = DocumentParser.getWordsFromLines(lines);
            DocumentData documentData = TFIDF.createDocumentData(words, searchTerms);
            documentDataMap.put(document, documentData);
        }
        Map<Double, List<String>> documentsByScore = TFIDF.getDocumentSortedByScore(searchTerms, documentDataMap);

        printResults(documentsByScore);

    }

    private static void printResults(Map<Double, List<String>> documentsByScore) {
        for (Map.Entry<Double, List<String>> documentScorePair : documentsByScore.entrySet()) {
            double score = documentScorePair.getKey();
            for (String document : documentScorePair.getValue()) {
                System.out.printf("Book : %s - score : %f%n", document.split("/")[6], score);
            }
        }
    }
}