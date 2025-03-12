package fi.tuni.prog3.sisu;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * Tests for ModuleData class
 */
public class ModuleDataTest {
    
    private final static String MODULE_ERROR = "Moduulia ei löytynyt!";
    private static String id;
    private static String fakeId;
    
    public ModuleDataTest() {
        id = "otm-3858f1d8-4bf9-4769-b419-3fee1260d7ff";
        fakeId = "12345";
    }

    /**
     * Test of readBasicInformation method, of class ModuleData.
     */
    @Test
    public void testReadBasicInformation() {
        System.out.println("readBasicInformation");
        assertEquals(ModuleData.readBasicInformation(id).getClass(), Module.class);
        RuntimeException e = assertThrows(RuntimeException.class, 
                () -> ModuleData.readBasicInformation(fakeId));
        String actualMessage = e.getMessage();
        assertTrue(actualMessage.contains(MODULE_ERROR));
    }

    /**
     * Test of readSubmodules method, of class ModuleData.
     */
    @Test
    public void testReadSubmodules() {
        System.out.println("readSubmodules");
        assertEquals((ModuleData.readSubmodules(id)).get(0).getClass(), Module.class);
        RuntimeException e = assertThrows(RuntimeException.class, 
                () -> ModuleData.readBasicInformation(fakeId));
        String actualMessage = e.getMessage();
        assertTrue(actualMessage.contains(MODULE_ERROR));
    }

    /**
     * Test of readCourses method, of class ModuleData.
     */
    @Test
    public void testReadCourses() {
        System.out.println("readCourses");
        assertEquals((ModuleData.readCourses(id)).get(0).getClass(), Course.class);
        RuntimeException e = assertThrows(RuntimeException.class, 
                () -> ModuleData.readBasicInformation(fakeId));
        String actualMessage = e.getMessage();
        assertTrue(actualMessage.contains(MODULE_ERROR));
    }   
}
