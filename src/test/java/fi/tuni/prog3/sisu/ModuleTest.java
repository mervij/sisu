package fi.tuni.prog3.sisu;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * Tests for Module class.
 */
public class ModuleTest {
    
    Module module, moduleEN;
    
    public ModuleTest() {
        module = new Module("moduleid","testimoduuli", "testmodule", 
                                    20, 20, "Learning outcomes");
        moduleEN = new Module("moduleid",null, "testmodule", 
                                    20, 20, "Learning outcomes");
        Module submodule = new Module("submoduleid","testialimoduuli", "testsubmodule", 
                                    20, 20, "Learning outcomes");
        ArrayList<Module> modules = new ArrayList<>();
        modules.add(submodule);
        module.setSubmodules(modules);
        ArrayList<Course> courses = new ArrayList<>();
        courses.add(new Course("courseid1","testikurssi1","testcourse1", 
                                5,5,"Course learning outcomes"));  
        module.setCourses(courses);
    }
    
    /**
     * Test of getCourses method, of class Module.
     */
    @Test
    public void testGetCourses() {
        System.out.println("getCourses");
        ArrayList<Course> expResult = new ArrayList<>();
        expResult.add(new Course("courseid1","testikurssi1","testcourse1", 
                                5,5,"Course learning outcomes"));
        ArrayList<Course> result = module.getCourses();
        assertEquals(expResult.get(0).getId(), result.get(0).getId());
    }

    /**
     * Test of getSubmodules method, of class Module.
     */ 
    @Test
    public void testGetSubmodules() {
        System.out.println("getSubmodules");
        ArrayList<Module> expResult = new ArrayList<>();
        expResult.add(new Module("submoduleid","testialimoduuli", "testsubmodule", 
                                    20, 20, "Learning outcomes"));
        ArrayList<Module> result = module.getSubmodules();
        assertEquals(expResult.get(0).getId(), result.get(0).getId());
    }
    
    /**
     * Test of toString method, of class Module.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        String expResult = "testimoduuli 0op / 20op";
        assertEquals(expResult, module.toString());
        String expResultEN = "testmodule 0op / 20op";
        assertEquals(expResultEN, moduleEN.toString());
    }    
}
