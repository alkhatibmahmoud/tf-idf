package tests.search;

import main.java.model.DocumentData;
import main.java.search.TDIDF;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TDIDFTest {

    @Test
    public void testCalculateTermFrequency() {
        List<String> document = Arrays.asList("apple", "banana", "apple", "orange", "banana");
        String searchTerm = "apple";
        double result = TDIDF.calculateTermFrequency(document, searchTerm);
        assertEquals(0.4, result, 0.001); // Assuming a precision of 3 decimal places
    }

    @Test
    public void testCalculateTermFrequencyCaseInsensitive() {
        List<String> document = Arrays.asList("Apple", "Banana", "apple", "Orange", "banana");
        String searchTerm = "apple";
        double result = TDIDF.calculateTermFrequency(document, searchTerm);
        assertEquals(0.4, result, 0.001);
    }

    @Test
    public void testCalculateTermFrequencyWithEmptyDocument() {
        List<String> document = new ArrayList<>();
        String searchTerm = "apple";
        double result = TDIDF.calculateTermFrequency(document, searchTerm);
        assertEquals(0.0, result, 0.001);
    }

    @Test
    public void testCreateDocumentData() {
        List<String> document = Arrays.asList("apple", "banana", "apple", "orange", "banana");
        List<String> searchTerms = Arrays.asList("apple", "banana", "orange");

        DocumentData documentData = TDIDF.createDocumentData(document, searchTerms);

        assertEquals(0.4, documentData.getTermFrequency("apple"), 0.001);
        assertEquals(0.4, documentData.getTermFrequency("banana"), 0.001);
        assertEquals(0.2, documentData.getTermFrequency("orange"), 0.001);
    }

    @Test
    public void testCreateDocumentDataWithEmptyDocument() {
        List<String> document = new ArrayList<>();
        List<String> searchTerms = Arrays.asList("apple", "banana", "orange");

        DocumentData documentData = TDIDF.createDocumentData(document, searchTerms);

        assertEquals(0.0, documentData.getTermFrequency("apple"), 0.001);
        assertEquals(0.0, documentData.getTermFrequency("banana"), 0.001);
        assertEquals(0.0, documentData.getTermFrequency("orange"), 0.001);
    }
}
