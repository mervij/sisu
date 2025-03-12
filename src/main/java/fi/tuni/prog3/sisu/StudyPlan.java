package fi.tuni.prog3.sisu;

/**
 * A class for representing study plan information.
 */
public class StudyPlan {
    /* Gson with JDK 17 is unable to handle private class variables without 
     * implementing a custom type adapter for it. We weren't able to do this 
     * adapter, so this class has public class variables.
     */
    public String name;
    public String programmeId;

    /**
     * Constructor for the study plan.
     * @param name The name of the plan.
     * @param programmeId The programme's id.
     */
    public StudyPlan(String name, String programmeId)
    {
        this.name = name;
        this.programmeId = programmeId;
    }

    /**
     * Returns the name of the plan.
     * @return the name of the plan.
     */
    public String getName() {return name; }

    /**
     * Returns the programme's id.
     * @return the programme's id.
     */
    public String getProgrammeId() {return programmeId; }

    /**
     * Sets the name of the plan.
     * @param name the name of the plan.
     */
    public void setName(String name) {this.name = name; }

    /**
     * Sets the id of the programme
     * @param programmeId the id of the programme.
     */
    public void setProgrammeId(String programmeId) {this.programmeId = programmeId; }
}
