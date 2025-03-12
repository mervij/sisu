package fi.tuni.prog3.sisu;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * A class for representing information of a user.
 */
public class User {
    /* Gson with JDK 17 is unable to handle private class variables without 
     * implementing a custom type adapter for it. We weren't able to do this 
     * adapter, so this class has public class variables.
     */
    public String studentNumber;
    public String name;
    public String address;
    public String email;
    public String phonenumber;
    public StudyPlan primaryStudyPlan;
    
    public List<StudyPlan> savedStudyPlans;
    public List<String> completedCourses;
    
    /**
     * Default constructor.
     */
    public User (){
        this.studentNumber = "";
        this.name = "";
        this.address = "";
        this.email = "";
        this.phonenumber = "";
        this.primaryStudyPlan = null;
        this.savedStudyPlans = new ArrayList<>();
        this.completedCourses = new ArrayList<>();
    }
    
    /**
     * Constructs a User object that is initialized
     * with a student number. 
     * @param studentNumber student number of the user.
     */
    public User (String studentNumber){
        this.studentNumber = studentNumber;
        this.name = "";
        this.address = "";
        this.email = "";
        this.phonenumber = "";
        this.primaryStudyPlan = null;
        this.savedStudyPlans = new ArrayList<>();
        this.completedCourses = new ArrayList<>();
    }

    /**
     * Returns the student number.
     * @return the student number
     */
    public String getStudentNumber() {
        return studentNumber;
    }

    /**
     * Sets the student number.
     * @param studentNumber the student number to set
     */
    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }
    
    /**
     * Returns the name of the user. 
     * @return the name of the user. 
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the user. 
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the address of the user. 
     * @return the address of the user.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the user.
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Returns the email of the user. 
     * @return the email of the user. 
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the user. 
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Return the phonenumber of the user. 
     * @return the phonenumber of the user. 
     */
    public String getPhonenumber() {
        return phonenumber;
    }

    /**
     * Sets the phonenumber of the user. 
     * @param phonenumber the phonenumber to set
     */
    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    /**
     * Returns the list of saved study plans of the user.
     * @return the list of saved StudyPlans of the user.
     */
    public List<StudyPlan> getSavedStudyPlans() {
        return savedStudyPlans;
    }

    /**
     * Sets the list of saved study plans of the user. 
     * @param savedStudyPlans the savedStudyPlans to set
     */
    public void setSavedStudyPlans(List<StudyPlan> savedStudyPlans) {
        this.savedStudyPlans = savedStudyPlans;
    }

    /**
     * Returns the list of completed courses of the user. 
     * @return the list completed courses of the user. 
     */
    public List<String> getCompletedCourses() {
        return completedCourses;
    }

    /**
     * Sets the list of the completed courses of the user. 
     * @param completedCourses the completedCourses to set
     */
    public void setCompletedCourses(List<String> completedCourses) {
        this.completedCourses = completedCourses;
    }
    
    /**
     * Adds a id of the degree programme to the list of saved study plans. 
     * @param plan the id of the degree programme.
     */
    public void addStudyPlan(StudyPlan plan){
        getSavedStudyPlans().add(plan);
    }
    
    /**
     * Adds a id of the course to the list of completed course. 
     * @param id the id of the course. 
     */
    public void addCompletedCourse(String id){
        getCompletedCourses().add(id);
    }
    
    /**
     * Returns whether the course is completed or not.
     * @param id the id of the course. 
     * @return true, if the course is completed, otherwise false. 
     */
    public boolean isCourseCompleted(String id){
        return getCompletedCourses().stream().anyMatch(c -> (c.equals(id)));    
    }

    /**
     * Returns the primary study plan that the user has selected.
     * @return the plan's name
     */
    public StudyPlan getPrimaryStudyPlan(){return primaryStudyPlan; }

    /**
     * Sets the primary study plan that the user has selected.
     * @param name the name of the plan
     */
    public void setPrimaryStudyPlan(StudyPlan name){primaryStudyPlan = name; }
}
