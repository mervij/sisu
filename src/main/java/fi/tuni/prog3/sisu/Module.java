package fi.tuni.prog3.sisu;

import java.util.ArrayList;

/**
 * A class for representing information of a module.
 * 
 */
public class Module extends StudyElement<Module> {
    private ArrayList <Course> courses = null;
    private ArrayList <Module> submodules = null;
    
    /**
     * Constructs a Module object that is initialized 
     * with a id, a name, credits and list of courses. 
     * @param id id of the module.
     * @param nameFI name of the module in Finnish.
     * @param nameEN name of the module in English.
     * @param creditsMIN minimum credits of the module.
     * @param creditsMAX maximum credits of the module.
     * @param learningOutcomes learning outcomes of the module. 
     */
    public Module (String id, String nameFI, String nameEN, 
            int creditsMIN, int creditsMAX, String learningOutcomes){
        super(id, nameFI, nameEN, creditsMIN, creditsMAX, learningOutcomes);
    }
    
    /**
     * Sets the courses of the module. 
     * @param courses a list of Course objects.
     */
    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    } 

    /**
     * Returns the courses of the module. 
     * @return the courses of the module.
     */
    public ArrayList<Course> getCourses() {
        if (this.courses == null){
            setCourses(ModuleData.readCourses(getId()));
        }
        
        return this.courses;
    } 
    
    /**
     * Sets the submodules of the module. 
     * @param submodules a list of Module objects.
     */
    public void setSubmodules(ArrayList<Module> submodules) {
        this.submodules = submodules;
    } 
    
    /**
     * Returns the submodules of the module. 
     * @return the submodules of the module.
     */
    public ArrayList<Module> getSubmodules() {
        if (this.submodules == null){
            setSubmodules(ModuleData.readSubmodules(getId()));
        }

        return this.submodules;
    } 
    
    /**
     * Returns module's name as a string. Default is a Finnish name, 
     * if it doesn't exist, an English name is returned. 
     * @return name of the module
     */
    @Override
    public String toString() {
        if (getCreditsMIN() == -1) {
            if (getNameFI() != null) {
                return String.format("%s %dop", getNameFI(), 0);
            }
            return String.format("%s %dop", getNameEN(), 0);
        }
        if (getNameFI() != null) {
            return String.format("%s %dop / %dop", getNameFI(), 0, getCreditsMIN());
        }
        return String.format("%s %dop / %dop", getNameEN(), 0, getCreditsMIN());
    }

    /**
     * Prints module with (completed) credits
     * @param credits credits completed
     * @return String of module
     */
    @Override
    public String toString(int credits) {
        if (getCreditsMIN() == -1) {
            if (getNameFI() != null) {
                return String.format("%s %dop", getNameFI(), credits);
            }
            return String.format("%s %dop", getNameEN(), credits);
        }
        if (getNameFI() != null) {
            return String.format("%s %dop / %dop", getNameFI(), credits, getCreditsMIN());
        }
        return String.format("%s %dop / %dop", getNameEN(), credits, getCreditsMIN());
    }
}
