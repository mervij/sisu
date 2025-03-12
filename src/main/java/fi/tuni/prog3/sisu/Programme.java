package fi.tuni.prog3.sisu;

import java.util.ArrayList;

/**
 * A class for representing information of a degree programme.
 * 
 */
public class Programme extends StudyElement<Programme> {
    
    private ArrayList <Module> modules = null;
    
    /**
     * Constructs a Programme object that is initialized 
     * with a id, names, credits and learning outcomes. 
     * @param id id of the programme.
     * @param nameFI name of the programme in Finnish.
     * @param nameEN name of the programme in English.
     * @param creditsMIN minimum credits of the programme.
     * @param creditsMAX maximum credits of the programme.
     * @param learningOutcomes learning outcomes of the programme
     */
    public Programme (String id, String nameFI, String nameEN, 
            int creditsMIN, int creditsMAX, String learningOutcomes){
        super(id, nameFI, nameEN, creditsMIN, creditsMAX, learningOutcomes);
    }

    /**
     * Sets the module list of the programme. 
     * @param modules list of programme's modules.
     */
    private void setModules(ArrayList <Module> modules) {
        this.modules = modules;
    }
    
    /**
     * Returns the modules of the programme. 
     * @return the modules of the programme.
     */
    public ArrayList<Module> getModules() {
        if (this.modules == null){
            setModules(ProgrammeData.readModules(getId()));
        }
        
        return this.modules;
    } 
    
    /**
     * Returns programme's name as a string. Default is a Finnish name, 
     * if it doesn't exist, an English name is returned. 
     * @return name of the programme
     */
    @Override
    public String toString() {
        if (getNameFI() != null) {
            return getNameFI();
        }
        return getNameEN();
    }

    /**
     * basic override for abstract function
     * @param i credits/truthvalue
     * @return null
     */
    @Override
    public String toString(int i) {
        return null;
    }
}
