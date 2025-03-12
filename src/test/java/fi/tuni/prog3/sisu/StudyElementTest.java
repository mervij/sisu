package fi.tuni.prog3.sisu;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * Tests for Study Element class
 */
public class StudyElementTest {
    
    private StudyElement instance;
    
    public StudyElementTest() {
        instance = new StudyElementImpl(
                "elementID","nameFI","nameEN",5,5,"Learning outcomes");
    }
    
    /**
     * Test of getId method, of class StudyElement.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        String expResult = "elementID";
        String result = instance.getId();
        assertEquals(expResult, result);
    }

    /**
     * Test of getNameFI method, of class StudyElement.
     */
    @Test
    public void testGetNameFI() {
        System.out.println("getNameFI");
        String expResult = "nameFI";
        String result = instance.getNameFI();
        assertEquals(expResult, result);
    }

    /**
     * Test of getNameEN method, of class StudyElement.
     */
    @Test
    public void testGetNameEN() {
        System.out.println("getNameEN");
        String expResult = "nameEN";
        String result = instance.getNameEN();
        assertEquals(expResult, result);
    }

    /**
     * Test of getCreditsMIN method, of class StudyElement.
     */
    @Test
    public void testGetCreditsMIN() {
        System.out.println("getCreditsMIN");
        int expResult = 5;
        int result = instance.getCreditsMIN();
        assertEquals(expResult, result);
    }

    /**
     * Test of getCreditsMAX method, of class StudyElement.
     */
    @Test
    public void testGetCreditsMAX() {
        System.out.println("getCreditsMAX");
        int expResult = 5;
        int result = instance.getCreditsMAX();
        assertEquals(expResult, result);
    }

    /**
     * Test of getLearningOutcomes method, of class StudyElement.
     */
    @Test
    public void testGetLearningOutcomes() {
        System.out.println("getLearningOutcomes");
        String expResult = "Learning outcomes";
        String result = instance.getLearningOutcomes();
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class StudyElement.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    public class StudyElementImpl extends StudyElement {
        
        public StudyElementImpl (String id, String nameFI, String nameEN, int creditsMIN, int creditsMAX, String learningOutcomes){
            super(id, nameFI, nameEN, creditsMIN, creditsMAX, learningOutcomes);
        }

        @Override
        public String toString() {
            return "";
        }

        @Override
        public String toString(int i) {
            return null;
        }
    }
}
