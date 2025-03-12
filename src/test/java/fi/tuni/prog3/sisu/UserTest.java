package fi.tuni.prog3.sisu;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * Tests for User class
 */
public class UserTest {
    
    public UserTest() {
    }

    /**
     * Test of setStudentNumber method, of class User.
     */
    @Test
    public void testSetStudentNumber() {
        System.out.println("setStudentNumber");
        String studentNumber = "2345678";
        User instance = new User();
        instance.setStudentNumber(studentNumber);
        assertEquals(studentNumber, instance.getStudentNumber());
    }
    
    /**
     * Test of getStudentNumber method, of class User.
     */
    @Test
    public void testGetStudentNumber() {
        System.out.println("getStudentNumber");
        User instance = new User("123456");
        String expResult = "123456";
        String result = instance.getStudentNumber();
        assertEquals(expResult, result);
    }

    /**
     * Test of setName method, of class User.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        String name = "Apina";
        User instance = new User();
        instance.setName(name);
        assertEquals(name, instance.getName());
    }
    
    /**
     * Test of getName method, of class User.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        User instance = new User();
        instance.setName("Apina");
        String expResult = "Apina";
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getSavedStudyPlans method, of class User.
     */
    @Test
    public void testGetSavedStudyPlans() {
        System.out.println("getSavedStudyPlans");
        User instance = new User();
        List<StudyPlan> expResult = new ArrayList<>();
        expResult.add(new StudyPlan("id1", null));
        expResult.add(new StudyPlan("id2", null));
        instance.setSavedStudyPlans(expResult);
        List<StudyPlan> result = instance.getSavedStudyPlans();
        assertEquals(expResult, result);
    }

    /**
     * Test of getCompletedCourses method, of class User.
     */
    @Test
    public void testGetCompletedCourses() {
        System.out.println("getCompletedCourses");
        User instance = new User();
        List<String> expResult = new ArrayList<>();
        expResult.add("id1");
        expResult.add("id2");
        instance.setCompletedCourses(expResult);
        List<String> result = instance.getCompletedCourses();
        assertEquals(expResult, result);
    }

    /**
     * Test of addStudyPlan method, of class User.
     */
    @Test
    public void testAddStudyPlan() {
        System.out.println("addStudyPlan");
        List<StudyPlan> expResult = new ArrayList<>();
        User instance = new User();
        instance.setSavedStudyPlans(expResult);
        instance.addStudyPlan(new StudyPlan("id1", null));
        assertEquals("id1",instance.getSavedStudyPlans().get(0).getName());
    }

    /**
     * Test of addCompletedCourse method, of class User.
     */
    @Test
    public void testAddCompletedCourse() {
        System.out.println("addCompletedCourse");
        User instance = new User();
        instance.addCompletedCourse("id1");
        assertEquals("id1",instance.getCompletedCourses().get(0));
    }

    /**
     * Test of isCourseCompleted method, of class User.
     */
    @Test
    public void testIsCourseCompleted() {
        System.out.println("isCourseCompleted");
        List<String> courses = new ArrayList<>();
        courses.add("id1");
        courses.add("id2");
        User instance = new User();
        instance.setCompletedCourses(courses);
        assertTrue(instance.isCourseCompleted("id2"));
        assertFalse(instance.isCourseCompleted("id3"));
    }
}
