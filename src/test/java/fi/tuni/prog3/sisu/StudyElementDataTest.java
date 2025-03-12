package fi.tuni.prog3.sisu;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * Tests for StudyElementData class
 */
public class StudyElementDataTest {
    
    private final URL url;
    private final InputStreamReader reader;
    private final JsonObject root;
    private final JsonObject rootNull;
    
    public StudyElementDataTest() throws IOException {
        url = new URL("https://sis-tuni.funidata.fi/kori/api/modules/by-group-"
                + "id?groupId=otm-3858f1d8-4bf9-4769-b419-3fee1260d7ff&"
                + "universityId=tuni-university-root-id");
        reader = new InputStreamReader(url.openStream());
        root = JsonParser.parseReader(reader).getAsJsonArray().get(0).getAsJsonObject();
        rootNull = null;
    }
    
    /**
     * Test of readNameFI method, of class StudyElementData.
     */
    @Test
    public void testReadNameFI() {       
        System.out.println("readNameFI");
        String expResult = "Tietojenkäsittelytieteiden tutkinto-ohjelman yhteiset opinnot";
        String result = StudyElementData.readNameFI(root);
        assertEquals(expResult, result);
        String resultNull = StudyElementData.readNameFI(rootNull);
        assertEquals(null, resultNull);     
    }

    /**
     * Test of readNameEN method, of class StudyElementData.
     */
    @Test
    public void testReadNameEN() {
        System.out.println("readNameEN");
        String expResult = "General Studies in Computer Sciences";
        String result = StudyElementData.readNameEN(root);
        assertEquals(expResult, result);
        String resultNull = StudyElementData.readNameEN(rootNull);
        assertEquals(null, resultNull);
    }

    /**
     * Test of readCreditsMin method, of class StudyElementData.
     */
    @Test
    public void testReadCreditsMin() {
        System.out.println("readCreditsMin");
        int expResult = 35;
        int result = StudyElementData.readCreditsMin(root, false);
        assertEquals(expResult, result);
        int noResult = StudyElementData.readCreditsMin(rootNull, false);
        assertEquals(-1, noResult);
    }

    /**
     * Test of readCreditsMax method, of class StudyElementData.
     */
    @Test
    public void testReadCreditsMax() {
        System.out.println("readCreditsMax");
        int expResult = -1;
        int result = StudyElementData.readCreditsMax(root, false);
        assertEquals(expResult, result);
        int noResult = StudyElementData.readCreditsMax(rootNull, false);
        assertEquals(-1, noResult);
    }

    /**
     * Test of readLearningOutcomes method, of class StudyElementData.
     */
    @Test
    public void testReadLearningOutcomes() {
        System.out.println("readLearningOutcomes");
        JsonElement element = null;
        String expResult = "Osaamistavoitteita ei ole määritelty.";
        String result = StudyElementData.readLearningOutcomes(element);
        assertEquals(expResult, result);
    } 
}
